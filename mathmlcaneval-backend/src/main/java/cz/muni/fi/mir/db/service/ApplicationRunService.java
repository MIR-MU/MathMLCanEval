/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface ApplicationRunService 
{
    void createApplicationRun(ApplicationRun applicationRun);
    void updateApplicationRun(ApplicationRun applicationRun);
    
    /**
     * Method deletes given application run from database. Based on other inputs method does following:
     * <ul>
     * <li> if <b>deleteFormulas</b> is set to true method deletes all formulas created by this application run. The vale <b>deleteCanonicOutputs</b> is not taken into account.</li>
     * <li> if <b>deleteFormulas</b> is set to false and <b>deleteCanonicOutputs</b> is set to false then for each canonic output created during the run is set application to null.
     * This combo also removes the information when the output, or formula was created.</li>
     * <li> if <b>deleteCanonicOutputs</b> is set to false and <b>deleteCanonicOutputs</b> is set to true then all canonic outputs created during the run are deleted</li>
     * </ul>
     * 
     * The reason why there is input value for formulas, is the initial import, which creates application run. Thus this options adds comfortable option for deletion of these formulas.
     * 
     * @param applicationRun to be deleted
     * @param deleteFormulas flags option how deletion is performed on formulas
     * @param deleteCanonicOutputs flags option how deletion is performed on canonic outputs
     */
    void deleteApplicationRun(ApplicationRun applicationRun,
            boolean deleteFormulas, 
            boolean deleteCanonicOutputs);
    
    /**
     * Method works same way as {@link #deleteApplicationRun(cz.muni.fi.mir.db.domain.ApplicationRun, boolean, boolean) }. The only difference is that
     * this call creates long runnable thread in background, because removal of e.g. 4000 formulas within application run creates lot of calls, which 
     * take some time to perform.
     * @param applicationRun to be deleted
     * @param deleteFormulas flags option how deletion is performed on formulas
     * @param deleteCanonicOutputs flags option how deletion is performed on canonic outputs
     */
    void deleteApplicationRunInTask(ApplicationRun applicationRun,
            boolean deleteFormulas, 
            boolean deleteCanonicOutputs);
    
    ApplicationRun getApplicationRunByID(Long id);
    
    List<ApplicationRun> getAllApplicationRuns();
    List<ApplicationRun> getAllApplicationRunsFromRange(int start, int end);
    List<ApplicationRun> getAllApplicationRunsByUser(User user);
    List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision);
    List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration);
}
