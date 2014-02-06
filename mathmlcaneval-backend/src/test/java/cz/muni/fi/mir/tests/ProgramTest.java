/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.service.ProgramService;
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
public class ProgramTest
{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ProgramTest.class);

    @Autowired
    private ProgramService programService;
    private static final Long ID = new Long(1);
    private List<Program> programs = new ArrayList<>(4);

    @Before
    public void init()
    {
        programs = DataTestTools.providePrograms();
    }

    @Test
    public void testCreateAndGetProgram()
    {
        logger.info("Running ProgramTest#testCreateAndGetProgram()");
        programService.createProgram(programs.get(0));

        Program result = programService.getProgramByID(ID);

        assertNotNull("Program object was not created.", result);

        deepCompare(programs.get(0), result);
    }

    @Test
    public void testDeleteProgram()
    {
        logger.info("Running ProgramTest#testDeleteProgram()");

        programService.createProgram(programs.get(0));

        Program result = programService.getProgramByID(ID);

        assertNotNull("Program object was not created.", result);

        programService.deleteProgram(result);

        assertNull("Program object was not deleted.", programService.getProgramByID(ID));

    }

    @Test
    public void testUpdateProgram()
    {
        logger.info("Running ProgramTest#testUpdateProgram()");

        programService.createProgram(programs.get(0));

        Program result = programService.getProgramByID(ID);

        assertNotNull("Program object was not created.", result);

        result.setNote("zmenena note");
        result.setParameters("-a -b -c");
        result.setVersion("0.0.1");

        programService.updateProgram(result);

        Program updatedResult = programService.getProgramByID(ID);

        assertEquals("Program object does not have proper note after update.", "zmenena note", updatedResult.getNote());
        assertEquals("Program object does not have proper parameters after update.", "-a -b -c", updatedResult.getParameters());
        assertEquals("Program object does not have proper version after update.", "0.0.1", updatedResult.getVersion());
    }

    @Test
    public void testGetProgramByName()
    {
        logger.info("Running ProgramTest#testGetProgramByName()");
        for (Program p : programs)
        {
            programService.createProgram(p);
        }

        List<Program> result = programService.getProgramByName("texlive");

        assertEquals("wrong size", 3, result.size());
        List<Program> temp = programs.subList(0, 3);
        Collections.sort(result, TestTools.programComparator);
        Collections.sort(temp, TestTools.programComparator);

        for (int i = 0; i < result.size(); i++)
        {
            deepCompare(temp.get(i), result.get(i));
        }

    }

    @Test
    public void testGetProgramByNameAndVersion()
    {
        logger.info("Running ProgramTest#testGetProgramByNameAndVersion()");

        for (Program p : programs)
        {
            programService.createProgram(p);
        }

        List<Program> result = programService.getProgramByNameAndVersion("texlive", "2013.2");

        assertFalse(TestTools.ERROR_LIST_SIZE, result.isEmpty());
        assertEquals("ResultList does not have proper size.", 2, result.size());

        for (Program r : result)
        {
            assertEquals("Program in resultList does not have proper name.", "texlive", r.getName());
            assertEquals("Program in resultList does not have proper version", "2013.2", r.getVersion());
        }
    }

    @Test
    public void testGetAllPrograms()
    {
        logger.info("Running ProgramTest#testGetAllPrograms()");

        for (Program p : programs)
        {
            programService.createProgram(p);
        }

        List<Program> result = programService.getAllPrograms();

        assertEquals(TestTools.ERROR_LIST_SIZE, programs.size(), result.size());

        Collections.sort(programs, TestTools.programComparator);
        Collections.sort(result, TestTools.programComparator);

        for (int i = 0; i < result.size(); i++)
        {
            deepCompare(programs.get(i), result.get(i));
        }
    }

    private void deepCompare(Program expected, Program actual)
    {
        assertEquals(TestTools.ERROR_WRONG_ID, expected.getId(), actual.getId());
        assertEquals("Given Program has wrong name.", expected.getName(), actual.getName());
        assertEquals("Given Program has wrong note.", expected.getNote(), actual.getNote());
        assertEquals("Given Program has wrong parameters.", expected.getParameters(), actual.getParameters());
        assertEquals("Given Program has wrong version.", expected.getVersion(), actual.getVersion());
    }
}
