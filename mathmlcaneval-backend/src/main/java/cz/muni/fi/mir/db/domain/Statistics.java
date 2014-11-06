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
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author emptak
 */
@Entity(name = "statistics")
public class Statistics implements Serializable
{
    private static final long serialVersionUID = -4837257012984879245L;
    
    @Id
    @Column(name = "statistics_id",nullable = false)
    @SequenceGenerator(name="statisticsid_seq", sequenceName="statisticsid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "statisticsid_seq")
    private Long id;
    
    @Column(name = "totalFormulas")
    private Integer totalFormulas;
    @Column(name="totalCanonicOutputs")
    private Integer totalCanonicOutputs;
    
    /**
     * Date when statistic entity was created
     */
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "calculationDate")
    private DateTime calculationDate;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<StatisticsHolder> statisticsHolders;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DateTime getCalculationDate()
    {
        return calculationDate;
    }

    public void setCalculationDate(DateTime calculationDate)
    {
        this.calculationDate = calculationDate;
    }

    public List<StatisticsHolder> getStatisticsHolders()
    {
        return statisticsHolders;
    }

    public void setStatisticsHolders(List<StatisticsHolder> statisticsHolders)
    {
        this.statisticsHolders = statisticsHolders;
    }

    public Integer getTotalFormulas()
    {
        return totalFormulas;
    }

    public void setTotalFormulas(Integer totalFormulas)
    {
        this.totalFormulas = totalFormulas;
    }

    public Integer getTotalCanonicOutputs()
    {
        return totalCanonicOutputs;
    }

    public void setTotalCanonicOutputs(Integer totalCanonicOutputs)
    {
        this.totalCanonicOutputs = totalCanonicOutputs;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
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
        final Statistics other = (Statistics) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Statistics{id=" + id + ", totalFormulas=" + totalFormulas + ", totalCanonicOutputs=" + totalCanonicOutputs + ", calculationDate=" + calculationDate + ", statisticsHolders=" + statisticsHolders + '}';
    }
}
