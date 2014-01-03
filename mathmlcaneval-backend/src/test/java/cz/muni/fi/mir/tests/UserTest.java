/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.UserRoleService;
import cz.muni.fi.mir.service.UserService;
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
    @Autowired private UserService userService;
    private List<UserRole> roles = new ArrayList<>(3);
    private static final Long ID = new Long(1);
    
    private User u = null;
    
    @Before
    public void init()
    {        
        roles.add(EntityFactory.createUserRole("ROLE_ANONYMOUS"));
        roles.add(EntityFactory.createUserRole("ROLE_USER"));
        roles.add(EntityFactory.createUserRole("ROLE_ADMINISTRATOR"));
        
        
        for(UserRole ur : roles)
        {
            userRoleService.createUserRole(ur);
        }
        
        // now they all should have assigned ID so we sort them
        Collections.sort(roles,TestTools.userRoleComparator);
        
        u = EntityFactory.createUser("username", "password", "real name", roles);
    }
    
    
    @Test
    public void createAndGetUser()
    {
        logger.info("Running UserTest#createAndGetUser()");
        
        userService.createUser(u);
        
        User result = userService.getUserByID(ID);
        
        
        assertNotNull("User object was not created.",result);
        
        deepCompare(u, result);
    }
    
    @Test
    public void testUpdateUser()
    {
        logger.info("Running UserTest#createAndGetUser()");
        
        userService.createUser(u);
        
        
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
        
        userService.createUser(u);
        
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
        
        u.setRealName("nahodne viac slovne meno");
        
        userService.createUser(u);
        userService.createUser(EntityFactory.createUser("username2", "password", "druhe viac aslovne cislo", roles));
        userService.createUser(EntityFactory.createUser("username3", "password", "dbezsd", roles));
        
        User result = userService.getUserByID(ID);
        assertNotNull("user has been not created", result);
        
        List<User> rList = userService.findUserByRealName("viac sl");
        assertEquals("result should be one",1,rList.size());
        
        deepCompare(u, rList.get(0));
        rList.clear();
        
        rList = userService.findUserByRealName("viac");
        
        assertEquals("result should be 2", 2,rList.size());
    }
    
    @Test
    public void testGetUserByUsername()
    {
        logger.info("Running UserTest#testGetUserByUsername()");
        userService.createUser(u);
        
        assertNotNull("not created",userService.getUserByID(ID));
        
        User result = userService.getUserByUsername("username");
        
        deepCompare(u, result);
    }
    
    
    @Test
    public void testGetAll()
    {
        logger.info("Running UserTest#testGetAll()");
        List<User> temp = new ArrayList<>(4);
        temp.add(u);
        temp.add(EntityFactory.createUser("username2", "password2", "real name2", roles));
        temp.add(EntityFactory.createUser("username3", "password3", "real name3", roles.subList(0, 2)));
        temp.add(EntityFactory.createUser("username4", "password4", "real name4", roles.get(0)));
        
        for(User uTemp : temp)
        {
            userService.createUser(uTemp);
        }
        
        List<User> resultList = userService.getAllUsers();
        
        Collections.sort(resultList,TestTools.userComparator);
        Collections.sort(temp,TestTools.userComparator);
        assertEquals("not same count",4,resultList.size());
        
        for(int i =0 ; i < resultList.size();i++)
        {
            deepCompare(temp.get(i), resultList.get(i));
        }     
    }
    
    
    @Test
    public void testGetByRole()
    {
        logger.info("Running UserTest#testGetAll()");
        User u1 = EntityFactory.createUser("username1", "password1", "real name1", roles.get(1));
        User u2 = EntityFactory.createUser("username2", "password2", "real name2", roles.get(2));
        User u3 = EntityFactory.createUser("username3", "password3", "real name3", roles.subList(1, 3));
        
        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        
        
        List<User> list1 = userService.getUsersByRole(roles.get(1));
        List<User> list2 = userService.getUsersByRole(roles.get(2));
        
        // anon
        assertEquals("not zero",0,userService.getUsersByRole(roles.get(0)).size());
        
        // user
        assertEquals("should be two",2,list1.size());
        
        // admin
        assertEquals("should be two",2,list2.size());
    }
    
    
    
    
    private void deepCompare(User expected, User actual)
    {
        assertEquals("id not same",expected.getId(),actual.getId());
        assertEquals("username not same", expected.getUsername(),actual.getUsername());
        assertEquals("real naem not same", expected.getRealName(),actual.getRealName());
        assertEquals("pass not same", expected.getPassword(),actual.getPassword());
        
        assertTrue("roles are not the same",(expected.getUserRoles().containsAll(actual.getUserRoles()) &&
                actual.getUserRoles().containsAll(expected.getUserRoles())));
    }
    
}
