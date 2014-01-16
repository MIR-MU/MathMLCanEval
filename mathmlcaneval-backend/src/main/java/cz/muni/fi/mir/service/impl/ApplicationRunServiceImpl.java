/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service.impl;

import cz.muni.fi.mir.dao.ApplicationRunDAO;
import cz.muni.fi.mir.domain.ApplicationRun;
import cz.muni.fi.mir.domain.Configuration;
import cz.muni.fi.mir.domain.Revision;
import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.service.ApplicationRunService;
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
}
