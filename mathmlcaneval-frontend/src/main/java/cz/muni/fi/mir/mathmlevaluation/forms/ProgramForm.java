
package cz.muni.fi.mir.mathmlevaluation.forms;

import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class representing every form related to Program on presentation layer.
 * Class servers as so called Data Transfer object. Use {@link org.dozer.Mapper} map
 * methods between conversions
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public class ProgramForm 
{
    private Long id;
    @NotEmpty(message = "{validator.program.name.empty}")
    private String name;
    private String version;    
    private String parameters;    
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
        final ProgramForm other = (ProgramForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Program{" + "id=" + id + ", name=" + name + ", version=" + version + ", parameters=" + parameters + ", note=" + note + '}';
    }
}
