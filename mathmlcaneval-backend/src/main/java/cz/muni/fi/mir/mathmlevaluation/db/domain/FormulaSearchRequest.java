/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlevaluation.db.domain;

import java.util.Map;

/**
 *
 * @author emptak
 */
public class FormulaSearchRequest
{
    private Program program;
    private SourceDocument sourceDocument;
    private Configuration configuration;
    private Revision revision;
    private ApplicationRun applicationRun;
    private String annotationContent;
    private String formulaContent;
    private Integer coRuns;
    private Map<Element,Integer> elements;

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    public SourceDocument getSourceDocument()
    {
        return sourceDocument;
    }

    public void setSourceDocument(SourceDocument sourceDocument)
    {
        this.sourceDocument = sourceDocument;
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    public Revision getRevision()
    {
        return revision;
    }

    public void setRevision(Revision revision)
    {
        this.revision = revision;
    }

    public ApplicationRun getApplicationRun()
    {
        return applicationRun;
    }

    public void setApplicationRun(ApplicationRun applicationRun)
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

    public Map<Element, Integer> getElements()
    {
        return elements;
    }

    public void setElements(Map<Element, Integer> elements)
    {
        this.elements = elements;
    }

    
}
