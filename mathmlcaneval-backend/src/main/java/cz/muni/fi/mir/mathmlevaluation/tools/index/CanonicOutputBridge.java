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
package cz.muni.fi.mir.mathmlevaluation.tools.index;

import java.io.StringReader;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.util.Version;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import cz.muni.fi.mir.mathmlevaluation.db.domain.Annotation;
import cz.muni.fi.mir.mathmlevaluation.db.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlevaluation.similarity.SimilarityFormConverterWrapper;
import cz.muni.fi.mir.mathmlevaluation.similarity.SimilarityForms;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CanonicOutputBridge implements FieldBridge
{
    private static final Logger logger = Logger.getLogger(CanonicOutputBridge.class); 

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions)
    {
        if(value instanceof Collection<?>)
        {
            Collection<CanonicOutput> canonicOutputs = (Collection<CanonicOutput>) value;
            
            for(CanonicOutput co : canonicOutputs)
            {
                convertCanonicOutput(co, document, luceneOptions);
            }
            
            document.add(newField("coRuns", String.valueOf(canonicOutputs.size()), luceneOptions, null));
        }
    }
    
    
    private void convertCanonicOutput(CanonicOutput canonicOutput,Document document,LuceneOptions luceneOptions)
    {
        SimilarityForms sf = SimilarityFormConverterWrapper.getConverter().process(canonicOutput);             
                
        document.add(newField("co.configuration.id", 
                canonicOutput.getApplicationRun().getConfiguration().getId().toString(), 
                luceneOptions, 
                new StandardAnalyzer(Version.LUCENE_36)
            )
        );
        
        document.add(newField("co.revision.id", 
                canonicOutput.getApplicationRun().getRevision().getId().toString(), 
                luceneOptions, 
                new StandardAnalyzer(Version.LUCENE_36)
            )
        );
        
        document.add(newField("co.applicationrun.id", 
                canonicOutput.getApplicationRun().getId().toString(), 
                luceneOptions, 
                new StandardAnalyzer(Version.LUCENE_36)
            )
        );
        
        if(canonicOutput.getAnnotations() != null && !canonicOutput.getAnnotations().isEmpty())
        {
            for(Annotation a : canonicOutput.getAnnotations())
            {
                document.add(newField("co.annotation", a.getAnnotationContent(), luceneOptions, new StandardAnalyzer(Version.LUCENE_36)));
            }
        }        
        
        // mathml is converted into Single String representation
        // which is stored in co.distanceForm
        document.add(newField("co.distanceForm",sf.getDistanceForm(),luceneOptions,null));
        
        PerFieldAnalyzerWrapper keywordAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer());
        
        for(String s : sf.getCountForm().keySet())
        {
            document.add(newField("co.element", s+"="+sf.getCountForm().get(s), luceneOptions, keywordAnalyzer)); 
        }
        
        logger.info("Canonic output ["+canonicOutput.getId()+"] indexed.");
    }
    
    private Field newField(String name, String value, LuceneOptions luceneOptions,Analyzer analyzer)
    {
        Field f =new Field(name,value,
                luceneOptions.getStore(),
                luceneOptions.getIndex(),
                luceneOptions.getTermVector()
        );
        
        f.setBoost(luceneOptions.getBoost());
        
        if(analyzer != null)
        {
            f.setTokenStream(analyzer.tokenStream(name, new StringReader(value)));
        }
        
        return f;
    }
}
