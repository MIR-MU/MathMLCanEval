/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import java.util.List;

/**
 *  The purpose of this interface is to provide basic CRUD operations and search 
 * functionality on UserRole objects persisted inside any given database engine 
 * specified by configuration. Since there might be some functionality that requires
 * more operation calls, no transaction management should be managed on this layer.
 * Also no validation is made on this layer so make sure you do not pass non-valid
 * objects into implementation of this DAO (Database Access Object) layer.
 * 
 * @author Dominik Szalai
 * @author Robert Siska
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public interface UserDAO extends GenericDAO<User,Long>
{
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByRole(UserRole userRole);
    List<User> getUsersByRoles(List<UserRole> roles);
    List<User> findUserByRealName(String name);
}
