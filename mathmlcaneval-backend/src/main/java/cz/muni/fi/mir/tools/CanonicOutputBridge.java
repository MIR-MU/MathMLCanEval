/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.similarity.SimilarityFormConverterWrapper;
import cz.muni.fi.mir.similarity.SimilarityForms;
import java.io.StringReader;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.util.Version;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

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
                
        if(canonicOutput.getAnnotations() != null && !canonicOutput.getAnnotations().isEmpty())
        {
            for(Annotation a : canonicOutput.getAnnotations())
            {
                document.add(newField("co.annotation", a.getNote(), luceneOptions, new StandardAnalyzer(Version.LUCENE_36)));
            }
        }        
        
        // mathml is converted into Single String representation
        // which is stored in co.distanceForm
        document.add(newField("co.distanceForm",sf.getDistanceForm(),luceneOptions,null));
        
        
        //does not work ejkejej TODO ~> so
        for(String s : sf.getCountForm().keySet())
        {
            //stores element in co.element field 
            //in form ElementName=count
            //eg mfrac=3
            document.add(newField("co.element", s+"="+sf.getCountForm().get(s), luceneOptions, null)); 
        }
        
//        document.add(newField("co.element",sf.getCountForm().toString(),luceneOptions,
//                new ElementCountAnalyzer(Version.LUCENE_36)));
        
        document.add(newField("co.longestBranch",String.valueOf(sf.getLongestBranch()),luceneOptions,null));
        
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
