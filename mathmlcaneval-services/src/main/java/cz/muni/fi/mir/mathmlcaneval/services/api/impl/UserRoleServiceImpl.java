/*
 * Copyright 2015 Math.
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

import cz.muni.fi.mir.mathmlcaneval.api.UserRoleService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import cz.muni.fi.mir.mathmlcaneval.database.UserRoleDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class UserRoleServiceImpl implements UserRoleService
{
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public void create(UserRoleDTO userRoleDTO) throws IllegalArgumentException
    {
        if (userRoleDTO == null)
        {
            throw new IllegalArgumentException("UserRole is null.");
        }
        if (StringUtils.isEmpty(userRoleDTO.getRoleName()))
        {
            throw new IllegalArgumentException("UserRole has no roleName.");
        }
        if (userRoleDTO.getId() != null)
        {
            throw new IllegalArgumentException("UserRole already has an id.");
        }

        UserRole ur = mapper.map(userRoleDTO, UserRole.class);
        userRoleDAO.create(ur);
        userRoleDTO.setId(ur.getId());
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public void update(UserRoleDTO userRoleDTO) throws IllegalArgumentException
    {
        if (userRoleDTO == null)
        {
            throw new IllegalArgumentException("UserRole is null.");
        }
        if (StringUtils.isEmpty(userRoleDTO.getRoleName()))
        {
            throw new IllegalArgumentException("UserRole has no roleName.");
        }
        if (userRoleDTO.getId() == null)
        {
            throw new IllegalArgumentException("UserRole does not have valid id.");
        }

        userRoleDAO.update(mapper.map(userRoleDTO, UserRole.class));
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public void delete(UserRoleDTO userRoleDTO) throws IllegalArgumentException
    {
        if (userRoleDTO == null)
        {
            throw new IllegalArgumentException("UserRole is null.");
        }
        if (userRoleDTO.getId() == null)
        {
            throw new IllegalArgumentException("UserRole does not have valid id.");
        }

        userRoleDAO.delete(userRoleDTO.getId());
        userRoleDTO.setId(null);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public UserRoleDTO getByID(Long id) throws IllegalArgumentException
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Given id is null.");
        }

        return mapper.map(userRoleDAO.getByID(id), UserRoleDTO.class);

    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public UserRoleDTO getByName(String roleName) throws IllegalArgumentException
    {
        if (StringUtils.isEmpty(roleName))
        {
            throw new IllegalArgumentException("Given roleName is empty.");
        }

        return mapper.map(userRoleDAO.getByName(roleName), UserRoleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_ADMINISTRATOR")
    public List<UserRoleDTO> getAll()
    {
        return mapper.mapList(userRoleDAO.getAll(), UserRoleDTO.class);
    }
}
