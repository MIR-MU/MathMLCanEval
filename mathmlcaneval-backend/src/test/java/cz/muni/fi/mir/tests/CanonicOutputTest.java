/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.AnnotationFlagService;
import cz.muni.fi.mir.db.service.AnnotationService;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.SimilarityFormConverter;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
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
public class CanonicOutputTest
{
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CanonicOutputTest.class);
    
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private RevisionService revisionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationRunService applicationRunService;
    @Autowired
    private AnnotationService annotationService;
    @Autowired
    private AnnotationFlagService annotationFlagService;
    @Autowired
    private SimilarityFormConverter similarityFormConverter;
    @Autowired
    private CanonicOutputService canonicOutputService;
    @Autowired
    private FormulaService formulaService;
    
    private static final Long ID = new Long(1);
    private List<Configuration> configs = new ArrayList<>(3);
    private List<User> users = new ArrayList<>(2);
    private List<Revision> revs = new ArrayList<>(4);
    private List<ApplicationRun> appruns = new ArrayList<>(5);
    private List<AnnotationFlag> aFlags = new ArrayList<>();
    private List<Annotation> annotations = new ArrayList<>();
    private List<CanonicOutput> canonicOutputs = new ArrayList<>();
    
    @Before
    public void init()
    {
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(true, true, true), "vsetko true", "vsetky hodnoty su true lebo kacka"));
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(true, true, false), "priemerny config 2:1", "vsetky hodnoty su true lebo medved"));
        configs.add(EntityFactory.createConfiguration(TestTools.getConfig(false, false, false), "vsetko false", "vsetky podnoty su true lebo holub"));
        for (Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }
        
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554a", "nahodna poznamka aby sa nieco naslo"));
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554b", "nahodna poznamka aby si nieco naslo"));
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554c", "nahodna poznamka aby so nieco naslo"));
        revs.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554d", "toto sa nenajde"));
        for (Revision r : revs)
        {
            revisionService.createRevision(r);
        }
        
        UserRole role = EntityFactory.createUserRole("ROLE_ADMINISTRATOR");
        userRoleService.createUserRole(role);
        
        users.add(EntityFactory.createUser("username2", "password2", "real name2", "example@example.com", role));
        users.add(EntityFactory.createUser("username3", "password3", "real name3", "example@example.com", role));
        for (User u : users)
        {
            userService.createUser(u);
        }
        
        appruns.add(EntityFactory.createApplicationRun("poznamka 1", new DateTime(2013, 02, 02, 12, 13), new DateTime(2013, 02, 02, 12, 14), users.get(0), revs.get(0), configs.get(0)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 2", new DateTime(2013, 02, 02, 13, 13), new DateTime(2013, 02, 02, 14, 14), users.get(0), revs.get(0), configs.get(0)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 3", new DateTime(2013, 02, 02, 14, 13), new DateTime(2013, 02, 02, 14, 14), users.get(1), revs.get(1), configs.get(0)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 4", new DateTime(2013, 02, 02, 15, 13), new DateTime(2013, 02, 02, 15, 14), users.get(1), revs.get(2), configs.get(1)));
        appruns.add(EntityFactory.createApplicationRun("poznamka 5", new DateTime(2013, 02, 02, 16, 13), new DateTime(2013, 02, 02, 16, 14), users.get(0), revs.get(1), configs.get(2)));
        
        for (ApplicationRun ar : appruns)
        {
            applicationRunService.createApplicationRun(ar);
        }
        
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_MIGHT_BE_PROPER));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_WRONG));
        aFlags.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_CHECK_PLS));
        
        for (AnnotationFlag af : aFlags)
        {
            annotationFlagService.createFlagAnnotation(af);
        }
        
        annotations.add(EntityFactory.createAnnotation("poznamka", users.get(0), aFlags.get(0)));
        annotations.add(EntityFactory.createAnnotation("poznamkaaa", users.get(0), aFlags.get(1)));
        annotations.add(EntityFactory.createAnnotation("poznamka12313", users.get(0), aFlags.get(1)));
        annotations.add(EntityFactory.createAnnotation("aha2313", users.get(1), aFlags.get(1)));
        annotations.add(EntityFactory.createAnnotation("pozyuyiua12313", users.get(1), aFlags.get(1)));
        
        for (Annotation a : annotations)
        {
            annotationService.createAnnotation(a);
        }
        
        canonicOutputs.add(EntityFactory.createCanonicOutput(TestTools.getFirstXML(), similarityFormConverter.convert(TestTools.getFirstXML()), null, 512516l, appruns.get(0), annotations.subList(0, 2)));
    }
    
    @Test
    public void testCreateAndGetCanonicOutput()
    {
        logger.info("Running CanonicOutputTest#testCreateAndGetCanonicOutput()");
        canonicOutputService.createCanonicOutput(canonicOutputs.get(0));
        
        CanonicOutput result = canonicOutputService.getCanonicOutputByID(ID);
        
        assertNotNull("n",result);
        deepCompare(canonicOutputs.get(0), result);
    }
    
    @Test
    public void testUpdateCanonicOutput()
    {
    }
    
    @Test
    public void testDeleteCanonicOutput()
    {
    }
    
    @Test
    public void testGetCanonicOutputBySimilarForm()
    {
    }
    
    @Test
    public void testGetCanonicOutputByAppRun()
    {
    }
    
    @Test
    public void testGetCanonicOutputByFormula()
    {
    }
    
    @Test
    public void testGetCanonicOutputByParentFormula()
    {
    }
    
    @Test
    public void testGetSimilarCanonicOutputs()
    {
    }
    
    private void deepCompare(CanonicOutput expected, CanonicOutput actual)
    {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOutputForm(), actual.getOutputForm());
        assertEquals(expected.getSimilarForm(), actual.getSimilarForm());
        //todo kolekcie
    }
    
}
