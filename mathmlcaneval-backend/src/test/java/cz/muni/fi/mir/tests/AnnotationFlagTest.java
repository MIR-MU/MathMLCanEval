/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.domain.AnnotationFlag;
import cz.muni.fi.mir.service.AnnotationFlagService;
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
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})
@TestExecutionListeners({
    DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AnnotationFlagTest
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationFlagTest.class);
    
    
    
    @Autowired private AnnotationFlagService annotationFlagService;
    private List<AnnotationFlag> aFlags = new ArrayList<>();
    private static final Long ID = new Long(1);
    
    
    @Before
    public void init()
    {
        //PROPER_RESULT, MIGHT_BE_PROPER, WRONG, CHECK_PLS
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_MIGHT_BE_PROPER));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_WRONG));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_CHECK_PLS));
    }

    @Test
    public void testCreateAndGetFlagAnnotation()
    {
        logger.info("Running AnnotationFlagTest#testCreateAndGetFlagAnnotation()");
        annotationFlagService.createFlagAnnotation(aFlags.get(0));
        
        AnnotationFlag af = annotationFlagService.getAnnotationFlagByID(ID);
        
        deepCompare(aFlags.get(0), af);
    }

    @Test
    public void testUpdateFlagAnnotation()
    {
        logger.info("Running AnnotationFlagTest#testCreateAndGetConfiguration()");
        annotationFlagService.createFlagAnnotation(aFlags.get(0));
        
        AnnotationFlag result = annotationFlagService.getAnnotationFlagByID(ID);
        assertNotNull("AnnotationFlag object was not created.",result);
        
        result.setFlagValue("changed");
        
        annotationFlagService.updateFlagAnnotation(result);
        
        
        AnnotationFlag updatedResult = annotationFlagService.getAnnotationFlagByID(ID);
        
        deepCompare(result, updatedResult);        
    }

    @Test
    public void testDeleteFlagAnnotation()
    {
        logger.info("Running AnnotationFlagTest#testCreateAndGetConfiguration()");
        annotationFlagService.createFlagAnnotation(aFlags.get(0));
        
        AnnotationFlag result = annotationFlagService.getAnnotationFlagByID(ID);
        assertNotNull("AnnotationFlag object was not created.",result);
        
        annotationFlagService.deleteFlagAnnotation(result);
        
        assertNull("AnnotationFlag object was not deleted.",annotationFlagService.getAnnotationFlagByID(ID));        
    }

    @Test
    public void testGetAllAnnotationFlags()
    {
        logger.info("Running AnnotationFlagTest#testCreateAndGetConfiguration()");
        for(AnnotationFlag af : aFlags)
        {
            annotationFlagService.createFlagAnnotation(af);
        }
        
        List<AnnotationFlag> result = annotationFlagService.getAllAnnotationFlags();
        
        assertEquals(TestTools.ERROR_LIST_SIZE,aFlags.size(),result.size());
        
        Collections.sort(aFlags,TestTools.annotationFlagComparator);
        Collections.sort(result,TestTools.annotationFlagComparator);
        
        for(int i = 0; i < aFlags.size();i++)
        {
            deepCompare(aFlags.get(i), result.get(i));
        }
        
    }

    @Test
    public void testFindAnnotationFlagByValue()
    {
        logger.info("Running AnnotationFlagTest#testCreateAndGetConfiguration()");
        
        for(AnnotationFlag af : aFlags)
        {
            annotationFlagService.createFlagAnnotation(af);
        }
        
        List<AnnotationFlag> result = annotationFlagService.findAnnotationFlagByValue(AnnotationFlag.VALUE_PROPER_RESULT);
        
        assertEquals(TestTools.ERROR_LIST_SIZE,2,result.size());
        
        for(AnnotationFlag af : result)
        {
            assertTrue("AnnotationFlag object does not have proper value.",af.getFlagValue().contains(AnnotationFlag.VALUE_PROPER_RESULT));
        }
    }
    
    private void deepCompare(AnnotationFlag expected, AnnotationFlag actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID,expected.getId(),actual.getId());
        assertEquals("Field value for AnnotationFlag object is wrong",expected.getFlagValue(),actual.getFlagValue());
    }
}
