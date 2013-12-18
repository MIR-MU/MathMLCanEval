/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *  This class came out as idea from Martin Liska. He argued that
 *  is not good to hold validity result in note of annotation. Therefore each
 *  annotation has this Flag which flags given result. Possible values might be like
 *  PROPER_RESULT, MIGHT_BE_PROPER, WRONG, CHECK_PLS or whatever.
 * @author Empt
 */
@Entity(name = "annotationflag")
public class AnnotationFlag implements Serializable
{
    private static final long serialVersionUID = -779680048147550854L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String flagValue;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFlagValue()
    {
        return flagValue;
    }

    public void setFlagValue(String flagValue)
    {
        this.flagValue = flagValue;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final AnnotationFlag other = (AnnotationFlag) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "AnnotationFlag{" + "id=" + id + ", flagValue=" + flagValue + '}';
    }
}
