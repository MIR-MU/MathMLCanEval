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

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author emptak
 */
@Entity(name = "element")
public class Element implements Serializable
{
    private static final long serialVersionUID = -7653612446719259055L;
    @Id
    @Column(name = "element_id",nullable = false)
    @SequenceGenerator(name="elementid_seq", sequenceName="elementid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "elementid_seq")
    private Long id;
    
    @Column(name = "elementName")
    private String elementName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getElementName()
    {
        return elementName;
    }

    public void setElementName(String elementName)
    {
        this.elementName = elementName;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.elementName);
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
        final Element other = (Element) obj;
        if(this.id == null)
        {
            return this.getElementName().equals(other.getElementName());
        }
        else
        {
            return Objects.equals(this.id, other.id);
        }        
    }
    
    
    

    @Override
    public String toString()
    {
        return "Element{" + "id=" + id + ", elementName=" + elementName + '}';
    }
}
