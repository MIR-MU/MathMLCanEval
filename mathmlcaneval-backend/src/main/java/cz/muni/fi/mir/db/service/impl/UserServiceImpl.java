/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.dao.UserDAO;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserService;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDAO userDAO;

    //temp hack bez zistim ako docasne
    //vypnut security pri vytvarani admin uctu
    @Override
    // @Secured(value = "ROLE_ADMINISTRATOR")
    @Transactional(readOnly = false)
    public void createUser(User user) throws IllegalArgumentException
    {
        if (user == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }

        userDAO.create(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUser(User user) throws IllegalArgumentException
    {
        InputChecker.checkInput(user);
        
        userDAO.update(user);
    }

    @Override
    @Secured(value = "ROLE_ADMINISTRATOR")
    @Transactional(readOnly = false)
    public void deleteUser(User user) throws IllegalArgumentException
    {
        InputChecker.checkInput(user);
        
        userDAO.delete(user.getId());
    }

    @Override
    public User getUserByUsername(String username) throws IllegalArgumentException
    {
        if(username == null || username.length() < 1)
        {
            throw new IllegalArgumentException("Given input string is empty. Should have at least character,but was ["+username+"]");
        }
        
        return userDAO.getUserByUsername(username);
    }

    @Override
    public User getUserByID(Long id) throws IllegalArgumentException
    {
        if (id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was [" + id + "]");
        }
        
        return userDAO.getByID(id);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userDAO.getAllUsers();
    }

    @Override
    public List<User> getUsersByRole(UserRole userRole) throws IllegalArgumentException
    {
        InputChecker.checkInput(userRole);
        
        return userDAO.getUsersByRole(userRole);
    }
}
