package cz.muni.fi.mir.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.forms.UserRoleForm;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;

/**
 * This class serves for handling requests for User objects with requests starting with <b>/user</b>
 * path.
 * @author Dominik Szalai
 * @author Robert Siska
 */
@Controller
@RequestMapping(value ="/user")
public class UserController
{
    @Autowired private UserService userService;
    @Autowired private UserRoleService userRoleService;
    @Autowired private Mapper mapper;
    @Autowired private SecurityContextFacade securityContext;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserController.class);
    
    
    @RequestMapping(value = {"/","/list","/list/"},method = RequestMethod.GET)
    public ModelAndView list()
    {        
        ModelMap mm = new ModelMap();
        mm.addAttribute("userList", userService.getAllUsers());
        
        return new ModelAndView("user_list",mm);
    }
    
    @RequestMapping("/login/")
    public ModelAndView handleLogin()
    {
        return new ModelAndView("login");
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/create","/create/"}, method = RequestMethod.GET)
    public ModelAndView handleRegister()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userForm", new UserForm());
        mm.addAttribute("userRolesFormList", userRoleService.getAllUserRoles());
        
        return new ModelAndView("user_create",mm);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/create","/create/"}, method = RequestMethod.POST)
    public ModelAndView createUser(
            @ModelAttribute("userForm") @Valid UserForm user,
            BindingResult result,
            Model model)
    {
        if (result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            System.out.println(user);
            mm.addAttribute("userForm", user);
            mm.addAttribute(model);
            mm.addAttribute("userRolesFormList", userRoleService.getAllUserRoles());
            return new ModelAndView("user_create",mm);
        }
        

        //UserRole userRole = userRoleService.getUserRoleByName("ROLE_USER");
        User u = mapper.map(user, User.class);
        
        u.setPassword(Tools.getInstance().SHA1(u.getPassword()));

        
        userService.createUser(u);

        return new ModelAndView("redirect:/user/list/");
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(EntityFactory.createUser(id));
        
        return new ModelAndView("redirect:/user/list/");
    }
    
    @RequestMapping(value= {"/profile","/profile/"},method = RequestMethod.GET)
    public ModelAndView showProfile()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userForm", mapper.map(userService.getUserByUsername(securityContext.getLoggedUser()), UserForm.class));
        
        List<UserRoleForm> userRolesFormList = new ArrayList<>();
        List<UserRole> temp = userRoleService.getAllUserRoles();
        for(UserRole ur : temp)
        {
            userRolesFormList.add(mapper.map(ur,UserRoleForm.class));
        }
        
        mm.addAttribute("userRolesFormList",userRolesFormList);
        
        
        return new ModelAndView("user_edit",mm);
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value={"/edit/{id}","/edit/{id}/"},method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        User u = userService.getUserByID(id);
        UserForm uf = mapper.map(u,UserForm.class);
        mm.addAttribute("userForm", uf);
        
        
        // nastudovat ako funguje cache
        //http://docs.spring.io/spring/docs/4.0.0.RC1/spring-framework-reference/html/cache.html
        // userrole sa totiz nemeni casto a je dobre to mat v cache ako furt selectit z db
//        List<UserRoleForm> userRolesFormList = new ArrayList<>();
//        List<UserRole> temp = userRoleService.getAllUserRoles();
//        for(UserRole ur : temp)
//        {
//            userRolesFormList.add(mapper.map(ur,UserRoleForm.class));
//        }
        // roles does not have to be converted into forms
        mm.addAttribute("userRolesFormList",userRoleService.getAllUserRoles());
        
        return new ModelAndView("user_edit",mm);        
    }
    
    /**
     * handles also update of user profile
     * @param userForm
     * @param result
     * @param model
     * @return 
     */
    @Secured("ROLE_USER")
    @RequestMapping(value={"/edit","/edit/"},method = RequestMethod.POST)
    public ModelAndView editUserSubmit(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result, Model model, HttpServletRequest request)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("userForm", userForm);
            mm.addAttribute(model);
            
            mm.addAttribute("userRolesFormList",userRoleService.getAllUserRoles());
            
            return new ModelAndView("user_edit",mm);
        }
        else
        {
            User u = mapper.map(userForm,User.class);
            
            u.setPassword(Tools.getInstance ().SHA1(u.getPassword()));
            
            if (request.isUserInRole("ROLE_ADMINISTRATOR"))
            {
                //ak daco bude treba zatial neviem co.
            }
            else
            {
                User uDB = userService.getUserByID(userForm.getId());
                // because user cannot change his roles
                // they are not present in form therefore
                // submited form will contain empty list
                // where should be at least 1 user role
                // name ROLE_USER. So all we have to do 
                // is to select them from DB assign to user
                // and then just stored modified user
                u.setUserRoles(uDB.getUserRoles());
                
                
                if(Tools.getInstance().stringIsEmpty(u.getPassword()))
                {
                    // we didnt fill password field and
                    // password verify (it would fail upon
                    // validation above). it means user did not
                    // changed his password so we have reselect
                    // it from database (or "cached" object)
                    u.setPassword(uDB.getPassword());
                }
            }
            if ((request.isUserInRole("ROLE_ADMINISTRATOR") || u.getUsername().equals(securityContext.getLoggedUser())))
            {
                userService.updateUser(u);
            } else
            {
                logger.info(String.format("Blocked unauthorized editing of user %s triggered by user %s.", u.getUsername(), securityContext.getLoggedUser()));
            }
            
            return new ModelAndView("redirect:/user/list/");
        }
    }
    
    
    @RequestMapping(value={"/list/{filters}","/list/{filters}/"},method = RequestMethod.GET)
    public ModelAndView filterList(@MatrixVariable(pathVar = "filters") Map<String,List<String>> filters)
    {
        ModelMap mm = new ModelMap();
        
        if(filters.containsKey("role_name"))
        {
            mm.addAttribute("userList", userService.getUsersByRole(userRoleService.getUserRoleByName(filters.get("role_name").get(0))));
        }
        else
        {
            mm.addAttribute("userList", userService.getAllUsers());
        }
        
        return new ModelAndView("user_list",mm);
    }
}
