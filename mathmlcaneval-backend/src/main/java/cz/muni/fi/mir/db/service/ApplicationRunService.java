package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
public interface ApplicationRunService
{

    /**
     * Method creates given application run inside database.
     *
     * @param applicationRun application run to be created.
     * @param withFlush if create should be immediately run with flush operation
     * @throws IllegalArgumentException if applicationRun is null
     */
    void createApplicationRun(ApplicationRun applicationRun, boolean withFlush) throws IllegalArgumentException;

    /**
     * Method updated given application run inside database.
     *
     * @param applicationRun to be updated
     * @throws IllegalArgumentException if application run is null or does not
     * have valid id (is null or lass than one)
     */
    void updateApplicationRun(ApplicationRun applicationRun) throws IllegalArgumentException;

    /**
     * Method deletes given application run from database. Based on other inputs
     * method does following:
     * <ul>
     * <li> if <b>deleteFormulas</b> is set to true method deletes all formulas
     * created by this application run. The vale <b>deleteCanonicOutputs</b> is
     * not taken into account.</li>
     * <li> if <b>deleteFormulas</b> is set to false and
     * <b>deleteCanonicOutputs</b> is set to false then for each canonic output
     * created during the run is set application to null. This combo also
     * removes the information when the output, or formula was created.</li>
     * <li> if <b>deleteCanonicOutputs</b> is set to false and
     * <b>deleteCanonicOutputs</b> is set to true then all canonic outputs
     * created during the run are deleted</li>
     * </ul>
     *
     * The reason why there is input value for formulas, is the initial import,
     * which creates application run. Thus this options adds comfortable option
     * for deletion of these formulas.
     *
     * @param applicationRun to be deleted
     * @param deleteFormulas flags option how deletion is performed on formulas
     * @param deleteCanonicOutputs flags option how deletion is performed on
     * canonic outputs
     * @throws IllegalArgumentException if application run is null or does not
     * have valid id (is null or lass than one)
     */
    void deleteApplicationRun(ApplicationRun applicationRun,
            boolean deleteFormulas,
            boolean deleteCanonicOutputs) throws IllegalArgumentException;

    /**
     * Method obtains given application run based on its id.
     *
     * @param id of application run to be obtained
     * @return application run with given id, null if there is no such
     * application run
     * @throws IllegalArgumentException if id is null or lass than one
     */
    ApplicationRun getApplicationRunByID(Long id) throws IllegalArgumentException;

    /**
     * TODO PAGINATION + rename to getFromRange Method obtains all application
     * runs from database.
     *
     * @return all application runs from database, empty list if there are no
     * runs yet.
     */
    List<ApplicationRun> getAllApplicationRuns();
}
