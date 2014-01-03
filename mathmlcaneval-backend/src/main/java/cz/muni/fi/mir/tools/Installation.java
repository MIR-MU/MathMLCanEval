/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.AnnotationFlagService;
import cz.muni.fi.mir.service.UserRoleService;
import cz.muni.fi.mir.service.UserService;
import cz.muni.fi.mir.wrappers.ApplicationContextWrapper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Empt
 */
public class Installation
{
    private static final ApplicationContextWrapper A = ApplicationContextWrapper.getInstance();
    
    private static UserService userService = A.getBean("userService");
    private static UserRoleService userRoleService = A.getBean("userRoleService");
    private static AnnotationFlagService annotationFlagService = A.getBean("annotationFlagService");
    public static void main(String[] args)
    {
        UserRole ur = EntityFactory.createUserRole("ROLE_USER");
        UserRole ur1 = EntityFactory.createUserRole("ROLE_ADMINISTRATOR");
        userRoleService.createUserRole(ur1);
        userRoleService.createUserRole(ur);
        
        List<UserRole> rolez = new ArrayList<>();
        rolez.add(ur);
        rolez.add(ur1);
        
        User u = null;
        try
        {
            u = EntityFactory.createUser("administrator", 
                    Tools.getInstance ().SHA1("heslo"), 
                    "Admin adminovic",
                    rolez);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            Logger.getLogger(Installation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        userService.createUser(u);
        
        annotationFlagService.createFlagAnnotation(EntityFactory.createAnnotaionFlag("PROPER_RESULT"));
        annotationFlagService.createFlagAnnotation(EntityFactory.createAnnotaionFlag("MIGHT_BE_PROPER"));
        annotationFlagService.createFlagAnnotation(EntityFactory.createAnnotaionFlag("WRONG"));
        annotationFlagService.createFlagAnnotation(EntityFactory.createAnnotaionFlag("CHECK_PLS"));
    }
}
