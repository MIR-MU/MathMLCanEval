package cz.muni.fi.mir.mathmlcaneval.test.integration;

/*
 * Copyright 2015 Dominik Szalai - emptulik at gmail.com.
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

import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import cz.muni.fi.mir.mathmlcaneval.database.factories.DomainFactory;
import cz.muni.fi.mir.mathmlcaneval.database.impl.UserRoleDAOImpl;
import cz.muni.fi.mir.mathmlcaneval.services.api.impl.UserRoleServiceImpl;
import cz.muni.fi.mir.mathmlcaneval.services.factories.DTOFactory;
import cz.muni.fi.mir.mathmlcaneval.services.impl.MapperImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-database.xml","classpath:/spring/spring-services.xml"})
@ActiveProfiles("test")
public class UserRoleMockTest
{
    @Mock
    private UserRoleDAOImpl userRoleDAOImpl;
    @Mock
    private MapperImpl mapper;
    @InjectMocks
    private UserRoleServiceImpl userRoleServiceImpl;
    
    private final DomainFactory domainFactory = new DomainFactory();
    private final DTOFactory dtoFactory = new DTOFactory();
    
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create()
    {
        UserRole ur = domainFactory.newUserRole(null, "test role");
        UserRoleDTO dto = dtoFactory.newUserRole(null, "test role");
        
        BDDMockito.given(mapper.map(dto, UserRole.class)).willReturn(ur);
        
        userRoleServiceImpl.create(dto);
        
        Mockito.verify(userRoleDAOImpl).create(ur);
    }
    
    @Test
    public void getByID()
    {
        UserRole ur = domainFactory.newUserRole(Long.valueOf("4"), "administrator");
        UserRoleDTO dto = dtoFactory.newUserRole(Long.valueOf("4"), "administrator");
        
        BDDMockito.given(mapper.map(ur,UserRoleDTO.class)).willReturn(dto);
        
        UserRoleDTO result = userRoleServiceImpl.getByID(Long.valueOf("4"));
        
        Mockito.verify(userRoleDAOImpl).getByID(Long.valueOf("4"));
    }
    
    @Test
    public void getAll()
    {
        //todo
    }
    
    @Test
    public void update()
    {
        //todo
    }
    
    @Test
    public void delete()
    {
        //todo
    }
}
