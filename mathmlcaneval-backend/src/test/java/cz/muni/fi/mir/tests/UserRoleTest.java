/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserRoleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
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
public class UserRoleTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserRoleTest.class);

    @Autowired
    UserRoleService userRoleService;
    private List<UserRole> roles = new ArrayList<>(3);
    private static final Long ID = new Long(1);
    
    @Before
    public void init()
    {
        roles = DataTestTools.provideUserRolesList();
    }

    @Test
    public void testCreateAndGet()
    {
        logger.info("Running UserRoleTest#testCreateAndGet()");

        userRoleService.createUserRole(roles.get(0));

        UserRole result = userRoleService.getUserRoleByID(ID);

        assertNotNull("UserRole object was not created.", result);

        deepCompare(roles.get(0), result);
    }

    @Test
    public void testGetUserRoleByName()
    {
        logger.info("Running UserRoleTest#testGetUserRoleByName()");

        userRoleService.createUserRole(roles.get(1));

        assertNotNull("UserRole object was not created.",userRoleService.getUserRoleByID(ID));

        UserRole result = userRoleService.getUserRoleByName(roles.get(1).getRoleName());

        deepCompare(roles.get(1), result);
    }

    @Test
    public void testDelete()
    {
        logger.info("Running UserRoleTest#testDelete()");
        
        userRoleService.createUserRole(roles.get(0));

        UserRole result = userRoleService.getUserRoleByID(ID);
        assertNotNull("UserRole object was not created.", result);

        userRoleService.deleteUserRole(result);

        assertNull("UserRole has not been deleted.", userRoleService.getUserRoleByID(ID));
    }

    @Test
    public void testUpdate()
    {
        logger.info("Running UserRoleTest#testUpdate()");
        
        userRoleService.createUserRole(roles.get(2));
        UserRole result = userRoleService.getUserRoleByID(ID);
        assertNotNull("UserRole object was not created.", result);

        result.setRoleName("ROLE_EDITED");
        userRoleService.updateUserRole(result);

        assertEquals("UserRole object was not updated.", result.getRoleName(), userRoleService.getUserRoleByID(ID).getRoleName());
    }

    @Test
    public void testGetAll()
    {
        logger.info("Running UserRoleTest#testGetAll()");        

        for (UserRole ur : roles)
        {
            userRoleService.createUserRole(ur);
        }

        // now they all should have assigned ID so we sort them
        Collections.sort(roles, TestTools.userRoleComparator);

        List<UserRole> result = userRoleService.getAllUserRoles();
        // we sort result just in case it is not sorted
        assertEquals(TestTools.ERROR_LIST_SIZE, roles.size(), result.size());

        Collections.sort(result, TestTools.userRoleComparator);

        for (int i = 0; i < result.size(); i++)
        {
            deepCompare(roles.get(i), result.get(i));
        }
    }

    private void deepCompare(UserRole expected, UserRole actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given UserRole does not have expected roleName.", expected.getRoleName(), actual.getRoleName());
    }
}
