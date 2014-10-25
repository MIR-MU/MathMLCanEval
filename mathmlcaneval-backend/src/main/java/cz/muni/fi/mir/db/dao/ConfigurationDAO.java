package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Configuration;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
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
}
