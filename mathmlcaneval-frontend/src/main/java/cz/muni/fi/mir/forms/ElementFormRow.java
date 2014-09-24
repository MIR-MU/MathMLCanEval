/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.Objects;

/**
 *
 * @author emptak
 */
public class ElementFormRow
{
    private ElementForm element;
    private Integer value;

    public ElementForm getElement()
    {
        return element;
    }

    public void setElement(ElementForm element)
    {
        this.element = element;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.element);
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
        final ElementFormRow other = (ElementFormRow) obj;
        if (!Objects.equals(this.element, other.element))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ElementFormRow{" + "element=" + element + ", value=" + value + '}';
    }

    
}