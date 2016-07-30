/*
 * Copyright 2016 MIR@MU.
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

import cz.muni.fi.mir.mathmlcaneval.database.ConfigurationDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.Configuration;
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
public class ConfigurationDAOTest
{
    @Autowired
    private ConfigurationDAO configurationDAO;
    @Autowired
    private PodamFactory podamFactory;
    
    @Test
    public void testCreate()
    {
        Configuration config = podamFactory.manufacturePojo(Configuration.class);
        configurationDAO.create(config);
        
        
        Assert.assertNotNull("ID was not assigned by DAO layer.", config.getId());
    }
    
    @Test
    public void testUpdate()
    {
        Configuration configuration = podamFactory.manufacturePojo(Configuration.class);
        configurationDAO.create(configuration);
        configuration.setName("say my name");
        
        configurationDAO.update(configuration);
        
        Configuration update = configurationDAO.getById(configuration.getId());
        
        Assert.assertEquals("Name was not changed by DAO layer.", "say my name", update.getName());
    }
    
    @Test
    public void testGetAll()
    {
        Assert.assertEquals("database not empty", 0, configurationDAO.getAll().size());
        
        for(int i = 0; i < 10; i++)
        {
            configurationDAO.create(podamFactory.manufacturePojo(Configuration.class));
        }
        
        Assert.assertEquals("10 objects were created but getAll returned invalid number of them.", 10, configurationDAO.getAll().size());
    }
    
    @Test
    public void testGetAllEnabled()
    {
        for(int i = 0 ; i < 25; i++)
        {
            Configuration c = podamFactory.manufacturePojo(Configuration.class);
            if(i%2==0)
            {
                c.setActive(Boolean.TRUE);
            }
            else
            {
                c.setActive(Boolean.FALSE);
            }
            
            configurationDAO.create(c);
        }
        
        Assert.assertEquals("There should be 13 enabled configurations", 13,configurationDAO.getAllEnabled().size());
    }
}
