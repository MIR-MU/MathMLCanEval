/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.service.ConfigurationService;
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
public class ConfigurationTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConfigurationTest.class);

    @Autowired
    private ConfigurationService configurationService;
    private static final Long ID = new Long(1);
    private List<Configuration> configs = new ArrayList<>(3);

    @Before
    public void init()
    {
        configs = DataTestTools.provideConfigurationList();
    }

    @Test
    public void testCreateAndGetConfiguration()
    {
        logger.info("Running ConfigurationTest#testCreateAndGetConfiguration()");
        configurationService.createConfiguration(configs.get(0));

        Configuration result = configurationService.getConfigurationByID(ID);

        assertNotNull("Configuration object was not created.", result);

        deepCompare(configs.get(0), result);
    }
    
    @Test
    public void testDeleteConfiguration()
    {
        logger.info("Running ConfigurationTest#testDeleteConfiguration()");

        configurationService.createConfiguration(configs.get(0));

        Configuration result = configurationService.getConfigurationByID(ID);

        assertNotNull("Configuration object was not created.", result);

        configurationService.deleteConfiguration(result);

        assertNull("Configuration object was not deleted.", configurationService.getConfigurationByID(ID));
    }

    @Test
    public void testGetAllCofigurations()
    {
        logger.info("Running ConfigurationTest#testGetAllCofigurations()");
        for (Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }

        List<Configuration> result = configurationService.getAllCofigurations();

        assertEquals(TestTools.ERROR_LIST_SIZE, configs.size(), result.size());

        Collections.sort(configs, TestTools.confiurationComparator);
        Collections.sort(result, TestTools.confiurationComparator);

        for (int i = 0; i < configs.size(); i++)
        {
            deepCompare(configs.get(i), result.get(i));
        }
    }

    private void deepCompare(Configuration expected, Configuration actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given Configuration has wrong name.", expected.getName(), actual.getName());
        assertEquals("Given Configuration has wrong note.", expected.getNote(), actual.getNote());
    }
}
