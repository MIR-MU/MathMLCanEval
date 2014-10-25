/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.UserDAO;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDAO userDAO;
    
    //temp hack bez zistim ako docasne
    //vypnut security pri vytvarani admin uctu
    @Override
   // @Secured(value = "ROLE_ADMINISTRATOR")
    @Transactional(readOnly = false)
    public void createUser(User user)
    {
        userDAO.create(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username)
    {
        return userDAO.getUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUser(User user)
    {
        userDAO.update(user);
    }

    @Override
    @Secured(value = "ROLE_ADMINISTRATOR")
    @Transactional(readOnly = false)
    public void deleteUser(User user)
    {
        userDAO.delete(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByID(Long id)
    {
        return userDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers()
    {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(UserRole userRole)
    {
        return userDAO.getUsersByRole(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUserByRealName(String name)
    {
        return userDAO.findUserByRealName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByRoles(List<UserRole> roles)
    {
        return userDAO.getUsersByRoles(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email)
    {
        return userDAO.getUserByEmail(email);
    }
}
