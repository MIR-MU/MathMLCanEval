/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


/**
 *
 * @author Empt
 */
@Entity(name = "canonicOutput")
@Indexed
public class CanonicOutput implements Serializable
{

    private static final long serialVersionUID = 6956045766345845859L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outputForm",columnDefinition = "TEXT",length = 10000)
    @Field
    private String outputForm;

    @Column(name = "similarForm")
    @Field
    private String similarForm;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="parents_id")
    private List<Formula> parents;

    @Column(name = "runtime")
    private long runningTime;

    @ManyToOne
    private ApplicationRun applicationRun;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Annotation> annotations;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOutputForm()
    {
        return outputForm;
    }

    public void setOutputForm(String outputForm)
    {
        this.outputForm = outputForm;
    }

    public List<Formula> getParents()
    {
        return parents;
    }

    public void setParents(List<Formula> parents)
    {
        this.parents = parents;
    }

    public long getRunningTime()
    {
        return runningTime;
    }

    public void setRunningTime(long runningTime)
    {
        this.runningTime = runningTime;
    }

    public ApplicationRun getApplicationRun()
    {
        return applicationRun;
    }

    public void setApplicationRun(ApplicationRun applicationRun)
    {
        this.applicationRun = applicationRun;
    }

    public List<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations)
    {
        this.annotations = annotations;
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
        final CanonicOutput other = (CanonicOutput) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "CanonicOutput{" + "id=" + id + ", outputForm=" + outputForm + ", similarForm=" + similarForm + ", parents=" + parents + ", runningTime=" + runningTime + ", applicationRun=" + applicationRun + ", annotations=" + annotations + '}';
    }

    @PreRemove
    private void removeOutputFromParents()
    {
        for (Formula f : parents)
        {
            f.getOutputs().remove(this);
        }
    }
}
