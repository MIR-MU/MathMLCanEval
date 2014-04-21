/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.index;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 *
 * @author emptak
 */
@Entity(name = "indexedoutput")
@Indexed
public class IndexedOutput implements Serializable
{
    private static final long serialVersionUID = 8531160062646702811L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private Long canonicOutputID;
    
    @Column
    @Field
    private String levenstheinForm;
    
    @Column
    private String countForm;
    
    @IndexedEmbedded()
    @Transient
    private Map<String,Integer> mapCountForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCanonicOutputID() {
        return canonicOutputID;
    }

    public void setCanonicOutputID(Long canonicOutputID) {
        this.canonicOutputID = canonicOutputID;
    }

    public String getLevenstheinForm() {
        return levenstheinForm;
    }

    public void setLevenstheinForm(String levenstheinForm) {
        this.levenstheinForm = levenstheinForm;
    }

    public String getCountForm()
    {
        return countForm;
    }

    public void setCountForm(String countForm)
    {
        this.countForm = countForm;
    }

    public Map<String, Integer> getMapCountForm()
    {
        return mapCountForm;
    }

    public void setMapCountForm(Map<String, Integer> mapCountForm)
    {
        this.mapCountForm = mapCountForm;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IndexedOutput other = (IndexedOutput) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "IndexedOutput{" + "id=" + id + ", canonicOutputID=" + canonicOutputID + ", levenstheinForm=" + levenstheinForm + '}';
    }
}
