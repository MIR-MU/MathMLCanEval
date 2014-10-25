/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ConfigurationDAO;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.service.ConfigurationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        checkInput(configuration);
        
        configurationDAO.update(configuration);
    }

    @Override
    public void deleteConfiguration(Configuration configuration) throws IllegalArgumentException
    {
        checkInput(configuration);
        
        configurationDAO.delete(configuration.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Configuration getConfigurationByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) < 1)
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
    
    private void checkInput(Configuration configuration) throws IllegalArgumentException
    {
        if(configuration == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        if(configuration.getId() == null || Long.valueOf("0").compareTo(configuration.getId()) < 1)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+configuration.getId()+"]");
        }
    }
}
