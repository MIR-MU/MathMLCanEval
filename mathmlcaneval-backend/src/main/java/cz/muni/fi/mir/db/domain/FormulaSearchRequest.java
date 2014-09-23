/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

/**
 *
 * @author emptak
 */
public class FormulaSearchRequest
{
    private Program program;
    private SourceDocument sourceDocument;
    private Configuration configuration;
    private String annotationContent;

    public String getAnnotationContent()
    {
        return annotationContent;
    }

    public void setAnnotationContent(String annotationContent)
    {
        this.annotationContent = annotationContent;
    }
    
    

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }
    

    public SourceDocument getSourceDocument()
    {
        return sourceDocument;
    }

    public void setSourceDocument(SourceDocument sourceDocument)
    {
        this.sourceDocument = sourceDocument;
    }  

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    
    
    // Map<Element,Boolean> elements,List<String> annotations,Integer size,Integer runsCount

    @Override
    public String toString()
    {
        return "FormulaSearchRequest{" + "program=" + program + ", sourceDocument=" + sourceDocument + ", configuration=" + configuration + '}';
    }
    
    
    
}
