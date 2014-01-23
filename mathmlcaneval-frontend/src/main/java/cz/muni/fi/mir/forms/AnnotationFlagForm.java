/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.Objects;

/**
 * This class came out as idea from Martin Liska. He argued that is not good to
 * hold validity result in note of annotation. Therefore each annotation has
 * this Flag which flags given result. Possible values might be like
 * PROPER_RESULT, MIGHT_BE_PROPER, WRONG, CHECK_PLS or whatever.
 *
 * @version 1.0
 * @since 1.0
 * @author Dominik Szalai
 */
public class AnnotationFlagForm
{
    private Long id;
    private String flagValue;
    public static final String VALUE_PROPER_RESULT = "PROPER_RESULT";    
    public static final String VALUE_MIGHT_BE_PROPER = "MIGHT_BE_PROPER";    
    public static final String VALUE_WRONG = "WRONG";
    public static final String VALUE_CHECK_PLS = "CHECK_PLS";

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
        final AnnotationFlagForm other = (AnnotationFlagForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "AnnotationFlag{" + "id=" + id + ", flagValue=" + flagValue + '}';
    }
}
