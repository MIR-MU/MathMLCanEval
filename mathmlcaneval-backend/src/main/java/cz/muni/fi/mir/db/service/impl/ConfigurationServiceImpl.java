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

import cz.muni.fi.mir.db.dao.ConfigurationDAO;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.service.ConfigurationService;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "configurationService")
@Transactional(readOnly = false)
public class ConfigurationServiceImpl implements ConfigurationService
{
    @Autowired private ConfigurationDAO configurationDAO;

    @Override
    public void createConfiguration(Configuration configuration) throws IllegalArgumentException
    {
        if(configuration == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        configurationDAO.create(configuration);
    }

    @Override
    public void updateConfiguration(Configuration configuration) throws IllegalArgumentException
    {
        InputChecker.checkInput(configuration);
        
        configurationDAO.update(configuration);
    }

    @Override
    public void deleteConfiguration(Configuration configuration) throws IllegalArgumentException
    {
        InputChecker.checkInput(configuration);
        
        configurationDAO.delete(configuration.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Configuration getConfigurationByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+id+"]");
        }
        
        return configurationDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Configuration> getAllCofigurations()
    {
        return configurationDAO.getAllCofigurations();
    }
}
