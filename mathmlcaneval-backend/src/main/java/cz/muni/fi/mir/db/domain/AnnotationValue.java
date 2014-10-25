/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

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
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Entity(name = "annotationValue")
public class AnnotationValue implements Serializable
{
    private static final long serialVersionUID = -1311727412278415211L;
    
    @Id
    @Column(name = "annotationv_id",nullable = false)
    @SequenceGenerator(name="annotationvid_seq", sequenceName="annotationvid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "annotationvid_seq")
    private Long id;
    @Column(name = "annotationValue",unique = true)
    private String value;
    @Column(name="description")
    private String description;

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
        final AnnotationValue other = (AnnotationValue) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "AnnotationValue{" + "id=" + id + ", value=" + value + ", description=" + description + '}';
    }
}
