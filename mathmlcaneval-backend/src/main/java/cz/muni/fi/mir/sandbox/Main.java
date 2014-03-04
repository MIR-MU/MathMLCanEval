package cz.muni.fi.mir.sandbox;

import cz.muni.fi.mir.db.dao.impl.CanonicOutputDAOImpl;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.services.FormulaCreator;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.SimilarityFormConverter;
import cz.muni.fi.mir.wrappers.ApplicationContextWrapper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.io.IOUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Empt
 */
public class Main
{
    private static final ApplicationContextWrapper A = ApplicationContextWrapper.getInstance();
    
    private static UserService userService = A.getBean("userService");
    private static UserRoleService userRoleService = A.getBean("userRoleService");
    private static SimilarityFormConverter similarityFormConverter = A.getBean("similarityFormConverter");
    private static SourceDocumentService sourceDocumentService = A.getBean("sourceDocumentService");
    private static CanonicOutputDAOImpl canonicDAO = A.getBean("canonicOutputDAO");
    private static CanonicOutputService canonicOutputService = A.getBean("canonicOutputService");
    private static FormulaService formulaService = A.getBean("formulaService");
    private static FormulaCreator formulaCreator = A.getBean("formulaCreator");
    private static MathCanonicalizerLoader mathCanonicalizerLoader = A.getBean("mathCanonicalizerLoader");
    
    public static void main(String[] args) throws IOException
    {
        System.out.println(mathCanonicalizerLoader);
        
        
       // System.out.println(formulaCreator.extractFormula(sourceDocumentService.getSourceDocumentByID(1L)));
        
 //       FormulaCreator.extractFormula(sourceDocumentService.getSourceDocumentByID(1L).getDocumentPath());
//        FileDirectoryService fds = new FileDirectoryService();
//        List<SourceDocument> list = Collections.emptyList();
//        try
//        {
//            list = fds.exploreDirectory(FileSystems.getDefault().getPath("C:\\Users\\Dominik\\Desktop"),"*.xml");
//        }
//        catch(FileNotFoundException nfe)
//        {
//        }
//        
//        for(SourceDocument sd : list)
//        {
//            System.out.println(sd);
//        }

//        PipedOutputStream pipeOut = new PipedOutputStream();
//        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
//        System.setOut(new PrintStream(pipeOut));
//        
        
        
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(baos);
//        // IMPORTANT: Save the old System.out!
//        PrintStream old = System.out;
//        // Tell Java to use your special stream
//        System.setOut(ps);
//
//        //System.out.println(lps.getBuilder().toString());
//        MathCanonicalizerLoader loader = null;
//        try
//        {
//            loader = MathCanonicalizerLoader.newInstance("1.0.0", "C:\\Users\\Dominik\\Downloads\\MathMLCan-master\\MathMLCan-master\\target", MathCanonicalizerLoader.Variant.WITH_DEPENDENCIES);
//        }
//        catch (FileNotFoundException nfe)
//        {
//            nfe.printStackTrace();
//        }
//        Formula f = formulaService.getFormulaByID(1L);
//        if (loader != null)
//        {
//            //Formula f = formulaService.getFormulaByID(1L);
////            User u = userService.getUserByID(new Long(2));
////            SourceDocument sd = sourceDocumentService.getSourceDocumentByID(new Long(1));
////            Formula f = new Formula();
////            f.setUser(u);
////            f.setSourceDocument(sd);
////            StringBuilder sb = new StringBuilder();
////            List<String> lines = Files.readAllLines(Paths.get(sd.getDocumentPath()), Charset.defaultCharset());
////            for(String line : lines)
////            {
////                sb.append(line).append("\n");
////            }
////            f.setXml(sb.toString());
//            
//            //System.out.println(f);
////            formulaService.createFormula(f);
//            loader.execute(f,null);
//        }
//
//        System.out.flush();
//        System.setOut(old);
//        
//        CanonicOutput co = new CanonicOutput();
//        
//        co.setOutputForm(baos.toString());
//        
//        System.out.println(co);
        
//        canonicOutputService.createCanonicOutput(co);
//        
//        List<CanonicOutput> outputs = new ArrayList<>(f.getOutputs());
//        outputs.add(co);
//        
//        f.setOutputs(new HashSet<>(outputs));
//        
//        formulaService.updateFormula(f);
        

//        System.out.println(baos.toString());
//        StringWriter writer = new StringWriter();
//        IOUtils.copy(pipeIn, writer);
//        IOUtils.
//        
//        System.out.println(writer.toString().length());
//        System.out.println(similarityFormConverter.convert(getFirstXML()));
//        System.out.println(similarityFormConverter.convert(getSecondXML()));
//        
//        System.out.println(StringUtils.getLevenshteinDistance(similarityFormConverter.convert(getFirstXML()), "x*y+zx+z"));
//        System.out.println(userService.getAllUsers());
//        
//        
//        System.out.println(canonicOutputService.findSimilarForms(canonicOutputService.getCanonicOutputByID(1L), 2,false));
//        System.out.println(StringUtils.getLevenshteinDistance("abad", "zzzabcd"));
    }

//    private static double getScore(String s1, String s2)
//    {
//       // if(s1)
//    }
}
