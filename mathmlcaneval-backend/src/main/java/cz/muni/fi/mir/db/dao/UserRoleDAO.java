/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.UserRole;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface UserRoleDAO
{
    void createUserRole(UserRole userRole);
    void updateUserRole(UserRole userRole);
    void deleteUserRole(UserRole userRole);
    
    UserRole getUserRoleByID(Long id);
    UserRole getUserRoleByName(String roleName);
    
    List<UserRole> getAllUserRoles();    
}
