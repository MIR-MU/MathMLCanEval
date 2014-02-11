package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The purpose of this class is to capture Configuration which was used during testing.
 * Because value is stored as Text String with length 10000 so capacity is nearly unlimited.
 * If we are using PostgreSQL database column type will be converted into TEXT.
 * 
 * @author Dominik Szalai
 * 
 * @version 1.0
 * @since 1.0
 */
@Entity(name = "configuration")
public class Configuration implements Serializable
{

    private static final long serialVersionUID = -4875490381198661605L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config",columnDefinition = "text",length = 10000)
    private String config;

    @Column(name = "name")
    private String name;

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

    public String getConfig()
    {
        return config;
    }

    public void setConfig(String config)
    {
        this.config = config;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Configuration other = (Configuration) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Configuration{" + "id=" + id + ", config=" + config + ", name=" + name + ", note=" + note + '}';
    }
}
