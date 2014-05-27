/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Empt
 */
@Entity(name = "formula")
public class Formula implements Serializable
{

    private static final long serialVersionUID = 7807040500942149400L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // db id

    //@Type(type="cz.muni.fi.mir.domain.SQLXMLType")
    @Column(name = "xml", columnDefinition = "text", length = 100000)
    private String xml;                         // formulka v MathML
    @Column(name = "note")
    private String note;                        // poznamka
    @Column(name = "hashValue", length = 40,nullable = true)
    private String hashValue;                        //used for computation whether formula is already stored
    @ManyToOne
    private SourceDocument sourceDocument;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "insertTime")
    private DateTime insertTime;                    // datum pridani
    @ManyToOne
    private Program program;                    // konverzni program
    @ManyToOne
    private User user;                          // kto ju vlozil
    @ManyToMany(mappedBy="parents")
    private List<CanonicOutput> outputs;         // 
    @OneToMany
    private List<Formula> similarFormulas;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getXml()
    {
        return xml;
    }

    public void setXml(String xml)
    {
        this.xml = xml;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public SourceDocument getSourceDocument()
    {
        return sourceDocument;
    }

    public void setSourceDocument(SourceDocument sourceDocument)
    {
        this.sourceDocument = sourceDocument;
    }

    public DateTime getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(DateTime insertTime)
    {
        this.insertTime = insertTime;
    }

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<CanonicOutput> getOutputs()
    {
        return outputs;
    }

    public void setOutputs(List<CanonicOutput> outputs)
    {
        this.outputs = outputs;
    }

    public List<Formula> getSimilarFormulas()
    {
        return similarFormulas;
    }

    public void setSimilarFormulas(List<Formula> similarFormulas)
    {
        this.similarFormulas = similarFormulas;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
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
        final Formula other = (Formula) obj;
        return Objects.equals(this.id, other.id);
    }

    public String getHashValue()
    {
        return hashValue;
    }

    public void setHash(String hashValue)
    {
        this.hashValue = hashValue;
    }
    
    

    @Override
    public String toString()
    {
        return "Formula{" + "id=" + id + ", xml=" + xml + ", note=" + note + ", sourceDocument=" + sourceDocument + ", insertTime=" + insertTime + ", program=" + program + ", user=" + user + ", outputs=" + outputs + ", similarFormulas=" + similarFormulas + '}';
    }

    @PreRemove
    private void removeFormulaFromOutputs()
    {
        for (CanonicOutput co : outputs)
        {
            co.getParents().remove(this);
        }
    }
}
