package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.similarity.ElementCountAnalyzer;
import cz.muni.fi.mir.similarity.SimilarityFormConverter;
import cz.muni.fi.mir.similarity.SimilarityFormConverterWrapper;
import cz.muni.fi.mir.similarity.SimilarityForms;
import java.io.StringReader;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.ParameterizedBridge;

/**
 *
 * @author emptak
 */
//@Configurable(autowire = Autowire.BY_TYPE,preConstruction = true)
public class CanonicOutputBridge implements FieldBridge, ParameterizedBridge
{
    private static final Logger logger = Logger.getLogger(CanonicOutputBridge.class);  
    
    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions)
    {
        SimilarityFormConverter sfc = SimilarityFormConverterWrapper.getConverter();
        
        CanonicOutput co =(CanonicOutput) value;
        SimilarityForms sf = sfc.process(co);        
        
        StringBuilder sb = new StringBuilder();
        
        for(Annotation a :co.getAnnotations())
        {
            sb.append(a.getNote()).append(" ");
        }
        
        document.add(FieldTools.newField("outputForm", sf.getDefaultForm(),luceneOptions,null));
        document.add(FieldTools.newField("distanceForm",sf.getDistanceForm(),luceneOptions,null));
        document.add(FieldTools.newField("countElementForm",sf.getCountForm(),luceneOptions,
                new ElementCountAnalyzer(Version.LUCENE_36)));
        document.add(FieldTools.newField("longestBranch",String.valueOf(sf.getLongestBranch()),luceneOptions,null));
        document.add(FieldTools.newField("annotation",sb.toString(),luceneOptions,new StandardAnalyzer(Version.LUCENE_36)));
        
        logger.debug("outputForm added " + sf);
    }

    @Override
    public void setParameterValues(Map<String, String> parameters)
    {
        //logger.debug(parameters);
    }
}
