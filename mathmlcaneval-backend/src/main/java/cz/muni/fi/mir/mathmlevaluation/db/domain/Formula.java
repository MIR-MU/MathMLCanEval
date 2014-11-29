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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;

import org.apache.solr.analysis.KeywordTokenizerFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.joda.time.DateTime;

import cz.muni.fi.mir.mathmlevaluation.tools.index.CanonicOutputBridge;

/**
 *
 * @author Empt
 */
@Entity(name = "formula")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AnalyzerDefs(
{
    @AnalyzerDef(name = "standardAnalyzer",
            tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
            filters =
            {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class)
            }),
    @AnalyzerDef(name = "keywordAnalyzer",
            tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class))
}
)
public class Formula implements Serializable, Auditable
{

    private static final long serialVersionUID = 7807040500942149400L;

    @Id
    @Column(name = "formula_id", nullable = false)
    @SequenceGenerator(name = "formulaid_seq", sequenceName = "formulaid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "formulaid_seq")
    private Long id;

    @Column(name = "xml", columnDefinition = "text", length = 100000)
    private String xml;                         // formulka v MathML
    @Column(name = "note")
    private String note;                        // poznamka
    @Column(name = "hashValue", length = 40, nullable = true, unique = true)
    private String hashValue;                        //used for computation whether formula is already stored
    @ManyToOne
    @IndexedEmbedded
    private SourceDocument sourceDocument;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "insertTime")
    private DateTime insertTime;                    // datum pridani
    @ManyToOne
    @IndexedEmbedded
    private Program program;                    // konverzni program
    @ManyToOne
    private User user;                          // kto ju vlozil
    @ManyToMany(mappedBy = "parents", cascade =
    {
        CascadeType.REMOVE, CascadeType.MERGE
    })
    @Field(bridge = @FieldBridge(impl = CanonicOutputBridge.class), store = Store.YES, analyze = Analyze.YES)
    private List<CanonicOutput> outputs;         // 
    @ManyToMany
    private List<Formula> similarFormulas;
    @OneToMany(cascade =
    {
        CascadeType.REMOVE, CascadeType.MERGE
    })
    //@IndexedEmbedded
    private List<Annotation> annotations;
    @ManyToMany
    private List<Element> elements;

    @Override
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
