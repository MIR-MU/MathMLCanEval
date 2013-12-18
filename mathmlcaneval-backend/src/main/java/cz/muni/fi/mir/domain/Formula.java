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
import javax.persistence.OneToOne;
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
    @Column(name="xml")
    private String xml;                         // formulka v MathML
    @Column(name="note")
    private String note;                        // poznamka
    @ManyToOne
    private SourceDocument sourceDocument;
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Column(name="stop")
    private DateTime insertTime;                    // datum pridani
    @ManyToOne
    private Program program;                    // konverzni program
    @OneToOne
    private User user;                          // kto ju vlozil
    @OneToMany
    private Set<CanonicOutput> outputs;         // 
    @OneToMany
    private Set<Formula> fimilarFormulas;

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

    public Set<CanonicOutput> getOutputs()
    {
        return outputs;
    }

    public void setOutputs(Set<CanonicOutput> outputs)
    {
        this.outputs = outputs;
    }

    public Set<Formula> getFimilarFormulas()
    {
        return fimilarFormulas;
    }

    public void setFimilarFormulas(Set<Formula> fimilarFormulas)
    {
        this.fimilarFormulas = fimilarFormulas;
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
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Formula{" + "id=" + id + ", xml=" + xml + ", note=" + note + ", sourceDocument=" + sourceDocument + ", insertTime=" + insertTime + ", program=" + program + ", user=" + user + ", outputs=" + outputs + ", fimilarFormulas=" + fimilarFormulas + '}';
    }
    
    
}
