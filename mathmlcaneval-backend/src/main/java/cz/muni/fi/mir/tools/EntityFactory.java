/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Empt
 */
public class EntityFactory
{
    
    /**
     * Method creates empty user
     * @return empty user
     */
    public static User createUser()
    {
        return new User();
    }
    
    /**
     * Method creates user with given input
     * @param username username of user
     * @param password user's <b>hashed</b> password
     * @param realName user's real name
     * @param roles roles of user to which (s)he belongs
     * @return user with set fields as were given on input
     */
    public static User createUser(String username, String password, String realName, List<UserRole> roles)
    {
        User u =createUser();
        u.setPassword(password);
        u.setRealName(realName);
        u.setUsername(username);
        u.setUserRoles(roles);
        
        return u;
    }
    
    /**
     * Method creates user with given input
     * @param username username of user
     * @param password hashed password of user
     * @param realName real name of user
     * @param userRole single role
     * @return user with fields set as input
     */
    public static User createUser(String username, String password, String realName, UserRole userRole)
    {
        User u =createUser();
        u.setPassword(password);
        u.setRealName(realName);
        u.setUsername(username);
        List<UserRole> rolez = new ArrayList<>();
        rolez.add(userRole);
        u.setUserRoles(rolez);
        
        return u;
    }
    
    /**
     * Method creates empty UserRole
     * @return new UserRole
     */
    public static UserRole createUserRole()
    {
        return new UserRole();
    }
    
    /**
     * Method creates UserRole with given name. By default, or at least it used to be,
     * all userRoles had to start with prefix <b>ROLE_</b> and then rest of role should be in 
     * uppercase. Therefore roleName is converted into uppercase just in case it was not. Method
     * however does not add required prefix. Use {@link #createUserRoleWithPrefix(java.lang.String) } instead.
     * @param roleName name of the role
     * @return UserRole with roleName as specified on input
     */
    public static UserRole createUserRole(String roleName)
    {
        UserRole ur = createUserRole();
        ur.setRoleName(roleName.toUpperCase());
        
        return ur;
    }
    
    
    /**
     * Method creates UserRole with given name. By default, or at least it used to be,
     * all userRoles had to start with prefix <b>ROLE_</b> and then rest of role should be in 
     * uppercase. Therefore roleName is converted into uppercase just in case it was not.
     * Method also adds <b>ROLE_</b> prefix as opposing to {@link #createUserRole(java.lang.String) }.
     * If roleName already starts with <b>ROLE_</b> it is not added again.
     * @param roleName name of role
     * @return UserRole with given rolename and with prefix in case it was not set.
     */
    public static UserRole createUserRoleWithPrefix(String roleName)
    {
        UserRole ur = createUserRole();
        if(roleName.startsWith("ROLE_"))
        {
            ur.setRoleName(roleName.toUpperCase());
        }
        else
        {
            ur.setRoleName("ROLE_"+roleName.toUpperCase());
        }        
        
        return ur;
    }
}
