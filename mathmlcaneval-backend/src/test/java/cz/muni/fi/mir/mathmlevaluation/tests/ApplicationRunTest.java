/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.tests;

import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Configuration;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Revision;
import cz.muni.fi.mir.mathmlevaluation.db.domain.User;
import cz.muni.fi.mir.mathmlevaluation.db.domain.UserRole;
import cz.muni.fi.mir.mathmlevaluation.db.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlevaluation.db.service.ConfigurationService;
import cz.muni.fi.mir.mathmlevaluation.db.service.RevisionService;
import cz.muni.fi.mir.mathmlevaluation.db.service.UserRoleService;
import cz.muni.fi.mir.mathmlevaluation.db.service.UserService;
import cz.muni.fi.mir.mathmlevaluation.tools.RandomString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    
    private RandomString randomString = new RandomString(25);

    private static final Long ID = new Long(1);
    private List<Configuration> configs = new ArrayList<>(3);
    private List<User> users = new ArrayList<>(2);
    private List<Revision> revs = new ArrayList<>(4);
    private List<ApplicationRun> appruns = new ArrayList<>(5);

    @Before
    public void init()
    {
        configs = DataTestTools.provideConfigurationList();
        for (Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }

        revs = DataTestTools.provideRevisions();
        for (Revision r : revs)
        {
            revisionService.createRevision(r);
        }

        List<UserRole> roles = DataTestTools.provideUserRolesList();
        for (UserRole ur : roles)
        {
            userRoleService.createUserRole(ur);
        }

        users = DataTestTools.provideUserRoleListGeneral(roles);

        for (User u : users)
        {
            userService.createUser(u);
        }

        appruns = DataTestTools.provideApplicationRuns(users, revs, configs);
    }

    @Test
    public void testCreateAndGetApplicationRun()
    {
        logger.info("Running ApplicationRunTest#testCreateAndGetApplicationRun()");

        applicationRunService.createApplicationRun(appruns.get(0),false);

        ApplicationRun result = applicationRunService.getApplicationRunByID(ID);

        assertNotNull("ApplicationRun was not created", result);

        deepCompare(appruns.get(0), result);
    }

    @Test
    public void testUpdateApplicationRun()
    {
        logger.info("Running ApplicationRunTest#testUpdateApplicationRun()");

        applicationRunService.createApplicationRun(appruns.get(0),false);

        ApplicationRun result = applicationRunService.getApplicationRunByID(ID);

        assertNotNull("ApplicationRun was not created", result);

        result.setNote("zmenenapoznamka");
        result.setRevision(revs.get(1));
        result.setConfiguration(configs.get(0));

        applicationRunService.updateApplicationRun(result);

        ApplicationRun update = applicationRunService.getApplicationRunByID(ID);

        assertNotNull("Updated ApplicationRun object could not be obtained", update);

        deepCompare(result, update);
    }

    @Test
    public void testDeleteApplicationRun()
    {
        logger.info("Running ApplicationRunTest#testDeleteApplicationRun()");
        applicationRunService.createApplicationRun(appruns.get(0),false);

        ApplicationRun result = applicationRunService.getApplicationRunByID(ID);

        assertNotNull("ApplicationRun was not created", result);

        applicationRunService.deleteApplicationRun(result,false,false);

        assertNull("ApplicationRun was not deleted", applicationRunService.getApplicationRunByID(ID));
    }

    @Test
    public void testGetAllApplicationRuns()
    {
        logger.info("Running ApplicationRunTest#testGetAllApplicationRuns()");
        for (ApplicationRun apRun : appruns)
        {
            applicationRunService.createApplicationRun(apRun,false);
        }

        List<ApplicationRun> result = applicationRunService.getAllApplicationRunsFromRange(0, 5,false);

        assertEquals(TestTools.ERROR_LIST_SIZE, appruns.size(), result.size());

        Collections.sort(appruns, TestTools.applicationRunComparator);
        Collections.sort(result, TestTools.applicationRunComparator);

        for (int i = 0; i < result.size(); i++)
        {
            deepCompare(appruns.get(i), result.get(i));
        }
    }

    private void deepCompare(ApplicationRun expected, ApplicationRun actual)
    {
        assertEquals("Given ApplicationRun does not have expected ID",expected.getId(), actual.getId());
        assertEquals("Given ApplicationRun does not have expected Configuration",expected.getConfiguration(), actual.getConfiguration());
        assertEquals("Given ApplicationRun does not have expected Note",expected.getNote(), actual.getNote());
        assertEquals("Given ApplicationRun does not have expected Revision",expected.getRevision(), actual.getRevision());
        assertEquals("Given ApplicationRun does not have expected StartTime",expected.getStartTime(), actual.getStartTime());
        assertEquals("Given ApplicationRun does not have expected StopTime",expected.getStopTime(), actual.getStopTime());
        assertEquals("Given ApplicationRun does not have expected User",expected.getUser(), actual.getUser());
    }
}
