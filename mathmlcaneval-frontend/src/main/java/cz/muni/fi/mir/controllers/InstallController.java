/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.AnnotationValue;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.AnnotationValueSerivce;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.services.MailService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptka
 */
@RequestMapping("/setup")
@Controller
public class InstallController
{

    private static final Logger logger = Logger.getLogger(InstallController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RevisionService revisionService;
    @Autowired
    private AnnotationValueSerivce annotationValueSerivce;
    @Autowired
    private CanonicOutputService canonicOutputService;
    
    
    @Value("${mathml-canonicalizer.default.revision}")
    private String revisionValue;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView landingPage()
    {
        if (Tools.getInstance().isEmpty(userService.getAllUsers()))
        {
            

            ModelMap mm = new ModelMap();
            mm.addAttribute("userForm", new UserForm());

            return new ModelAndView("setup/step1", mm);
        }
        else
        {
            return new ModelAndView("redirect:/reset/");
        }
    }

    @RequestMapping(value = "/step2/", method = RequestMethod.POST)
    public ModelAndView step2(HttpServletRequest request)
    {
        String username = null;
        String email = null;
        String pass1 = null;
        String passVerify = null;
        try
        {
            username = ServletRequestUtils.getStringParameter(request, "username");
            email = ServletRequestUtils.getStringParameter(request, "email");
            pass1 = ServletRequestUtils.getStringParameter(request, "password");
            passVerify = ServletRequestUtils.getStringParameter(request, "passwordVerify");
        }
        catch (ServletRequestBindingException ex)
        {
            logger.error(ex);
        }
        
        if(Tools.getInstance().stringIsEmpty(username))
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("email", email);
            
            return new ModelAndView("setup/step1", mm);
        }
        else if(Tools.getInstance().stringIsEmpty(email))
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("username", username);
            
            return new ModelAndView("setup/step1", mm);
        }
        else if(pass1 != null && !pass1.equals(passVerify))
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("email", email);
            mm.addAttribute("username", username);
            
            return new ModelAndView("setup/step1", mm);
        }
        else
        {
            List<UserRole> roles = userRoleService.getAllUserRoles();
            if(Tools.getInstance().isEmpty(roles))
            {
                UserRole ur1 = EntityFactory.createUserRole("ROLE_ADMINISTRATOR");
                UserRole ur2 = EntityFactory.createUserRole("ROLE_USER");
                userRoleService.createUserRole(ur1);
                userRoleService.createUserRole(ur2);
                roles = new ArrayList<>();
                roles.add(ur1);
                roles.add(ur2);
            }
            User u = EntityFactory.createUser(username, pass1, username, email, roles);
            
            User u2 = EntityFactory.createUser("system", null, "system", "webmias@fi.muni.cz", roles);
            
            u.setPassword(Tools.getInstance().SHA1(u.getPassword()));

            userService.createUser(u);
            userService.createUser(u2);
            
            revisionService.createRevision(
                    EntityFactory
                            .createRevision(revisionValue, 
                                    "Default revision created by install process.")
            );
            
            
            // config should be outside controller, but its run only once per
            // aplication setup so it does not matter somehow
            InputStream is = InstallController.class.getClassLoader().getResourceAsStream("other/sample-config-1.1SNAP.xml");
            
            Configuration config = null;
            try
            {
                String xmlContent = IOUtils.toString(is);
                config = EntityFactory.createConfiguration(xmlContent, 
                        "sample-config-1.1SNAP", 
                        "Default configuration created by install process.");
                logger.info("Loaded sample config");
                logger.info(xmlContent);
            }
            catch(IOException ex)
            {
                logger.error(ex);
            }
            finally
            {
                if(is != null)
                {
                    try
                    {
                        is.close();
                    }
                    catch(IOException ex)
                    {
                        logger.error(ex);
                    }
                }
            }
            
            if(config != null)
            {
                configurationService.createConfiguration(config);
            }
            
            for(AnnotationValue av : provideAnnotaionValues())
            {
                annotationValueSerivce.createAnnotationValue(av);
            }
            
            mailService.sendMail(null, email, "Installation is now complete.", 
                    "Installation of MathCanEval application has ended. Below are user credentials submited in setup process.\n"
            +"Username: "+username+"\nPassword: "+passVerify);
            return new ModelAndView("redirect:/");
        }
    }
    
    private AnnotationValue[] provideAnnotaionValues()
    {
        AnnotationValue[] values = new AnnotationValue[6];
        values[0] = prepareAnnotationValue(AnnotationValue.Type.FORMULA, 
                    "#formulaRemove", "Formula should be removed from the database as the formula markup is"
                            + " invalid (i.e. the formula MathML is not valid XML)",
                    "remove", "danger", Integer.valueOf("10"));
        
        values[1] = prepareAnnotationValue(AnnotationValue.Type.FORMULA, 
                    "#formulaMeaningless", "Formula is valid MathML but is meaningless, for example "
                            + "some text improperly encoded as string of math variables.",
                    "trash", "danger", Integer.valueOf("9"));
        
        values[2] = prepareAnnotationValue(AnnotationValue.Type.CANONICOUTPUT, 
                    "#isValid", "Canonicalization result is correct according to current implementation of the canonicalization functions, "
                            + "i.e. all the canonicalization methods were applied correctly and the result is valid MathML "
                            + "with the same meaning of the input formula.",
                    "ok", "success", Integer.valueOf("10"));
        values[3] = prepareAnnotationValue(AnnotationValue.Type.CANONICOUTPUT, 
                    "#isInvalid", "Implemented canonicalization methods were not applied correctly according their specification, or the "
                            + "result is not valid MathML, or the result formula has different meaning as the input one.",
                    "flag", "warning", Integer.valueOf("9"));
        values[4] = prepareAnnotationValue(AnnotationValue.Type.CANONICOUTPUT, 
                    "#uncertain", "The annotator is not able to decide the canonicalization was applied correctly.",
                    "question-sign", "info", Integer.valueOf("8"));
        values[5] = prepareAnnotationValue(AnnotationValue.Type.CANONICOUTPUT, 
                    "#removeResult", "The result should be removed for any reason.",
                    "remove", "danger", Integer.valueOf("7"));
        
        return values;
    }
    
    @RequestMapping("/update")
    public ModelAndView setupCanonicOutputs()
    {
        canonicOutputService.recalculateHashes();
        
        User u2 = EntityFactory.createUser("system", null, "system", "webmias@fi.muni.cz", userRoleService.getAllUserRoles());
        userService.createUser(u2);
        
        return new ModelAndView("redirect:/");
    }
    
    
    private AnnotationValue prepareAnnotationValue(AnnotationValue.Type type,
            String value,
            String description,
            String icon,
            String label,
            Integer priority)
    {
        AnnotationValue av = new AnnotationValue();
        av.setDescription(description);
        av.setIcon(icon);
        av.setLabel(label);
        av.setPriority(priority);
        av.setType(type);
        av.setValue(value);
        
        return av;
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = "/reset/",method = RequestMethod.GET)
    public ModelAndView handleReset()
    {
//        Configuration cfg = new Configuration();
//        cfg.setProperty(null, null)
        return new ModelAndView("redirect:/");
    }
}
