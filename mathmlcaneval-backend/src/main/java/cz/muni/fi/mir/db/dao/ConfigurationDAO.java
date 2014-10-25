package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Configuration;
import java.util.List;

/**
 * The purpose of this interface is to provide basic CRUD operations and search
 * functionality on {@link cz.muni.fi.mir.db.domain.Configuration} objects
 * persisted inside any given database engine specified by configuration. Since
 * there might be some functionality that requires more operation calls, no
 * transaction management should be managed on this layer. Also no validation is
 * made on this layer so make sure you do not pass non-valid objects into
 * implementation of this DAO (Database Access Object) layer.
 *
 * @author Dominik Szalai
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface ConfigurationDAO extends GenericDAO<Configuration, Long>
{
    /**
     * Method fetches all Configurations out of database in <b>DESCENDING</b>
     * order.
     *
     * @return List of Configurations in descending order. If there are no
     * Configurations yet then empty List is returned.
     */
    List<Configuration> getAllCofigurations();

    /**
     * Method fetches all Configurations from database that have given subString
     * passed as method argument in note.
     *
     * @param subString to be found in Configuration note
     * @return List of Configurations having given subString in note. Empty List
     * if there are no such Configurations.
     */
    List<Configuration> getBySubstringNote(String subString);

    /**
     * Method used for searching between Configuration with aim to find
     * Configurations having given name in their name.
     *
     * @param name of Configuration to be found
     * @return List of Configurations containing given name in their name. Empty
     * List if there are no such Configurations.
     */
    List<Configuration> findyByName(String name);
}
