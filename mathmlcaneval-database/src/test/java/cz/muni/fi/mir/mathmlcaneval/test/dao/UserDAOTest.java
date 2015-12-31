/*
 * Copyright 2015 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.test.dao;

import cz.muni.fi.mir.mathmlcaneval.database.UserDAO;
import cz.muni.fi.mir.mathmlcaneval.database.UserRoleDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.User;
import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import cz.muni.fi.mir.mathmlcaneval.database.factories.DomainFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{
    "classpath:spring/spring-database.xml"
})
@TestExecutionListeners(
        {
            DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
public class UserDAOTest
{
    private static final Logger LOG = LogManager.getLogger(UserDAOTest.class);
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DomainFactory domainFactory;
    @Autowired
    private PodamFactory podamFactory;
    private User workingUser = null;

    private List<UserRole> roles = null;

    @Before
    public void init()
    {
        roles = new ArrayList<>(3);
        roles.add(podamFactory.manufacturePojo(UserRole.class));
        roles.add(podamFactory.manufacturePojo(UserRole.class));
        roles.add(podamFactory.manufacturePojo(UserRole.class));

        for (UserRole ur : roles)
        {
            userRoleDAO.create(ur);
        }
        
        workingUser = podamFactory.manufacturePojo(User.class);
        workingUser.setRoles(roles);
    }

    @Test
    public void testCreate()
    {
        userDAO.create(workingUser);
        
        Assert.assertNotNull("User was not created because it does not have set their id.",workingUser.getId());
    }

    @Test
    public void testGetById()
    {
        userDAO.create(workingUser);
        User result = userDAO.getByID(Long.valueOf("1"));
        
        Assert.assertEquals("UserDAO returned user with different ID than expected.", workingUser.getId(), result.getId());
        Assert.assertEquals("UserDAO returned user with different username than expected.", workingUser.getUsername(), result.getUsername());
    }

    @Test
    public void testDelete()
    {
        userDAO.create(workingUser);
        Long id = workingUser.getId();
        userDAO.delete(id);
        Assert.assertNull("UserDAO did not delete user", userDAO.getByID(id));
    }

    @Test
    public void testUpdate()
    {
        userDAO.create(workingUser);
        User toUpdate = userDAO.getByID(workingUser.getId());
        toUpdate.setEmail("new@email.com");
        toUpdate.setRealName("Norton Bond");
        
        userDAO.update(toUpdate);
        
        User updated = userDAO.getByID(toUpdate.getId());
        
        Assert.assertEquals("UserDAO did not change users email.", "new@email.com", updated.getEmail());
        Assert.assertEquals("UserDAO did not change users real name.", "Norton Bond", updated.getRealName());
    }

    @Test
    public void testGetAll()
    {
        Assert.assertEquals("Database should be empty.", 0, userDAO.getAll().size());
        
        for(int i = 0; i < 5; i++)
        {
            User u = podamFactory.manufacturePojo(User.class);
            u.setRoles(roles);
            userDAO.create(u);
        }
        
        Assert.assertEquals("UserRoleDAO did not return expected number of entries.",5,userDAO.getAll().size());
    }

    @Test
    public void testGetByUsername()
    {
        User toBeCreated = podamFactory.manufacturePojo(User.class);
        toBeCreated.setRoles(roles);
        String username = toBeCreated.getUsername();
        
        userDAO.create(toBeCreated);
        
        User result = userDAO.getByUsername(username);
        
        Assert.assertEquals("UserRoleDao did not return expected user with username", username,result.getUsername());
        Assert.assertEquals("UserRoleDao did not return expected user with wrong id", toBeCreated.getId(),result.getId());
    }
}
