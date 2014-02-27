/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @ManyToOne
    private SourceDocument sourceDocument;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "insertTime")
    private DateTime insertTime;                    // datum pridani
    @ManyToOne
    private Program program;                    // konverzni program
    @ManyToOne
    private User user;                          // kto ju vlozil
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<CanonicOutput> outputs;         // 
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Formula> similarFormulas;

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

    public Set<Formula> getSimilarFormulas()
    {
        return similarFormulas;
    }

    public void setSimilarFormulas(Set<Formula> similarFormulas)
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

    @Override
    public String toString()
    {
        return "Formula{" + "id=" + id + ", xml=" + xml + ", note=" + note + ", sourceDocument=" + sourceDocument + ", insertTime=" + insertTime + ", program=" + program + ", user=" + user + ", outputs=" + outputs + ", similarFormulas=" + similarFormulas + '}';
    }
}
