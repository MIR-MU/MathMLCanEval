/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Configuration;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface ConfigurationDAO
{
    void createConfiguration(Configuration configuration);
    void updateConfiguration(Configuration configuration);
    void deleteConfiguration(Configuration configuration);
    
    Configuration getConfigurationByID(Long id);
    
    List<Configuration> getAllCofigurations();
    List<Configuration> getBySubstringNote(String subString);
    List<Configuration> findyByName(String name);
}
