/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.Objects;
import java.util.Set;
import org.joda.time.DateTime;

/**
 *
 * @author Empt
 */
public class FormulaForm
{
    private Long id;        
    private String xml;                         
    private String note;
    private SourceDocumentForm sourceDocumentForm;
    private DateTime insertTime;
    private ProgramForm programForm; 
    private UserForm userForm;    
    private Set<CanonicOutputForm> canonicOutputForms;  
    private Set<FormulaForm> similarFormulaForms;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getXml()
    {
        return xml;
    }

    public void setXml(String xml)
    {
        this.xml = xml;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public SourceDocumentForm getSourceDocumentForm()
    {
        return sourceDocumentForm;
    }

    public void setSourceDocumentForm(SourceDocumentForm sourceDocumentForm)
    {
        this.sourceDocumentForm = sourceDocumentForm;
    }

    public DateTime getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(DateTime insertTime)
    {
        this.insertTime = insertTime;
    }

    public ProgramForm getProgramForm()
    {
        return programForm;
    }

    public void setProgramForm(ProgramForm programForm)
    {
        this.programForm = programForm;
    }

    public UserForm getUserForm()
    {
        return userForm;
    }

    public void setUserForm(UserForm userForm)
    {
        this.userForm = userForm;
    }

    public Set<CanonicOutputForm> getCanonicOutputForms()
    {
        return canonicOutputForms;
    }

    public void setCanonicOutputForms(Set<CanonicOutputForm> canonicOutputForms)
    {
        this.canonicOutputForms = canonicOutputForms;
    }

    public Set<FormulaForm> getSimilarFormulaForms()
    {
        return similarFormulaForms;
    }

    public void setSimilarFormulaForms(Set<FormulaForm> similarFormulaForms)
    {
        this.similarFormulaForms = similarFormulaForms;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final FormulaForm other = (FormulaForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Formula{" + "id=" + id + ", xml=" + xml + ", note=" + note + ", sourceDocument=" + sourceDocumentForm + ", insertTime=" + insertTime + ", program=" + programForm + ", user=" + userForm + ", outputs=" + canonicOutputForms + ", fimilarFormulas=" + similarFormulaForms + '}';
    }
}
