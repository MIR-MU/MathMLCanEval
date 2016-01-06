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
import cz.muni.fi.mir.mathmlcaneval.api.UserService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import cz.muni.fi.mir.mathmlcaneval.services.factories.DTOFactory;
import java.util.ArrayList;
import java.util.List;
import org.dozer.MappingException;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import uk.co.jemos.podam.api.PodamFactory;

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
public class UserServiceTest
{

    @Autowired
    private DTOFactory dTOFactory;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PodamFactory podamFactory;
    private List<UserRoleDTO> userRoles = new ArrayList<>();
    
    @Before
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void init()
    {
        userRoles = new ArrayList<>();
        userRoles.add(podamFactory.manufacturePojo(UserRoleDTO.class));
        userRoles.add(podamFactory.manufacturePojo(UserRoleDTO.class));
        userRoles.add(podamFactory.manufacturePojo(UserRoleDTO.class));
        
        for(UserRoleDTO ur : userRoles)
        {
            userRoleService.create(ur);
        }
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testCreateError()
    {
        try
        {
            userService.create(null);
            fail("UserService allowed to create null user.");
        }
        catch(IllegalArgumentException iae)
        {
            
        }
        
        try
        {
            userService.create(podamFactory.manufacturePojo(UserDTO.class));
            fail("UserSerice allowed to create user user with null id");
        }
        catch(IllegalArgumentException ex)
        {
            
        }
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testCreateNoCredentials()
    {
    }

    @Test
    public void testMissingCredentials()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testCreate()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testGetByIdError()
    {
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testGetByIdNoCredentials()
    {
    }

    @Test
    public void testGetByIdMissingCredentials()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testGetById()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testDeleteError()
    {
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testDeleteNoCredentials()
    {
    }

    @Test
    public void testDeleteMissingCredentials()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testDelete()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testUpdateError()
    {
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testUpdateNoCredentials()
    {
    }

    @Test
    public void testUpdateMissingCredentials()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testUpdate()
    {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testGetAllError()
    {
        try
        {
            userService.getAll();
        }
        catch (MappingException me)
        {
            fail("Empty List returned by DAO is not handled correctly.");
        }
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testGetAllNoCredentials()
    {
        userService.getAll();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(authorities = {"ROLE_USER"})
    public void testGetAllMissingCredentials()
    {
        userService.getAll();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMINISTRATOR"})
    public void testGetAll()
    {
    }
}
