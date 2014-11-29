/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlevaluation.db.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;


/**
 *
 * @author Empt
 */
@Entity(name = "canonicOutput")
public class CanonicOutput implements Serializable,Auditable
{
    private static final long serialVersionUID = 6956045766345845859L;

    @Id
    @Column(name = "id",nullable = false)
    @SequenceGenerator(name="canonicoutputid_seq", sequenceName="canonicoutputid_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "canonicoutputid_seq")
    private Long id;

    @Column(name = "outputForm",columnDefinition = "TEXT",length = 10000)
    private String outputForm;
    
    @Column(name="hashValue",length = 40)
    private String hashValue;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="parents_id")
    private List<Formula> parents;

    @Column(name = "runtime")
    private long runningTime;

    @ManyToOne
    private ApplicationRun applicationRun;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<Annotation> annotations;

    @Override
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

    public String getHashValue()
    {
        return hashValue;
    }

    public void setHashValue(String hashValue)
    {
        this.hashValue = hashValue;
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
        return "CanonicOutput{" + "hashValue=" + hashValue + '}';
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
