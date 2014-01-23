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
    void deleteApplicationRun(ApplicationRun applicationRun);
    
    ApplicationRun getApplicationRunByID(Long id);
    
    List<ApplicationRun> getAllApplicationRuns();
    List<ApplicationRun> getAllApplicationRunsByUser(User user);
    List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision);
    List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration);
}
