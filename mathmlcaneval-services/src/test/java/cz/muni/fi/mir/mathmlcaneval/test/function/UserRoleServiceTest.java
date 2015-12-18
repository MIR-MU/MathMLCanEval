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
package cz.muni.fi.mir.mathmlcaneval.test.function;

import cz.muni.fi.mir.mathmlcaneval.api.UserRoleService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import cz.muni.fi.mir.mathmlcaneval.services.factories.DTOFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.dozer.MappingException;
import org.junit.Assert;
import static org.junit.Assert.fail;
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

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{
    "classpath:spring/spring-database.xml", "classpath:/spring/spring-services.xml"
})
@TestExecutionListeners(
        {
            DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserRoleServiceTest
{

    @Autowired
    private DTOFactory dTOFactory;
    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void testCreateError()
    {
        try
        {
            userRoleService.create(null);
            fail("No exception thrown when null was attempted to create.");
        }
        catch (IllegalArgumentException iae)
        {
        }
        try
        {
            userRoleService.create(dTOFactory.newUserRole(null));
            fail("No exception thrown when attempting to create role with null id");
        }
        catch (IllegalArgumentException iae)
        {
        }
        try
        {
            userRoleService.create(dTOFactory.newUserRole(null, null));
            fail("No exception thrown when attempting to create role with null name");
        }
        catch (IllegalArgumentException iae)
        {
        }

        try
        {
            userRoleService.create(dTOFactory.newUserRole(Long.valueOf("1"), "Test"));
            fail("No exception thrown when attempting to create role with already assigned id");
        }
        catch (IllegalArgumentException iae)
        {
        }
    }

    @Test
    public void testCreate()
    {
        UserRoleDTO dto = dTOFactory.newUserRole(null, "ROLE_ADMINISTRATOR");
        userRoleService.create(dto);

        Assert.assertNotNull("UserService did not assign ID to DTO, it may have not been created", dto.getId());
    }

    @Test
    public void updateError()
    {
        try
        {
            userRoleService.update(null);
            fail("No exception thrown when attempting to update null");
        }
        catch (IllegalArgumentException iae)
        {

        }

        try
        {
            userRoleService.update(dTOFactory.newUserRole(null, "value"));
            fail("No exception thrown when attempting to update UserRole with null id");
        }
        catch (IllegalArgumentException iae)
        {

        }

        try
        {
            userRoleService.update(dTOFactory.newUserRole(Long.valueOf("1")));
            fail("No exception thrown when attempting to update UserRole with no roleName");
        }
        catch (IllegalArgumentException iae)
        {

        }
    }

    @Test
    public void update()
    {
        UserRoleDTO ur = dTOFactory.newUserRole(null, "ROLE_ADMINISTRATOR");
        userRoleService.create(ur);
        ur.setRoleName("ROLE_USER");
        userRoleService.update(ur);

        Assert.assertEquals("UserRoleService did not change the roleName", "ROLE_USER", ur.getRoleName());
    }

    @Test
    public void deleteError()
    {
        try
        {
            userRoleService.delete(null);
            fail("UserRoleService allowed to delete null.");
        }
        catch (IllegalArgumentException iae)
        {

        }
        try
        {
            userRoleService.delete(dTOFactory.newUserRole(null, null));
            fail("UserRoleService allowed to delete DTO with null ID.");
        }
        catch (IllegalArgumentException iae)
        {
        }
    }

    @Test
    public void delete()
    {
        UserRoleDTO ur = dTOFactory.newUserRole(null, "ROLE_USER");
        userRoleService.create(ur);
        Long id = ur.getId();
        userRoleService.delete(ur);

        Assert.assertNull("UserService did not remove id from deleted DTO", ur.getId());
        Assert.assertNull("UserService did not remove DTO from database", userRoleService.getByID(id));
    }

    @Test
    public void getByIDError()
    {
        try
        {
            userRoleService.getByID(null);
            fail("No exception thrown when attempting to retrieve UserRole with null id.");
        }
        catch (IllegalArgumentException ex)
        {

        }

        UserRoleDTO ur = dTOFactory.newUserRole(null, "ROLE_USER");
        userRoleService.create(ur);

        try
        {
            userRoleService.getByID(Long.valueOf("100"));
        }
        catch (MappingException me)
        {
            fail("Returned null by DAO is not handled correctly.");
        }
    }

    @Test
    public void getByID()
    {
        UserRoleDTO ur = dTOFactory.newUserRole(null, "ROLE_USER");
        userRoleService.create(ur);

        UserRoleDTO dto = userRoleService.getByID(ur.getId());

        deepEquals(ur, dto);
    }

    @Test
    public void getByNameError()
    {
        try
        {
            userRoleService.getByName(null);
            fail("UserRoleService allowed to retrieve UserRole with null name.");
        }
        catch (IllegalArgumentException iae)
        {

        }

        try
        {
            userRoleService.getByName("");
            fail("UserRoleService allowed to retrieve UserRole with name consisting of empty string.");
        }
        catch (IllegalArgumentException iae)
        {

        }

        try
        {
            userRoleService.create(dTOFactory.newUserRole(null, "ROLE_ADMINISTRATOR"));
            userRoleService.getByName("ROLE_USER");
        }
        catch (MappingException me)
        {
            fail("Returned null by DAO is not handled correctly.");
        }
    }

    @Test
    public void testGetByName()
    {
        UserRoleDTO ur = dTOFactory.newUserRole(null, "ROLE_ADMINISTRATOR");
        userRoleService.create(ur);
        UserRoleDTO dto = userRoleService.getByName("ROLE_ADMINISTRATOR");
        System.out.println(ur);
        System.out.println(dto);
        deepEquals(ur, dto);
    }

    @Test
    public void testGetAllError()
    {
        try
        {
            userRoleService.getAll();
        }
        catch (MappingException me)
        {
            fail("Empty List returned by DAO is not handled correctly.");
        }
    }

    @Test
    public void testGetAll()
    {
        List<UserRoleDTO> list1 = new ArrayList<>(
                Arrays.asList(dTOFactory.newUserRole(null, "r1"),
                        dTOFactory.newUserRole(null, "r2d2"),
                        dTOFactory.newUserRole(null, "c3p0"),
                        dTOFactory.newUserRole(null, "BB-8")
                )
        );
        
        for(UserRoleDTO u : list1)
        {
            userRoleService.create(u);
        }
        
        List<UserRoleDTO> list2 = userRoleService.getAll();
        
        Assert.assertEquals("UserRoleService did not return the same number of UserRoles", list1.size(), list2.size());
        Collections.sort(list1, new UserRoleComparator());
        Collections.sort(list2, new UserRoleComparator());
        
        for(int i = 0; i < list1.size(); i++)
        {
            deepEquals(list1.get(i), list2.get(i));
        }
    }
    
    private void deepEquals(UserRoleDTO expected, UserRoleDTO actual)
    {
        Assert.assertEquals("UserRoleService did not return UserRole with the same ID.", expected.getId(), actual.getId());
        Assert.assertEquals("UserRoleService did not return UserRole with the same roleName.", expected.getRoleName(), actual.getRoleName());
    }

    private class UserRoleComparator implements Comparator<UserRoleDTO>
    {

        @Override
        public int compare(UserRoleDTO o1, UserRoleDTO o2)
        {
            return o1.getId().compareTo(o2.getId());
        }

    }
}
