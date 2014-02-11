/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "applicationRunService")
public class ApplicationRunServiceImpl implements ApplicationRunService
{

    @Autowired
    private ApplicationRunDAO applicationRunDAO;

    @Override
    @Transactional(readOnly = false)
    public void createApplicationRun(ApplicationRun applicationRun)
    {
        applicationRunDAO.createApplicationRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateApplicationRun(ApplicationRun applicationRun)
    {
        applicationRunDAO.updateApplicationRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteApplicationRun(ApplicationRun applicationRun)
    {
        applicationRunDAO.deleteApplicationRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationRun getApplicationRunByID(Long id)
    {
        return applicationRunDAO.getApplicationRunByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRuns()
    {
        return applicationRunDAO.getAllApplicationRuns();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsByUser(User user)
    {
        return applicationRunDAO.getAllApplicationRunsByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision)
    {
        return applicationRunDAO.getAllApplicationRunsByRevision(revision);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration)
    {
        return applicationRunDAO.getAllApplicationRunsByConfiguration(configuration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsFromRange(int start, int end)
    {
        if (start < 0)
        {
            throw new IllegalArgumentException("ERROR: start cannot be lower than zero. Start value is [" + start + "].");
        } 
        else if (end < 0)
        {
            throw new IllegalArgumentException("ERROR: end cannot be lower than zero. End value is [" + start + "].");
        } 
        else if (start > end)
        {
            throw new IllegalArgumentException("ERROR: end value cannot be lower than start value. Current value for end is [" + end + "] and for start [" + start + "]");
        } 
        else
        {
            return applicationRunDAO.getAllApplicationRunsFromRange(start, end);
        }
    }
}
