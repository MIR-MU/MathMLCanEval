/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
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
 * @author siska
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
public class FormulaTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FormulaTest.class);
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SourceDocumentService sourceDocumentService;
    @Autowired
    private FormulaService formulaService;

    private List<User> users = new ArrayList<>(2);
    private List<UserRole> roles = new ArrayList<>(2);
    private List<SourceDocument> sourceDocuments = new ArrayList<>(2);

    private static final Long ID = Long.valueOf("1");
    private List<Formula> formulas = new ArrayList<>(4);

    @Before
    public void init()
    {
        sourceDocuments = DataTestTools.provideSourceDocuments();
        for(SourceDocument sd : sourceDocuments)
        {
            sourceDocumentService.createSourceDocument(sd);
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
        formulas = DataTestTools.provideFormulas(users, sourceDocuments);
    }

    @Test
    public void testCreateAndGetFormula()
    {
        logger.info("Running FormulaTest#testCreateAndGetFormula()");
        formulaService.createFormula(formulas.get(0));

        Formula result = formulaService.getFormulaByID(ID);

        assertNotNull("Formula object was not created.", result);

        deepCompare(formulas.get(0), result);
    }

    @Test
    public void testDeleteFormula()
    {
        logger.info("Running FormulaTest#testDeleteFormula()");

        formulaService.createFormula(formulas.get(0));

        Formula result = formulaService.getFormulaByID(ID);

        assertNotNull("Formula object was not created.", result);

        formulaService.deleteFormula(result);
        
        Formula shouldBeNull = formulaService.getFormulaByID(ID);
        
        logger.fatal(shouldBeNull);

        assertNull("Formula object was not deleted.", shouldBeNull);

    }

    @Test
    public void testUpdateFormula()
    {
        logger.info("Running FormulaTest#testUpdateFormula()");

        formulaService.createFormula(formulas.get(0));

        Formula result = formulaService.getFormulaByID(ID);

        assertNotNull("Formula object was not created.", result);

        result.setNote("zmenena note");
        result.setXml("zmena xml");

        formulaService.updateFormula(result);

        Formula updatedResult = formulaService.getFormulaByID(ID);

        assertEquals("Formula object does not have proper note after update.", "zmenena note", updatedResult.getNote());
        assertEquals("Formula object does not have proper xml after update.", "zmena xml", updatedResult.getXml());
    }

    @Test
    public void testGetAllFormulas()
    {
        logger.info("Running FormulaTest#testGetAllFormulas()");

        for (Formula p : formulas)
        {
            formulaService.createFormula(p);
        }

        List<Formula> result = formulaService.getAllFormulas(0, 100);

        assertEquals(TestTools.ERROR_LIST_SIZE, formulas.size(), result.size());

        Collections.sort(formulas, TestTools.formulaComparator);
        Collections.sort(result, TestTools.formulaComparator);

        for (int i = 0; i < result.size(); i++)
        {
            deepCompare(formulas.get(i), result.get(i));
        }
    }

    @Test
    public void testGetAllFormulasPagination()
    {
        logger.info("Running FormulaTest#testGetAllFormulasPagination()");

        for (Formula f : formulas)
        {
            formulaService.createFormula(f);
        }

        int max_results = formulas.size() - 1;
        List<Formula> result = formulaService.getAllFormulas(1, max_results);
        assertEquals(max_results, result.size());
    }

    private void deepCompare(Formula expected, Formula actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given Formula has wrong xml.", expected.getXml(), actual.getXml());
        assertEquals("Given Formula has wrong note.", expected.getNote(), actual.getNote());
        assertEquals("Given Formula has wrong insert time.", expected.getInsertTime(), actual.getInsertTime());
        assertEquals("Given Formula has wrong user.", expected.getUser(), actual.getUser());
        assertEquals("Given Formula has wrong source document.", expected.getSourceDocument(), actual.getSourceDocument());

        List<Formula> expectedFormulas = expected.getSimilarFormulas();
        List<Formula> actualFormulas = actual.getSimilarFormulas();
        if (expectedFormulas != null && actualFormulas != null)
        {
            Collections.sort(expectedFormulas, TestTools.formulaComparator);
            Collections.sort(actualFormulas, TestTools.formulaComparator);
            for (int i = 0; i < actualFormulas.size(); i++)
            {
                deepCompare(expectedFormulas.get(i), actualFormulas.get(i));
            }
        }

        List<CanonicOutput> expectedOutputs = expected.getOutputs();
        List<CanonicOutput> actualOutputs = actual.getOutputs();
        if (expectedOutputs != null && actualOutputs != null)
        {
            Collections.sort(expectedOutputs, TestTools.canonicOutputComparator);
            Collections.sort(actualOutputs, TestTools.canonicOutputComparator);
            for (int i = 0; i < actualOutputs.size(); i++)
            {
                deepCompare(expectedOutputs.get(i), actualOutputs.get(i));
            }
        }
    }

    private void deepCompare(CanonicOutput expected, CanonicOutput actual)
    {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOutputForm(), actual.getOutputForm());
        assertEquals(expected.getSimilarForm(), actual.getSimilarForm());
    }
}
