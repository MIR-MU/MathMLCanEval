/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Empt
 */
public class SourceDocumentForm
{
    private Long id; 
    @NotEmpty
    private String name;
    private String note; 

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final SourceDocumentForm other = (SourceDocumentForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "SourceDocumentForm{" + "id=" + id + ", name=" + name + ", note=" + note + '}';
    }
}
