/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
    
    
    /**
     * Total count of formulas in system.
     */
    @Column
    private Integer totalFormulas;
    
    /**
     * Total count of formulas in system that have at least 1 canonic output.
     */
    @Column(name = "totalFormulaswco")
    private Integer totalFormulasWithCanonicOutput;
    
    /**
     * Total count of canonic outputs.
     */
    @Column
    private Integer totalCanonicalized;
    
    
    /**
     * Total count of IS_VALID tags see appcontext for specific value.
     */
    @Column
    private Integer totalValid;
    
    /**
     * Total count of IS_INVALID tags see appcontext for specific value.
     */
    @Column
    private Integer totalInvalid;
    
    /**
     * Total count of UNCERTAIN tags see appcontext for specific value.
     */
    @Column
    private Integer totalUncertain;
    
    /**
     * Total count of REMOVE_RESULT tags see appcontext for specific value.
     */
    @Column
    private Integer totalRemove;
    
    /**
     * Total count of Formulas marked as #formulaRemove, or shortcut #fRemove
     */
    @Column 
    private Integer totalFormulaRemove;
    
    /**
     * Total count Formulas marked as #formulaMeaningles, or shortcut #fMeaningless
     */
    @Column
    private Integer totalFormulaMeaningless;
    
    /**
     * Date when statistic entity was created
     */
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "calculationDate")
    private DateTime calculationDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getTotalFormulas()
    {
        return totalFormulas;
    }

    public void setTotalFormulas(Integer totalFormulas)
    {
        this.totalFormulas = totalFormulas;
    }

    public Integer getTotalCanonicalized()
    {
        return totalCanonicalized;
    }

    public void setTotalCanonicalized(Integer totalCanonicalized)
    {
        this.totalCanonicalized = totalCanonicalized;
    }

    public Integer getTotalValid()
    {
        return totalValid;
    }

    public void setTotalValid(Integer totalValid)
    {
        this.totalValid = totalValid;
    }

    public Integer getTotalInvalid()
    {
        return totalInvalid;
    }

    public void setTotalInvalid(Integer totalInvalid)
    {
        this.totalInvalid = totalInvalid;
    }

    public Integer getTotalUncertain()
    {
        return totalUncertain;
    }

    public void setTotalUncertain(Integer totalUncertain)
    {
        this.totalUncertain = totalUncertain;
    }

    public Integer getTotalRemove()
    {
        return totalRemove;
    }

    public void setTotalRemove(Integer totalRemove)
    {
        this.totalRemove = totalRemove;
    }

    public DateTime getCalculationDate()
    {
        return calculationDate;
    }

    public void setCalculationDate(DateTime calculationDate)
    {
        this.calculationDate = calculationDate;
    }

    public Integer getTotalFormulasWithCanonicOutput()
    {
        return totalFormulasWithCanonicOutput;
    }

    public void setTotalFormulasWithCanonicOutput(Integer totalFormulasWithCanonicOutput)
    {
        this.totalFormulasWithCanonicOutput = totalFormulasWithCanonicOutput;
    }

    public Integer getTotalFormulaRemove()
    {
        return totalFormulaRemove;
    }

    public void setTotalFormulaRemove(Integer totalFormulaRemove)
    {
        this.totalFormulaRemove = totalFormulaRemove;
    }

    public Integer getTotalFormulaMeaningless()
    {
        return totalFormulaMeaningless;
    }

    public void setTotalFormulaMeaningless(Integer totalFormulaMeaningless)
    {
        this.totalFormulaMeaningless = totalFormulaMeaningless;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        return "Statistics{" + "id=" + id + ", totalFormulas=" + totalFormulas + ", totalFormulasWithCanonicOutput=" + totalFormulasWithCanonicOutput + ", totalCanonicalized=" + totalCanonicalized + ", totalValid=" + totalValid + ", totalInvalid=" + totalInvalid + ", totalUncertain=" + totalUncertain + ", totalRemove=" + totalRemove + ", totalFormulaRemove=" + totalFormulaRemove + ", totalFormulaMeaningless=" + totalFormulaMeaningless + ", calculationDate=" + calculationDate + '}';
    }
}
