/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.forms;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Empt
 */
public class CanonicOutputForm
{
    private Long id;
    private String outputForm;
    private String similarForm;
    private Set<FormulaForm> parents;
    private long runningTime;
    private ApplicationRunForm applicationRunForm;
    private String hashValue;
    private Set<AnnotationForm> annotationForms;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOutputForm()
    {
        return outputForm;
    }

    public void setOutputForm(String outputForm)
    {
        this.outputForm = outputForm;
    }

    public Set<FormulaForm> getParents()
    {
        return parents;
    }

    public void setParents(Set<FormulaForm> parents)
    {
        this.parents = parents;
    }

    public long getRunningTime()
    {
        return runningTime;
    }

    public void setRunningTime(long runningTime)
    {
        this.runningTime = runningTime;
    }

    public ApplicationRunForm getApplicationRun()
    {
        return applicationRunForm;
    }

    public void setApplicationRun(ApplicationRunForm applicationRun)
    {
        this.applicationRunForm = applicationRun;
    }

    public Set<AnnotationForm> getAnnotationForms()
    {
        return annotationForms;
    }

    public void setAnnotationForms(Set<AnnotationForm> annotationForms)
    {
        this.annotationForms = annotationForms;
    }

    public String getSimilarForm()
    {
        return similarForm;
    }

    public void setSimilarForm(String similarForm)
    {
        this.similarForm = similarForm;
    }

    public ApplicationRunForm getApplicationRunForm()
    {
        return applicationRunForm;
    }

    public void setApplicationRunForm(ApplicationRunForm applicationRunForm)
    {
        this.applicationRunForm = applicationRunForm;
    }

    public String getHashValue()
    {
        return hashValue;
    }

    public void setHashValue(String hashValue)
    {
        this.hashValue = hashValue;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final CanonicOutputForm other = (CanonicOutputForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "CanonicOutput{" + "id=" + id + ", outputForm=" + outputForm + ", similarForm=" + similarForm + ", parents=" + parents + ", runningTime=" + runningTime + ", applicationRun=" + applicationRunForm + ", annotations=" + annotationForms + '}';
    }
}
