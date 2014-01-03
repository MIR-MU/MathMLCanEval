/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service;

import cz.muni.fi.mir.domain.Configuration;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface ConfigurationService
{
    void createConfiguration(Configuration configuration);
    void updateConfiguration(Configuration configuration);
    void deleteConfiguration(Configuration configuration);
    
    Configuration getConfigurationByID(Long id);
    
    List<Configuration> getAllCofigurations();
    List<Configuration> getBySubstringNote(String subString);
    List<Configuration> findyByName(String name);
}
