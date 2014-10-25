package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ApplicationRunDAO extends GenericDAO<ApplicationRun, Long>
{

    /**
     * Method creates ApplicationRun inside database and calls flush after.
     *
     * @param applicationRun ApplicationRun to be persisted.
     */
    void createApplicationRunWithFlush(ApplicationRun applicationRun);

    /**
     * Method obtains all ApplicationRuns from database in <b>DESCENDING</b>
     * order. Newer ApplicationRuns are therefore in front and the old ones are
     * in the back.
     *
     * @return List of all ApplicationRuns in descending order. If there are no
     * ApplicationRuns yet, empty List is returned.
     */
    List<ApplicationRun> getAllApplicationRuns();

    /**
     * Method returns number of canonicalizations for given application run.
     * Reason for this is that canonic outputs are set to lazy mode, thus not
     * loaded during obtaining from database.
     *
     * @param applicationRun to be checked
     * @return number of canonic outputs
     */
    Integer getNumberOfCanonicalizations(ApplicationRun applicationRun);
}
