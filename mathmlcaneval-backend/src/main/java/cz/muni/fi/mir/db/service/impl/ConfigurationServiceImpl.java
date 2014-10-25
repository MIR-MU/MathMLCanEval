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
 * @author Empt
 */
@Service(value = "configurationService")
public class ConfigurationServiceImpl implements ConfigurationService
{
    @Autowired private ConfigurationDAO configurationDAO;

    @Override
    @Transactional(readOnly = false)
    public void createConfiguration(Configuration configuration)
    {
        configurationDAO.create(configuration);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateConfiguration(Configuration configuration)
    {
        configurationDAO.update(configuration);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteConfiguration(Configuration configuration)
    {
        configurationDAO.delete(configuration.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Configuration getConfigurationByID(Long id)
    {
        return configurationDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Configuration> getAllCofigurations()
    {
        return configurationDAO.getAllCofigurations();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Configuration> getBySubstringNote(String subString)
    {
        return configurationDAO.getBySubstringNote(subString);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Configuration> findyByName(String name)
    {
        return configurationDAO.findyByName(name);
    }
    
}
