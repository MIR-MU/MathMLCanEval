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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.jemos.podam.api.PodamFactory;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DAOTest
public class UserRoleDAOTest
{
    private static final Logger LOG = LogManager.getLogger(UserRoleDAOTest.class);
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private PodamFactory podamFactory;

    @Test
    public void testCreate()
    {
        UserRole ur = podamFactory.manufacturePojo(UserRole.class);
        userRoleDAO.create(ur);
        Assert.assertNotNull("ID was not assigned by TX/DAO",ur.getId());
    }
    
    @Test
    public void getByID()
    {
        UserRole ur = podamFactory.manufacturePojo(UserRole.class);
        
        userRoleDAO.create(ur);
        
        UserRole result = userRoleDAO.getById(ur.getId());
        
        Assert.assertNotNull("UserRoleDAO returned null.", result);
        Assert.assertNotNull("UserRoleDAO returned result with null ID.", result.getId());
        Assert.assertEquals("UserRoleDAO returned invalid result based on equals().", ur, result);
    }
    
    @Test
    public void delete()
    {
        UserRole ur = podamFactory.manufacturePojo(UserRole.class);
        userRoleDAO.create(ur);
        Long id = ur.getId();
        userRoleDAO.delete(id);
        
        Assert.assertNull("UserRoleDAO did not delete UserRole properly.", userRoleDAO.getById(id));
    }
    
    @Test
    public void update()
    {
        UserRole ur = podamFactory.manufacturePojo(UserRole.class);
        userRoleDAO.create(ur);
        UserRole update = userRoleDAO.getById(ur.getId());
        update.setRoleName("user");
        userRoleDAO.update(update);
        
        Assert.assertEquals("UserRoleDAO did not update UserRole properly.", "user", update.getRoleName());
    }
    
    @Test
    public void getByName()
    {
        UserRole ur = podamFactory.manufacturePojo(UserRole.class);
        String name = ur.getRoleName();
        userRoleDAO.create(ur);
        UserRole result = userRoleDAO.getByName(name);
        
        Assert.assertEquals("UserRoleDAO did not return UserRole with expected ID.", ur.getId(),result.getId());
        Assert.assertEquals("UserRoleDAO did not return UserRole with expected roleName.",ur.getRoleName(),result.getRoleName());
    }
    
    @Test
    public void getAll()
    {
        Assert.assertEquals("Database should be empty.", 0, userRoleDAO.getAll().size());
        userRoleDAO.create(podamFactory.manufacturePojo(UserRole.class));
        userRoleDAO.create(podamFactory.manufacturePojo(UserRole.class));
        userRoleDAO.create(podamFactory.manufacturePojo(UserRole.class));
        
        Assert.assertEquals("UserRoleDAO did not return expected number of entries.",3,userRoleDAO.getAll().size());
    }

}
