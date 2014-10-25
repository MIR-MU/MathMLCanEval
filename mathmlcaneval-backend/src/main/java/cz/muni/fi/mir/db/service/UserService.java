/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserService
{
    /**
     * 
     * @param user
     * @throws IllegalArgumentException 
     */
    void createUser(User user) throws IllegalArgumentException;
    
    /**
     * 
     * @param user
     * @throws IllegalArgumentException 
     */
    void updateUser(User user) throws IllegalArgumentException;
    
    /**
     * 
     * @param user
     * @throws IllegalArgumentException 
     */
    void deleteUser(User user) throws IllegalArgumentException;
    
    /**
     * 
     * @param id
     * @return
     * @throws IllegalArgumentException 
     */
    User getUserByID(Long id) throws IllegalArgumentException;
    
    /**
     * 
     * @param username
     * @return
     * @throws IllegalArgumentException 
     */
    User getUserByUsername(String username) throws IllegalArgumentException;
   
    /**
     * 
     * @return 
     */    
    List<User> getAllUsers();
    
    /**
     * 
     * @param userRole
     * @return
     * @throws IllegalArgumentException 
     */
    List<User> getUsersByRole(UserRole userRole) throws IllegalArgumentException;
}
