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
    private List<User> users = new ArrayList<>(3);
    private List<UserRole> roles = new ArrayList<>();
    private List<Revision> revs = new ArrayList<>(4);
    private List<ApplicationRun> appruns = new ArrayList<>(5);
    private List<AnnotationFlag> aFlags = new ArrayList<>();
    private List<Annotation> annotations = new ArrayList<>();
    private List<CanonicOutput> canonicOutputs = new ArrayList<>();
    
    @Before
    public void init()
    {
        configs = DataTestTools.provideConfigurationList();
        for (Configuration c : configs)
        {
            configurationService.createConfiguration(c);
        }
        
        revs = DataTestTools.provideRevisions();
        for (Revision r : revs)
        {
            revisionService.createRevision(r);
        }
        
        roles = DataTestTools.provideUserRolesList();
        for(UserRole ur : roles)
        {
            userRoleService.createUserRole(ur);
        }
        
        users = DataTestTools.provideUserRoleListGeneral(roles);
        for (User u : users)
        {
            userService.createUser(u);
        }
        
        appruns = DataTestTools.provideApplicationRuns(users, revs, configs);
        for (ApplicationRun ar : appruns)
        {
            applicationRunService.createApplicationRun(ar);
        }
        
        aFlags = DataTestTools.provideAnnotationFlagList();
        for (AnnotationFlag af : aFlags)
        {
            annotationFlagService.createFlagAnnotation(af);
        }
        
        annotations = DataTestTools.provideAnnotationList(users, aFlags);
        for (Annotation a : annotations)
        {
            annotationService.createAnnotation(a);
        }
        
        canonicOutputs.add(EntityFactory.createCanonicOutput(TestTools.getFirstXML(), similarityFormConverter.convert(TestTools.getFirstXML()), null, 512516l, appruns.get(0), annotations.subList(0, 2)));
        canonicOutputs.add(EntityFactory.createCanonicOutput(TestTools.getFirstXML(), similarityFormConverter.convert(TestTools.getFirstXML()), null, 512516, appruns.get(1), annotations.subList(1, 2)));
        canonicOutputs.add(EntityFactory.createCanonicOutput(TestTools.getSecondXML(), similarityFormConverter.convert(TestTools.getSecondXML()), null, 5125, appruns.get(1), annotations));
    }
    
    @Test
    public void testCreateAndGetCanonicOutput()
    {
        logger.info("Running CanonicOutputTest#testCreateAndGetCanonicOutput()");
        canonicOutputService.createCanonicOutput(canonicOutputs.get(0));
        
        CanonicOutput result = canonicOutputService.getCanonicOutputByID(ID);
        
        assertNotNull("Canonic Output was not created.",result);
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
