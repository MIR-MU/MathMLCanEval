/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Empt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})
@TestExecutionListeners({
    DirtiesContextTestExecutionListener.class, 
    DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserTest
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserTest.class);
    
    
    @Autowired private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    private List<UserRole> roles = new ArrayList<>(3);
    private List<User> users = new ArrayList<>(3);
    private static final Long ID = new Long(1);
    
    @Before
    public void init()
    {        
        roles = DataTestTools.provideUserRolesList();
        
        
        for(UserRole ur : roles)
        {
            userRoleService.createUserRole(ur);
        }
        
        // now they all should have assigned ID so we sort them
        Collections.sort(roles,TestTools.userRoleComparator);
        
        users = DataTestTools.provideUserRoleListSpecific(roles);
    }
    
    
    @Test
    public void createAndGetUser()
    {
        logger.info("Running UserTest#createAndGetUser()");
        
        userService.createUser(users.get(0));
        
        User result = userService.getUserByID(ID);
        
        
        assertNotNull("User object was not created.",result);
        
        deepCompare(users.get(0), result);
    }
    
    @Test
    public void testUpdateUser()
    {
        logger.info("Running UserTest#createAndGetUser()");
        
        userService.createUser(users.get(0));
        
        
        User result = userService.getUserByID(ID);
        assertNotNull("User object was not created.", result);
        
        result.setPassword("zmenene heslo");
        result.setUsername("novyusername");
        result.setRealName("pepa z depa");
        List<UserRole> rolesz = new ArrayList<>(result.getUserRoles());
        
        rolesz.remove(2);
        rolesz.remove(1);
        result.setUserRoles(rolesz);
        
        userService.updateUser(result);
        
        User updatedUser = userService.getUserByID(ID);
        
        
        deepCompare(updatedUser, result);
    }
    
    @Test
    public void testDelete()
    {
        logger.info("Running UserTest#testDelete()");
        
        userService.createUser(users.get(0));
        
        User result = userService.getUserByID(ID);
        assertNotNull("User object was not created.", result);
        
        userService.deleteUser(result);
        
        assertNull("User object was not deleted.", userService.getUserByID(ID));
        assertEquals(TestTools.ERROR_LIST_SIZE, 0, userService.getAllUsers().size());
    }
    
    @Test
    public void testFindUserByRealName()
    {
        logger.info("Running UserTest#testFindUserByRealName()");
        
        for(User u : users)
        {
            userService.createUser(u);
        }
        
        List<User> rList = userService.findUserByRealName("tretie");
        assertEquals("result should be one",1,rList.size());
        
        deepCompare(users.get(1), rList.get(0));
        rList.clear();
        
        rList = userService.findUserByRealName("viac");
        
        assertEquals("result should be 2", 2,rList.size());
    }
    
    @Test
    public void testGetUserByUsername()
    {
        logger.info("Running UserTest#testGetUserByUsername()");
        userService.createUser(users.get(0));
        
        assertNotNull("not created",userService.getUserByID(ID));
        
        User result = userService.getUserByUsername(users.get(0).getUsername());
        
        deepCompare(users.get(0), result);
    }
    
    
    @Test
    public void testGetAll()
    {
        logger.info("Running UserTest#testGetAll()");
        for(User u : users)
        {
            userService.createUser(u);
        }
        
        List<User> resultList = userService.getAllUsers();
        
        Collections.sort(resultList,TestTools.userComparator);
        Collections.sort(users,TestTools.userComparator);
        assertEquals("not same count",users.size(),resultList.size());
        
        for(int i =0 ; i < resultList.size();i++)
        {
            deepCompare(users.get(i), resultList.get(i));
        }     
    }
    
    
    @Test
    public void testGetByRole()
    {
        logger.info("Running UserTest#testGetAll()");
        for(User u : users)
        {
            userService.createUser(u);
        }
        
        UserRole temp = EntityFactory.createUserRole("ROLE_TEST");
        userRoleService.createUserRole(temp);
        
        
        List<User> list1 = userService.getUsersByRole(roles.get(1));
        List<User> list2 = userService.getUsersByRole(roles.get(2));
        
        // anon
        assertEquals("not zero",0,userService.getUsersByRole(temp).size());
        
        // user
        assertEquals("should be two",2,list1.size());
        
        // admin
        assertEquals("should be two",1,list2.size());
    }
    
    /**
     * TODO
     */
    @Test 
    public void testGetByRoles()
    {
        logger.info("Running UserTest#testGetByRoles()");
        List<User> temp = DataTestTools.provideUserRoleListGeneral(roles);
        temp.add(EntityFactory.createUser("username4", "password4", "real name4", "example4@example.com", roles.subList(1, 3)));
        for(User u : temp)
        {
            userService.createUser(u);
        }
        
//        List<User> result = userService.getUsersByRoles(roles.subList(1, 3));
//        
//        for(User u : result)
//        {
//            System.out.println(u.getId());
//        }
    }
    
    @Test
    public void testGetByEmail()
    {
        logger.info("Running UserTest#testGetByEmail()");
        for(User u : users)
        {
            userService.createUser(u);
        }
        
        User u1 = userService.getUserByEmail("example1@example.com");
        User u2 = userService.getUserByEmail("example3@example.com");
        
        assertNotNull("User was not created", u1);
        assertNotNull("User was not created", u2);
        
        deepCompare(users.get(0), u1);
        deepCompare(users.get(2), u2);
    }
    
    
    private void deepCompare(User expected, User actual)
    {
        assertEquals("id not same",expected.getId(),actual.getId());
        assertEquals("username not same", expected.getUsername(),actual.getUsername());
        assertEquals("real naem not same", expected.getRealName(),actual.getRealName());
        assertEquals("pass not same", expected.getPassword(),actual.getPassword());
        assertEquals("email not same",expected.getEmail(),actual.getEmail());
        assertTrue("roles are not the same",(expected.getUserRoles().containsAll(actual.getUserRoles()) &&
                actual.getUserRoles().containsAll(expected.getUserRoles())));
    }    
}
