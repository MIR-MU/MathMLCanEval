/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Empt
 */
@Entity(name="canonicOutput")
public class CanonicOutput implements Serializable
{
    private static final long serialVersionUID = 6956045766345845859L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="revision")
    private String revision;
    
    @Column(name="output")
    private String output;
    
    @OneToMany
    private Set<Formula> parents;
    
    @Column(name="runtime")
    private long runningTime;
    
    @ManyToOne
    private ApplicationRun applicationRun;
    
    @OneToMany
    private Set<Annotation> annotations;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRevision()
    {
        return revision;
    }

    public void setRevision(String revision)
    {
        this.revision = revision;
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }

    public Set<Formula> getParents()
    {
        return parents;
    }

    public void setParents(Set<Formula> parents)
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

    public Set<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations)
    {
        this.annotations = annotations;
    }

    @Override
    public String toString()
    {
        return "CanonicOutput{" + "id=" + id + ", revision=" + revision + ", output=" + output + ", parents=" + parents + ", runningTime=" + runningTime + ", applicationRun=" + applicationRun + ", annotations=" + annotations + '}';
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
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }
    
    
}
