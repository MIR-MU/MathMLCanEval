/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.tools.EntityFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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
public class SourceDocumentTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SourceDocumentTest.class);

    @Autowired
    SourceDocumentService sourceDocumentService;
    private List<SourceDocument> docs = new ArrayList<>(4);
    private static final Long ID = new Long(1);

    @Before
    public void init()
    {
        docs = DataTestTools.provideSourceDocuments();
    }

    @Test
    public void testCreateAndGetSourceDocument()
    {
        logger.info("Running SourceDocumentTest#testCreateAndGetSourceDocument()");
        sourceDocumentService.createSourceDocument(docs.get(0));

        SourceDocument result = sourceDocumentService.getSourceDocumentByID(ID);

        assertNotNull("SourceDocument object was not created.", result);

        deepCompare(docs.get(0), result);
    }

    @Test
    public void testUpdateSourceDocument()
    {
        logger.info("Running SourceDocumentTest#createAndGetUser()");

        sourceDocumentService.createSourceDocument(docs.get(0));

        SourceDocument result = sourceDocumentService.getSourceDocumentByID(ID);

        assertNotNull("SourceDocument object was not created.", result);

        result.setNote("zmenena poznamka");
        result.setDocumentPath("/home/root/input/doc.xml");

        sourceDocumentService.updateSourceDocument(result);

        SourceDocument updatedResult = sourceDocumentService.getSourceDocumentByID(ID);

        assertEquals("SourceDocument object does not have proper note after update.", "zmenena poznamka", updatedResult.getNote());
        assertEquals("SourceDocument object does not have proper path after update.", "/home/root/input/doc.xml", updatedResult.getDocumentPath());
    }

    @Test
    public void testDeleteSourceDocument()
    {
        logger.info("Running SourceDocumentTest#createAndGetUser()");

        sourceDocumentService.createSourceDocument(docs.get(0));

        SourceDocument result = sourceDocumentService.getSourceDocumentByID(ID);

        assertNotNull("SourceDocument object was not created.", result);

        sourceDocumentService.deleteSourceDocument(result);

        assertNull("SourceDocument object was not deleted.", sourceDocumentService.getSourceDocumentByID(ID));
    }

    @Test
    public void testGetSourceDocumentByPath()
    {
        logger.info("Running SourceDocumentTest#createAndGetUser()");
        for (SourceDocument sd : docs)
        {
            sourceDocumentService.createSourceDocument(sd);
        }

        assertEquals(TestTools.ERROR_LIST_SIZE, 4, sourceDocumentService.getAllDocuments().size());
        SourceDocument result = sourceDocumentService.getSourceDocumentByPath("/home/empt/input/data/test2.xhtml");

        deepCompare(docs.get(1), result);
    }

    @Test
    public void getAllDocuments()
    {
        logger.info("Running SourceDocumentTest#createAndGetUser()");

        for (SourceDocument sd : docs)
        {
            sourceDocumentService.createSourceDocument(sd);
        }

        List<SourceDocument> result = sourceDocumentService.getAllDocuments();

        assertEquals(TestTools.ERROR_LIST_SIZE, docs.size(), result.size());

        Collections.sort(docs, TestTools.sourceDocumentComparator);
        Collections.sort(result, TestTools.sourceDocumentComparator);

        for (int i = 0; i < docs.size(); i++)
        {
            deepCompare(docs.get(i), result.get(i));
        }
    }

    @Test
    public void getDocumentsOnSubPath()
    {
        logger.info("Running SourceDocumentTest#createAndGetUser()");
        for (SourceDocument sd : docs)
        {
            sourceDocumentService.createSourceDocument(sd);
        }

        assertEquals(TestTools.ERROR_LIST_SIZE, 4, sourceDocumentService.getAllDocuments().size());

        List<SourceDocument> result = sourceDocumentService.getDocumentsOnSubPath("/home/empt/input/data/");

        assertEquals(TestTools.ERROR_LIST_SIZE, 2, result.size());

        for (SourceDocument sd : result)
        {
            assertEquals("SourceDocument in resultList does not have proper path.", true, sd.getDocumentPath().startsWith("/home/empt/input/data/"));
        }
    }

    private void deepCompare(SourceDocument expected, SourceDocument actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given SourceDocument does not have expected path.", expected.getDocumentPath(), actual.getDocumentPath());
        assertEquals("Given SourceDocument does not have expected note.", expected.getNote(), actual.getNote());
    }
}
