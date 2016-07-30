/*
 * Copyright 2015 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.services.api.impl;

import cz.muni.fi.mir.mathmlcaneval.api.UserService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import cz.muni.fi.mir.mathmlcaneval.database.UserDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.User;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private Mapper mapper;
    @Autowired
    private UserDAO userDAO;

    private UserDTO systemUser;
    @Value("${application.system.username}")
    private String systemUsername;

    @PostConstruct
    private void init()
    {
        if (StringUtils.isEmpty(systemUsername))
        {
            throw new IllegalStateException("System username is not set.");
        }
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public void create(UserDTO user) throws IllegalArgumentException
    {
        checkUser(user);
        User u = mapper.map(user, User.class);
        userDAO.create(u);
        user.setId(u.getId());
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public void update(UserDTO user) throws IllegalArgumentException
    {
        checkUser(user);
        checkID(user);

        userDAO.update(mapper.map(user, User.class));
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public void delete(UserDTO user) throws IllegalArgumentException
    {
        checkNull(user);
        checkID(user);

        userDAO.delete(user.getId());
        user.setId(null);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public UserDTO getByID(Long id) throws IllegalArgumentException
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Given id is null.");
        }
        
        return mapper.map(userDAO.getById(id), UserDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public List<UserDTO> getAll()
    {
        return mapper.mapList(userDAO.getAll(), UserDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public UserDTO getSystemUser() throws RuntimeException
    {
        if (systemUser == null)
        {
            User u = userDAO.getByUsername(systemUsername);

            if (u == null)
            {
                throw new RuntimeException("There is no system user with specified username yet [" + systemUsername + "]");
            }
            else
            {
                systemUser = mapper.map(u, UserDTO.class);
            }
        }

        return systemUser;
    }

    private void checkUser(UserDTO user) throws IllegalArgumentException
    {
        checkNull(user);
        if (StringUtils.isEmpty(user.getEmail()))
        {
            throw new IllegalArgumentException("Given user does not have set their email.");
        }
        if (StringUtils.isEmpty(user.getPassword()))
        {
            throw new IllegalArgumentException("Given user does not have set their password.");
        }
        if (StringUtils.isEmpty(user.getRealName()))
        {
            throw new IllegalArgumentException("Given user does not have set their name.");
        }
        if (StringUtils.isEmpty(user.getUsername()))
        {
            throw new IllegalArgumentException("Given user does not have set their username.");
        }
        if (user.getRoles() == null || user.getRoles().isEmpty())
        {
            throw new IllegalArgumentException("Given user does not have set any role.");
        }
    }

    private void checkNull(UserDTO user) throws IllegalArgumentException
    {
        if (user == null)
        {
            throw new IllegalArgumentException("Given user to be saved is null.");
        }
    }

    private void checkID(UserDTO user) throws IllegalArgumentException
    {
        if (user.getId() == null)
        {
            throw new IllegalArgumentException("Given user does not have set their ID.");
        }
    }
}
