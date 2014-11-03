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
package cz.muni.fi.mir.forms;

import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class AnnotationValueForm
{
    public enum Type
    {
        FORMULA,
        CANONICOUTPUT,
        BOTH
    }
    
    private Long id;
    @NotEmpty
    private String value;
    private String description;
    private Type type;
    private String icon;
    private String label;
    private Integer priority;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final AnnotationValueForm other = (AnnotationValueForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "AnnotationValue{" + "id=" + id + ", value=" + value + ", description=" + description + ", type=" + type + ", icon=" + icon + ", label=" + label + '}';
    }
}
