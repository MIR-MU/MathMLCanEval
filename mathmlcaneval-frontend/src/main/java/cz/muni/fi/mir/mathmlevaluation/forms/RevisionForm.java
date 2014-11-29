/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.forms;

import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emptak
 */
public class RevisionForm
{
    private Long id;
    @NotEmpty(message = "{validator.revision.hash.empty}")
    private String revisionHash;
    private String note;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRevisionHash()
    {
        return revisionHash;
    }

    public void setRevisionHash(String revisionHash)
    {
        this.revisionHash = revisionHash;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final RevisionForm other = (RevisionForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Revision{" + "id=" + id + ", revisionHash=" + revisionHash + ", note=" + note + '}';
    }

}
