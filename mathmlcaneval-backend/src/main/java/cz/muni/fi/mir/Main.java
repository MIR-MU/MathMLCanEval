package cz.muni.fi.mir;


import cz.muni.fi.mir.dao.UserDAO;
import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.UserRoleService;
import cz.muni.fi.mir.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.wrappers.ApplicationContextWrapper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Empt
 */
public class Main
{
    private static final ApplicationContextWrapper A = ApplicationContextWrapper.getInstance();
    
    private static UserService userService = A.getBean("userService");
    private static UserRoleService userRoleService = A.getBean("userRoleService");
    
    public static void main(String[] args)
    {
        
       admin("hesloadministratora");
        
    }
    
    private static void admin(String _password)
    {
        UserRole ur = EntityFactory.createUserRole("ROLE_USER");
        UserRole ur1 = EntityFactory.createUserRole("ROLE_ADMINISTRATOR");
        userRoleService.createUserRole(ur1);
        userRoleService.createUserRole(ur);
        
        String password = null;
        try
        {
            password = Tools.SHA1(_password);
        } 
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<UserRole> rolez = new ArrayList<>();
        rolez.add(ur);
        rolez.add(ur1);
        
        User u = EntityFactory.createUser("emptak", password, "Dominik Szalai", rolez);
        
        userService.createUser(u);
    }
}
