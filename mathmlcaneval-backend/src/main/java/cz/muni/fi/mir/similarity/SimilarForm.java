/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.similarity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Empt
 */
@Table(name = "canonicOutput")
@Entity
public class SimilarForm implements Serializable
{
    private static final long serialVersionUID = 3861587013945745864L;
    
    @Id
    @Column(name="id")
    private Long id;
    
    @Column(name="similarForm")
    private String similarForm;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSimilarForm()
    {
        return similarForm;
    }

    public void setSimilarForm(String similarForm)
    {
        this.similarForm = similarForm;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
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
        final SimilarForm other = (SimilarForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "SimilarForm{" + "id=" + id + ", similarForm=" + similarForm + '}';
    }
    
    
}
