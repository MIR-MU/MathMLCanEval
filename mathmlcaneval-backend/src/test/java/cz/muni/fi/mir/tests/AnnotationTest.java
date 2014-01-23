/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.AnnotationFlagService;
import cz.muni.fi.mir.db.service.AnnotationService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
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
public class AnnotationTest
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationTest.class);
    
    
    @Autowired private AnnotationService annotationService;
    @Autowired private AnnotationFlagService annotationFlagService;
    @Autowired private UserService userService;
    @Autowired private UserRoleService userRoleService;
    
    private List<AnnotationFlag> aFlags = new ArrayList<>();
    private List<UserRole> roles = new ArrayList<>(3);
    private List<Annotation> annotations = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private static final Long ID = new Long(1);
    
    
    @Before
    public void init()
    {
        roles.add(EntityFactory.createUserRole("ROLE_ANONYMOUS"));
        roles.add(EntityFactory.createUserRole("ROLE_USER"));
        roles.add(EntityFactory.createUserRole("ROLE_ADMINISTRATOR"));
        
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_MIGHT_BE_PROPER));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_WRONG));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_CHECK_PLS));
        
        
        users.add(EntityFactory.createUser("username1", "password1", "druhe viac aslovne cislo1", roles));
        users.add(EntityFactory.createUser("username2", "password2", "druhe viac aslovne cislo2", roles));
        users.add(EntityFactory.createUser("username3", "password3", "druhe viac aslovne cislo3", roles));
        
        
        for(UserRole ur : roles)
        {
            userRoleService.createUserRole(ur);
        }
        
        for(AnnotationFlag af : aFlags)
        {
            annotationFlagService.createFlagAnnotation(af);
        }
        
        for(User u : users)
        {
            userService.createUser(u);
        }
        
        annotations.add(EntityFactory.createAnnotation("poznamka", users.get(0), aFlags.get(0)));
        annotations.add(EntityFactory.createAnnotation("poznamkaaa", users.get(0), aFlags.get(1)));
        annotations.add(EntityFactory.createAnnotation("poznamka12313", users.get(0), aFlags.get(1)));
        annotations.add(EntityFactory.createAnnotation("aha2313", users.get(1), aFlags.get(1)));
        annotations.add(EntityFactory.createAnnotation("pozyuyiua12313", users.get(1), aFlags.get(1)));
    }

    @Test
    public void testCreateAndGetAnnotation()
    {
        logger.info("Running AnnotationTest#testCreateAndGetAnnotation()");
        annotationService.createAnnotation(annotations.get(0));
        
        Annotation a = annotationService.getAnnotationByID(ID);
        
        deepCompare(annotations.get(0), a);
        
    }

    @Test
    public void testUpdateAnnotation()
    {
        logger.info("Running AnnotationTest#testUpdateAnnotation()");
        annotationService.createAnnotation(annotations.get(0));
        
        Annotation a = annotationService.getAnnotationByID(ID);
        
        assertNotNull("Annotation object was not created.",a);
        
        a.setNote("ano zmenilo sa");
        a.setAnnotationFlag(aFlags.get(2));
        
        annotationService.updateAnnotation(a);
        
        Annotation updated = annotationService.getAnnotationByID(ID);
        
        deepCompare(a, updated);
    }

    @Test
    public void testDeleteAnnotation()
    {
        logger.info("Running AnnotationTest#testDeleteAnnotation()");
        annotationService.createAnnotation(annotations.get(0));
        
        Annotation a = annotationService.getAnnotationByID(ID);
        
        assertNotNull("Annotation object was not created.",a);
        
        annotationService.deleteAnnotation(a);
        
        assertNull("Annotation object was not deleted.",annotationService.getAnnotationByID(ID));
    }

    @Test
    public void testGetAllAnnotations()
    {
        logger.info("Running AnnotationTest#testGetAllAnnotations()");
        for(Annotation a : annotations)
        {
            annotationService.createAnnotation(a);
        }
        
        List<Annotation> resultList = annotationService.getAllAnnotations();
        assertEquals(TestTools.ERROR_LIST_SIZE, annotations.size(),resultList.size());
        Collections.sort(annotations, TestTools.annotationComparator);
        Collections.sort(resultList, TestTools.annotationComparator);
        
        for(int i = 0;i<annotations.size();i++)
        {
            deepCompare(annotations.get(i), resultList.get(i));
        }
    }

    @Test
    public void testGetAnnotationByUser()
    {
        logger.info("Running AnnotationTest#testGetAnnotationByUser()");
        for(Annotation a : annotations)
        {
            annotationService.createAnnotation(a);
        }
        
        List<Annotation> resultList = annotationService.getAnnotationByUser(users.get(0));
        
        assertEquals(TestTools.ERROR_LIST_SIZE,3,resultList.size());
        
        for(Annotation a : resultList)
        {
            assertEquals("Given annotaion has wrong user set in its field.",users.get(0),a.getUser());
        }
    }

    @Test
    public void testGetAnnotationByFlag()
    {
        logger.info("Running AnnotationTest#testGetAnnotationByFlag()");
        for(Annotation a : annotations)
        {
            annotationService.createAnnotation(a);
        }
        
        List<Annotation> resultList = annotationService.getAnnotationByFlag(aFlags.get(1));
        List<Annotation> resultList2 = annotationService.getAnnotationByFlag(aFlags.get(3));
        
        assertEquals(TestTools.ERROR_LIST_SIZE,4,resultList.size());
        assertEquals(TestTools.ERROR_LIST_SIZE,0,resultList2.size());
        
        for(Annotation a : resultList)
        {
            assertEquals("Given annotaion has wrong AnnotaionFlag set in its field.",aFlags.get(1),a.getAnnotationFlag());
        }        
    }

    @Test
    public void testFindByNote()
    {
        logger.info("Running AnnotationTest#testFindByNote()");
        for(Annotation a : annotations)
        {
            annotationService.createAnnotation(a);
        }
        
        List<Annotation> resultList = annotationService.findByNote("poz");
        
        assertEquals(TestTools.ERROR_LIST_SIZE,4,resultList.size());
        
        
        for(Annotation a : resultList)
        {
            assertTrue("Given annotaion has wrong note set in its field.",a.getNote().contains("poz"));
        }
    }
    
    
    private void deepCompare(Annotation expected, Annotation actual)
    {
        assertNotNull("Expected object is null.", actual);
        assertEquals(TestTools.ERROR_WRONG_ID,expected.getId(),actual.getId());
        assertEquals("Given Annotation has wrong note.",expected.getNote(),actual.getNote());
        assertEquals("Given Annotation has wrong user.",expected.getUser(),actual.getUser());
        assertEquals("Given Annotation has wrong annotationFlag.",expected.getAnnotationFlag(),actual.getAnnotationFlag());
    }
}