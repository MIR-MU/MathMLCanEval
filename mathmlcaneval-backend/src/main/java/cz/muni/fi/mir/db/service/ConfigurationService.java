/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.Configuration;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ConfigurationService
{

    /**
     * Method creates configuration.
     *
     * @param configuration to be created
     * @throws IllegalArgumentException if input is null
     */
    void createConfiguration(Configuration configuration) throws IllegalArgumentException;

    /**
     * Method updates configuration.
     *
     * @param configuration to be updated
     * @throws IllegalArgumentException if input is null, or does not have valid
     * id.
     */
    void updateConfiguration(Configuration configuration) throws IllegalArgumentException;

    /**
     * Method deletes configuration.
     *
     * @param configuration to be deleted
     * @throws IllegalArgumentException if configuration is null or does not
     * have valid id.
     */
    void deleteConfiguration(Configuration configuration) throws IllegalArgumentException;

    /**
     * Method obtains configuration by its id.
     *
     * @param id of configuration to be obtained
     * @return configuration with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or out of valid range.
     */
    Configuration getConfigurationByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains all configurations out of database.
     *
     * @return all configurations in form of list. If there are no
     * configurations yet, then empty list is returned.
     */
    List<Configuration> getAllCofigurations();
}
