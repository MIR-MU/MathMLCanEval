/*
 * Copyright 2016 MIR@MU.
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

import cz.muni.fi.mir.mathmlcaneval.api.ConfigurationService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.ConfigurationDTO;
import cz.muni.fi.mir.mathmlcaneval.database.ConfigurationDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.Configuration;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService
{
    @Autowired
    private ConfigurationDAO configurationDAO;
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void createConfiguration(ConfigurationDTO configuration) throws IllegalArgumentException
    {
        Configuration c = mapper.map(configuration, Configuration.class);
        configurationDAO.create(c);
        configuration.setId(c.getId());
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void disableConfiguration(ConfigurationDTO configuration) throws IllegalArgumentException
    {
        Configuration c = configurationDAO.getByID(configuration.getId());
        c.setActive(Boolean.FALSE);
        configurationDAO.update(c);
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void enableConfiguration(ConfigurationDTO configuration) throws IllegalArgumentException
    {
        Configuration c = configurationDAO.getByID(configuration.getId());
        c.setActive(Boolean.TRUE);
        configurationDAO.update(c);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_USER")
    public ConfigurationDTO getConfigurationByID(Long id) throws IllegalArgumentException
    {
        return mapper.map(configurationDAO.getByID(id), ConfigurationDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_USER")
    public List<ConfigurationDTO> getAll()
    {
        return mapper.mapList(configurationDAO.getAll(), ConfigurationDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_USER")
    public List<ConfigurationDTO> getAllEnabled()
    {
        return mapper.mapList(configurationDAO.getAllEnabled(), ConfigurationDTO.class);
    }
}
