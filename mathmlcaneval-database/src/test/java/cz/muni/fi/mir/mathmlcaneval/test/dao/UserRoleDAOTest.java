/*
 * Copyright 2015 Math.
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

import cz.muni.fi.mir.mathmlcaneval.database.UserRoleDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import cz.muni.fi.mir.mathmlcaneval.database.factories.DomainFactory;
import org.junit.Assert;
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
public class UserRoleDAOTest
{
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private DomainFactory domainFactory;

    @Test
    public void testCreate()
    {
        UserRole ur = domainFactory.newUserRole(null, "admin");
        userRoleDAO.create(ur);
        
        Assert.assertNotNull("ID was not assigned by TX/DAO",ur.getId());
    }
    
    @Test
    public void getByID()
    {
        UserRole ur = domainFactory.newUserRole(null, "admin");
        
        userRoleDAO.create(ur);
        
        UserRole result = userRoleDAO.getByID(ur.getId());
        
        Assert.assertNotNull("UserRoleDAO returned null.", result);
        Assert.assertNotNull("UserRoleDAO returned result with null ID.", result.getId());
        Assert.assertEquals("UserRoleDAO returned invalid result based on equals().", result, result);
    }
    
    @Test
    public void delete()
    {
        UserRole ur = domainFactory.newUserRole(null, "admin");
        userRoleDAO.create(ur);
        Long id = ur.getId();
        userRoleDAO.delete(id);
        
        Assert.assertNull("UserRoleDAO did not delete UserRole properly.", userRoleDAO.getByID(id));
    }
    
    @Test
    public void update()
    {
        UserRole ur = domainFactory.newUserRole(null, "admin");
        userRoleDAO.create(ur);
        UserRole update = userRoleDAO.getByID(ur.getId());
        update.setRoleName("user");
        userRoleDAO.update(update);
        
        Assert.assertEquals("UserRoleDAO did not update UserRole properly.", "user", update.getRoleName());
    }
    
    @Test
    public void getByName()
    {
        UserRole ur = domainFactory.newUserRole(null, "aircraft carrier");
        userRoleDAO.create(ur);
        UserRole result = userRoleDAO.getByName("aircraft carrier");
        
        Assert.assertEquals("UserRoleDAO did not return UserRole with expected ID.", ur.getId(),result.getId());
        Assert.assertEquals("UserRoleDAO did not return UserRole with expected roleName.",ur.getRoleName(),result.getRoleName());
    }
    
    @Test
    public void getAll()
    {
        UserRole ur = domainFactory.newUserRole(null, "role_1");
        UserRole ur2 = domainFactory.newUserRole(null, "role_2");
        UserRole ur3 = domainFactory.newUserRole(null, "role_3");
        
        userRoleDAO.create(ur);
        userRoleDAO.create(ur2);
        userRoleDAO.create(ur3);
        
        Assert.assertEquals("UserRoleDAO did not return expected number of entries.",3,userRoleDAO.getAll().size());
    }

}
