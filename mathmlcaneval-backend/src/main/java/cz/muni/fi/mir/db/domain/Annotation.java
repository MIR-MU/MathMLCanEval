package cz.muni.fi.mir.db.domain;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
@Entity(name = "annotation")
public class Annotation implements Serializable
{

    private static final long serialVersionUID = -8493177720663943928L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private AnnotationFlag annotationFlag;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
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

    public AnnotationFlag getAnnotationFlag()
    {
        return annotationFlag;
    }

    public void setAnnotationFlag(AnnotationFlag annotationFlag)
    {
        this.annotationFlag = annotationFlag;
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
        final Annotation other = (Annotation) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Annotation{" + "id=" + id + ", user=" + user + ", note=" + note + ", annotationFlag=" + annotationFlag + '}';
    }
}