/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.list.LazyList;

/**
 *
 * @author emptak
 */
public class FormulaSearchRequestForm
{

    private ProgramForm program;
    private SourceDocumentForm sourceDocument;
    private ConfigurationForm configuration;
    private String annotationContent;
    private String formulaContent;
    private Integer coRuns;
    private List<ElementFormRow> elementRows = LazyList.lazyList(new ArrayList<ElementFormRow>(), FactoryUtils.instantiateFactory(ElementFormRow.class));

    public Integer getCoRuns()
    {
        return coRuns;
    }

    public void setCoRuns(Integer coRuns)
    {
        this.coRuns = coRuns;
    }
    
    public String getFormulaContent()
    {
        return formulaContent;
    }

    public void setFormulaContent(String formulaContent)
    {
        this.formulaContent = formulaContent;
    }

    
    
    public List<ElementFormRow> getElementRows()
    {
        return elementRows;
    }

    public void setElementRows(List<ElementFormRow> elementRows)
    {
        this.elementRows = elementRows;
    }

    
    public String getAnnotationContent()
    {
        return annotationContent;
    }

    public void setAnnotationContent(String annotationContent)
    {
        this.annotationContent = annotationContent;
    }

    public ConfigurationForm getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(ConfigurationForm configuration)
    {
        this.configuration = configuration;
    }

    public SourceDocumentForm getSourceDocument()
    {
        return sourceDocument;
    }

    public void setSourceDocument(SourceDocumentForm sourceDocument)
    {
        this.sourceDocument = sourceDocument;
    }

    public ProgramForm getProgram()
    {
        return program;
    }

    public void setProgram(ProgramForm program)
    {
        this.program = program;
    }

    // Map<Element,Boolean> elements,List<String> annotations,Integer size,Integer runsCount

    @Override
    public String toString()
    {
        return "FormulaSearchRequestForm{" + "program=" + program + ", sourceDocument=" + sourceDocument + ", configuration=" + configuration + ", annotationContent=" + annotationContent + ", elementRows=" + elementRows + '}';
    }
    
}
