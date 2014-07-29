/*
 * To change this template, choose Tools | Templates
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
 * @author Empt
 */
@Entity(name = "program")
public class Program implements Serializable
{

    private static final long serialVersionUID = 7394481046699929994L;

    @Id
    @Column(name = "program_id",nullable = false)
    @SequenceGenerator(name="programid_seq", sequenceName="programid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "programid_seq")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "version")
    private String version;
    @Column(name = "pparameters") // because parameters is SQL-99 reserved keyword....
    private String parameters;
    @Column(name = "note")
    private String note;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getParameters()
    {
        return parameters;
    }

    public void setParameters(String parameters)
    {
        this.parameters = parameters;
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
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Program other = (Program) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Program{" + "id=" + id + ", name=" + name + ", version=" + version + ", parameters=" + parameters + ", note=" + note + '}';
    }
}
