/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.UserRole;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserRoleService
{
    /**
     * 
     * @param userRole
     * @throws IllegalArgumentException 
     */
    void createUserRole(UserRole userRole) throws IllegalArgumentException;
    
    /**
     * 
     * @param userRole
     * @throws IllegalArgumentException 
     */
    void updateUserRole(UserRole userRole) throws IllegalArgumentException;
    
    /**
     * 
     * @param userRole
     * @throws IllegalArgumentException 
     */
    void deleteUserRole(UserRole userRole) throws IllegalArgumentException;
    
    /**
     * 
     * @param id
     * @return
     * @throws IllegalArgumentException 
     */
    UserRole getUserRoleByID(Long id) throws IllegalArgumentException;
    
    /**
     * 
     * @param roleName
     * @return
     * @throws IllegalArgumentException 
     */
    UserRole getUserRoleByName(String roleName) throws IllegalArgumentException;
    
    /**
     * 
     * @return 
     */
    List<UserRole> getAllUserRoles();     
}
