/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service.impl;

import cz.muni.fi.mir.dao.UserDAO;
import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.UserService;
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
    
    @Override
    @Secured(value = "ROLE_ADMINISTRATOR")
    @Transactional(readOnly = false)
    public void createUser(User user)
    {
        userDAO.createUser(user);
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
        userDAO.updateUser(user);
    }

    @Override
    @Secured(value = "ROLE_ADMINISTRATOR")
    @Transactional(readOnly = false)
    public void deleteUser(User user)
    {
        userDAO.deleteUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByID(Long id)
    {
        return userDAO.getUserByID(id);
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
    
}
