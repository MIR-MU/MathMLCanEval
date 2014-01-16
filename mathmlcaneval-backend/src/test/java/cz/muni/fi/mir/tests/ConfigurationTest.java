/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.domain.Configuration;
import cz.muni.fi.mir.service.ConfigurationService;
import cz.muni.fi.mir.tools.EntityFactory;
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
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(true, true, true), "vsetko true", "vsetky hodnoty su true lebo kacka"));
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(true, true, false), "priemerny config 2:1", "vsetky hodnoty su true lebo medved"));
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(false, false, false), "vsetko false", "vsetky podnoty su true lebo holub"));
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
    public void testUpdateConfiguration()
    {
        logger.info("Running ConfigurationTest#testUpdateConfiguration()");

        configurationService.createConfiguration(configs.get(0));

        Configuration result = configurationService.getConfigurationByID(ID);

        assertNotNull("Configuration object was not created.", result);

        result.setName("hello");
        result.setNote("1:2 nakoniec");
        result.setConfig(TestTools.getConfig(false, false, true));

        configurationService.updateConfiguration(result);

        Configuration updatedResult = configurationService.getConfigurationByID(ID);

        deepCompare(result, updatedResult);
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

    @Test
    public void testGetBySubstringNote()
    {
        logger.info("Running ConfigurationTest#testGetBySubstringNote()");
        for (Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }

        List<Configuration> result = configurationService.getBySubstringNote("hodnoty su");
        assertEquals(TestTools.ERROR_LIST_SIZE, 2, result.size());

        for (Configuration c : result)
        {
            assertTrue("does not contain substrin", c.getNote().contains("hodnoty su"));
        }

    }

    @Test
    public void testFindyByName()
    {
        logger.info("Running ConfigurationTest#testFindyByName()");
        for (Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }

        List<Configuration> result = configurationService.findyByName("vsetko");
        assertEquals(TestTools.ERROR_LIST_SIZE, 2, result.size());

        for (Configuration c : result)
        {
            assertTrue("Configuration does not have field name set properly.", c.getName().contains("vsetko"));
        }
    }

    private void deepCompare(Configuration expected, Configuration actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given Configuration has wrong name.", expected.getName(), actual.getName());
        assertEquals("Given Configuration has wrong note.", expected.getNote(), actual.getNote());
    }
}
