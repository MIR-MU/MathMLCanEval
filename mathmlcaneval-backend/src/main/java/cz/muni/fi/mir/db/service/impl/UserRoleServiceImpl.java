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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.dao.UserRoleDAO;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserRoleService;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "userRoleService")
@Transactional(readOnly = false)
public class UserRoleServiceImpl implements UserRoleService
{

    @Autowired
    UserRoleDAO userRoleDAO;

    @Override
    public void createUserRole(UserRole userRole) throws IllegalArgumentException
    {
        if (userRole == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }

        userRoleDAO.create(userRole);
    }

    @Override
    public void updateUserRole(UserRole userRole) throws IllegalArgumentException
    {
        InputChecker.checkInput(userRole);

        userRoleDAO.update(userRole);
    }

    @Override
    public void deleteUserRole(UserRole userRole) throws IllegalArgumentException
    {
        InputChecker.checkInput(userRole);

        userRoleDAO.delete(userRole.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserRole getUserRoleByID(Long id) throws IllegalArgumentException
    {
        if (id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was [" + id + "]");
        }

        return userRoleDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRole getUserRoleByName(String roleName) throws IllegalArgumentException
    {
        if (roleName == null || roleName.length() < 1)
        {
            throw new IllegalArgumentException("Given input string is empty. Expected role name but was ["+roleName+"]");
        }
        return userRoleDAO.getUserRoleByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRole> getAllUserRoles()
    {
        return userRoleDAO.getAllUserRoles();
    }
}
