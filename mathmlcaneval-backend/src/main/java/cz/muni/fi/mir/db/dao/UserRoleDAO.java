package cz.muni.fi.mir.db.dao;

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
public interface UserRoleDAO
{
    /**
     * Method creates given UserRole inside database.
     * @param userRole to be created
     */
    void createUserRole(UserRole userRole);
    
    /**
     * Method updates given UserRole inside database.
     * @param userRole to be updated
     */
    void updateUserRole(UserRole userRole);
    
    /**
     * Method deletes UserRole from database. Since entityManager
     * checks only ID, it is the only one required field to be set.
     * @param userRole to be deleted
     */
    void deleteUserRole(UserRole userRole);
    
    /**
     * Method fetches UserRole with given ID from database.
     * @param id of UserRole to be fetched
     * @return UserRole with given ID, null if there is no match
     */
    UserRole getUserRoleByID(Long id);
    
    /**
     * Method obtains UserRole based on its text value. Because
     * roleName is unique, only single result can be obtained
     * @param roleName of UserRole
     * @return UserRole with given roleName, null if there is no match.
     */
    UserRole getUserRoleByName(String roleName);
    
    /**
     * Method fetches all UserRoles from database in <b>DESCENDING</b> order.
     * @return List of all UserRoles, if there are no UserRole empty List is returned.
     */
    List<UserRole> getAllUserRoles();    
}
