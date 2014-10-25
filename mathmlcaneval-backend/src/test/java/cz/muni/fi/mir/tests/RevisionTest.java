/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.service.RevisionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import static org.junit.Assert.*;

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
public class RevisionTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RevisionTest.class);

    @Autowired
    private RevisionService revisionService;
    private List<Revision> revs = new ArrayList<>(4);
    private static final Long ID = new Long(1);

    @Before
    public void init()
    {
        revs = DataTestTools.provideRevisions();
    }

    @Test
    public void testCreateAndGetRevision()
    {
        logger.info("Running RevisionTest#testCreateAndGetRevision()");

        revisionService.createRevision(revs.get(0));

        Revision result = revisionService.getRevisionByID(ID);

        assertNotNull("Revision object was not created.", result);

        deepCompare(revs.get(0), result);
    }

    @Test
    public void testDeleteRevision()
    {
        logger.info("Running RevisionTest#testDeleteRevision()");

        revisionService.createRevision(revs.get(0));
        Revision result = revisionService.getRevisionByID(ID);

        assertNotNull("Revision object was not created.", result);

        revisionService.deleteRevision(result);

        assertNull("Revision object was not deleted.", revisionService.getRevisionByID(ID));
    }

    @Test
    public void testUpdateRevision()
    {
        logger.info("Running RevisionTest#testUpdateRevision()");

        revisionService.createRevision(revs.get(0));
        Revision result = revisionService.getRevisionByID(ID);

        result.setRevisionHash("744fbdaa4f1618401a4ad4d177a65a723a9f6ef2");
        result.setNote("zmenena poznamka");

        revisionService.updateRevision(result);

        Revision updatedRevision = revisionService.getRevisionByID(ID);

        assertEquals("Revision object does not have proper hash after update.", "744fbdaa4f1618401a4ad4d177a65a723a9f6ef2", updatedRevision.getRevisionHash());
        assertEquals("Revision object does not have proper note after update.", "zmenena poznamka", updatedRevision.getNote());;
    }

    @Test
    public void testGetAllRevisions()
    {
        logger.info("Running RevisionTest#testGetAllRevisions()");

        for (Revision r : revs)
        {
            revisionService.createRevision(r);
        }

        List<Revision> result = revisionService.getAllRevisions();

        assertEquals(TestTools.ERROR_LIST_SIZE, revs.size(), result.size());

        Collections.sort(result, TestTools.revisionComparator);
        Collections.sort(revs, TestTools.revisionComparator);

        for (int i = 0; i < revs.size(); i++)
        {
            deepCompare(revs.get(i), result.get(i));
        }
    }

    private void deepCompare(Revision expected, Revision actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given revision does not have expected hash.", expected.getRevisionHash(), actual.getRevisionHash());
        assertEquals("Given revision does not have expected note.", expected.getNote(), actual.getNote());
    }
}
