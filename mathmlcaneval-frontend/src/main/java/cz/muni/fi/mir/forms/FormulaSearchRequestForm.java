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
    private RevisionForm revision;
    private ApplicationRunForm applicationRun;
    private String annotationContent;
    private String formulaContent;
    private Integer coRuns;
    private List<ElementFormRow> elementRows = LazyList.lazyList(new ArrayList<ElementFormRow>(), FactoryUtils.instantiateFactory(ElementFormRow.class));

    public ProgramForm getProgram()
    {
        return program;
    }

    public void setProgram(ProgramForm program)
    {
        this.program = program;
    }

    public SourceDocumentForm getSourceDocument()
    {
        return sourceDocument;
    }

    public void setSourceDocument(SourceDocumentForm sourceDocument)
    {
        this.sourceDocument = sourceDocument;
    }

    public ConfigurationForm getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(ConfigurationForm configuration)
    {
        this.configuration = configuration;
    }

    public RevisionForm getRevision()
    {
        return revision;
    }

    public void setRevision(RevisionForm revision)
    {
        this.revision = revision;
    }

    public ApplicationRunForm getApplicationRun()
    {
        return applicationRun;
    }

    public void setApplicationRun(ApplicationRunForm applicationRun)
    {
        this.applicationRun = applicationRun;
    }

    public String getAnnotationContent()
    {
        return annotationContent;
    }

    public void setAnnotationContent(String annotationContent)
    {
        this.annotationContent = annotationContent;
    }

    public String getFormulaContent()
    {
        return formulaContent;
    }

    public void setFormulaContent(String formulaContent)
    {
        this.formulaContent = formulaContent;
    }

    public Integer getCoRuns()
    {
        return coRuns;
    }

    public void setCoRuns(Integer coRuns)
    {
        this.coRuns = coRuns;
    }

    public List<ElementFormRow> getElementRows()
    {
        return elementRows;
    }

    public void setElementRows(List<ElementFormRow> elementRows)
    {
        this.elementRows = elementRows;
    }
    
    @Override
    public String toString()
    {
        return "FormulaSearchRequestForm{" + "program=" + program + ", sourceDocument=" + sourceDocument + ", configuration=" + configuration + ", annotationContent=" + annotationContent + ", elementRows=" + elementRows + '}';
    }
    
}
