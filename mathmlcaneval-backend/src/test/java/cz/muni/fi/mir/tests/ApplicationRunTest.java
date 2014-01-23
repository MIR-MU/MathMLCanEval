/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 *
 * @author Empt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{
    "classpath:spring/applicationContext-test.xml"
})
@TestExecutionListeners(
{
    DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationRunTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ApplicationRunTest.class);

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private RevisionService revisionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationRunService applicationRunService;
    
    private static final Long ID = new Long(1);
    private List<Configuration> configs = new ArrayList<>(3);
    private List<User> users = new ArrayList<>(2);    
    private List<Revision> revs = new ArrayList<>(4);
    private List<ApplicationRun> appruns = new ArrayList<>(5);

    @Before
    public void init()
    {
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(true, true, true), "vsetko true", "vsetky hodnoty su true lebo kacka"));
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(true, true, false), "priemerny config 2:1", "vsetky hodnoty su true lebo medved"));
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(false, false, false), "vsetko false", "vsetky podnoty su true lebo holub"));
        for(Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }
        
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554a", "nahodna poznamka aby sa nieco naslo"));
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554b", "nahodna poznamka aby si nieco naslo"));
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554c", "nahodna poznamka aby so nieco naslo"));
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554d", "toto sa nenajde"));
        for(Revision r : revs)
        {
            revisionService.createRevision(r);
        }
        
        UserRole role = EntityFactory.createUserRole("ROLE_ADMINISTRATOR");
        userRoleService.createUserRole(role);
        
        
        users.add(EntityFactory.createUser("username2", "password2", "real name2", role));
        users.add(EntityFactory.createUser("username3", "password3", "real name3", role));
        for(User u : users)
        {
            userService.createUser(u);
        }
        
        appruns.add(EntityFactory.createApplicationRun("poznamka 1", new DateTime(2013, 02, 02, 12, 13), new DateTime(2013, 02, 02, 12, 14), users.get(0), revs.get(0),configs.get(0)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 2", new DateTime(2013, 02, 02, 13, 13), new DateTime(2013, 02, 02, 14, 14), users.get(0), revs.get(0),configs.get(0)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 3", new DateTime(2013, 02, 02, 14, 13), new DateTime(2013, 02, 02, 14, 14), users.get(1), revs.get(1),configs.get(0)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 4", new DateTime(2013, 02, 02, 15, 13), new DateTime(2013, 02, 02, 15, 14), users.get(1), revs.get(2),configs.get(1)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 5", new DateTime(2013, 02, 02, 16, 13), new DateTime(2013, 02, 02, 16, 14), users.get(0), revs.get(1),configs.get(2)));       
    }
    
    @Test
    public void testCreateAndGetApplicationRun()
    {
        logger.info("Running ApplicationRunTest#testCreateAndGetApplicationRun()");
        
        applicationRunService.createApplicationRun(appruns.get(0));
        
        ApplicationRun result = applicationRunService.getApplicationRunByID(ID);
        
        assertNotNull("meh", result);
        
        deepCompare(appruns.get(0), result);
    }

    
    @Test
    public void testUpdateApplicationRun()
    {
        logger.info("Running ApplicationRunTest#testUpdateApplicationRun()");
        
        applicationRunService.createApplicationRun(appruns.get(0));
        
        ApplicationRun result = applicationRunService.getApplicationRunByID(ID);
        
        assertNotNull("meh", result);
        
        result.setNote("zmenenapoznamka");
        result.setRevision(revs.get(1));
        result.setConfiguration(configs.get(0));
        
        applicationRunService.updateApplicationRun(result);
        
        ApplicationRun update = applicationRunService.getApplicationRunByID(ID);
        
        assertNotNull("mehe",update);
        
        deepCompare(result, update);
    }

    
    @Test
    public void testDeleteApplicationRun()
    {
        logger.info("Running ApplicationRunTest#testDeleteApplicationRun()");
        applicationRunService.createApplicationRun(appruns.get(0));
        
        ApplicationRun result = applicationRunService.getApplicationRunByID(ID);
        assertNotNull("muh",result);
        applicationRunService.deleteApplicationRun(result);
        assertNull("mih",applicationRunService.getApplicationRunByID(ID));
    }

    @Test
    public void testGetAllApplicationRuns()
    {
        logger.info("Running ApplicationRunTest#testGetAllApplicationRuns()");
        for(ApplicationRun apRun : appruns)
        {
            applicationRunService.createApplicationRun(apRun);
        }
        
        List<ApplicationRun> result = applicationRunService.getAllApplicationRuns();
        assertEquals("size",appruns.size(),result.size());
        Collections.sort(appruns,TestTools.applicationRunComparator);
        Collections.sort(result,TestTools.applicationRunComparator);
        
        for(int i =0;i<result.size();i++)
        {
            deepCompare(appruns.get(i), result.get(i));
        }
    }

    @Test
    public void testGetAllApplicationRunsByUser()
    {
        logger.info("Running ApplicationRunTest#testGetAllApplicationRunsByUser()");
        
        for(ApplicationRun apRun : appruns)
        {
            applicationRunService.createApplicationRun(apRun);
        }
        
        List<ApplicationRun> result = applicationRunService.getAllApplicationRunsByUser(users.get(0));
        assertEquals("size",3,result.size());
        for(ApplicationRun apr : result)
        {
            assertEquals(users.get(0),apr.getUser());
        }
    }

    @Test
    public void testGetAllApplicationRunsByRevision()
    {
        logger.info("Running ApplicationRunTest#testGetAllApplicationRunsByRevision()");
        
        for(ApplicationRun apRun : appruns)
        {
            applicationRunService.createApplicationRun(apRun);
        }
        
        List<ApplicationRun> result = applicationRunService.getAllApplicationRunsByRevision(revs.get(1));
        assertEquals("size",2,result.size());
        for(ApplicationRun apr : result)
        {
            assertEquals(revs.get(1),apr.getRevision());
        }
    }


    @Test
    public void testGetAllApplicationRunsByConfiguration()
    {
        logger.info("Running ApplicationRunTest#testGetAllApplicationRunsByConfiguration()");
        for(ApplicationRun apRun : appruns)
        {
            applicationRunService.createApplicationRun(apRun);
        }
        
        List<ApplicationRun> result = applicationRunService.getAllApplicationRunsByConfiguration(configs.get(0));
        List<ApplicationRun> result1 = applicationRunService.getAllApplicationRunsByConfiguration(configs.get(1));
        assertEquals("size",3,result.size());
        assertEquals("size",1,result1.size());
        
        for(ApplicationRun apr : result)
        {
            assertEquals(configs.get(0),apr.getConfiguration());
        }
    }
    
    private void deepCompare(ApplicationRun expected,ApplicationRun actual)
    {
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getConfiguration(), actual.getConfiguration());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getRevision(), actual.getRevision());
        assertEquals(expected.getStartTime(), actual.getStartTime());
        assertEquals(expected.getStopTime(), actual.getStopTime());
        assertEquals(expected.getUser(), actual.getUser());
    }
}
