/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

import cz.muni.fi.mir.similarity.ElementCountTokenizerFactory;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TokenizerDef;
import org.joda.time.DateTime;

/**
 *
 * @author Empt
 */
@Entity(name = "formula")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AnalyzerDef(name="countElementFormAnalyzer",
        tokenizer = @TokenizerDef(factory = ElementCountTokenizerFactory.class))
public class Formula implements Serializable
{

    private static final long serialVersionUID = 7807040500942149400L;

    @Id
    @Column(name = "formula_id",nullable = false)
    @SequenceGenerator(name="formulaid_seq", sequenceName="formulaid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "formulaid_seq")
    private Long id;                         
    
    @Column(name = "xml", columnDefinition = "text", length = 100000)
    private String xml;                         // formulka v MathML
    @Column(name = "note")
    private String note;                        // poznamka
    @Column(name = "hashValue", length = 40,nullable = true,unique = true)
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
    @ManyToMany(mappedBy="parents",cascade = CascadeType.REMOVE)
    @IndexedEmbedded
    private List<CanonicOutput> outputs;         // 
    @OneToMany
    private List<Formula> similarFormulas;    
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Annotation> annotations;    
    @ManyToMany
    private List<Element> elements;

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

    public List<Element> getElements()
    {
        return elements;
    }

    public void setElements(List<Element> elements)
    {
        this.elements = elements;
    }

    public String getHashValue()
    {
        return hashValue;
    }

    public void setHashValue(String hashValue)
    {
        this.hashValue = hashValue;
    }

    public List<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations)
    {
        this.annotations = annotations;
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
        return "Formula{" + "id=" + id + " hashValue=" + hashValue + '}';
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
