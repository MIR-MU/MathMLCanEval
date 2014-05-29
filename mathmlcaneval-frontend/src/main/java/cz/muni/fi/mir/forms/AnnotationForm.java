package cz.muni.fi.mir.forms;

import java.util.Objects;

/**
 * The purpose of this class is to capture some comments of given Canonic
 * output. Class remembers user who submitted this annotation, note and
 * {@link cz.muni.fi.mir.domain.AnnotationFlag} which specifies in closer terms
 * the output result.
 *
 * @version 1.0
 * @since 1.0
 * @author Dominik Szalai
 */
public class AnnotationForm
{
    private Long id;
    private UserForm user;
    private String note;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public UserForm getUser()
    {
        return user;
    }

    public void setUser(UserForm user)
    {
        this.user = user;
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
        int hash = 5;
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
        final AnnotationForm other = (AnnotationForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Annotation{" + "id=" + id + ", user=" + user + ", note=" + note + '}';
    }
}
