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
 * @author Empt
 */
public interface UserService
{
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    
    User getUserByID(Long id);
    User getUserByUsername(String username);
    
    
    List<User> getAllUsers();
    List<User> getUsersByRole(UserRole userRole);
    List<User> findUserByRealName(String name);
}
