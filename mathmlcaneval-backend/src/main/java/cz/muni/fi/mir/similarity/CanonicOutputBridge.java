package cz.muni.fi.mir.similarity;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import java.io.StringReader;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.util.Version;
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
        
        document.add(newField("outputForm", sf.getDefaultForm(),luceneOptions,null));
        document.add(newField("distanceForm",sf.getDistanceForm(),luceneOptions,null));
        document.add(newField("countElementForm",sf.getCountForm(),luceneOptions,
                new ElementCountAnalyzer(Version.LUCENE_36)));
        document.add(newField("longestBranch",String.valueOf(sf.getLongestBranch()),luceneOptions,null));
        document.add(newField("annotation",sb.toString(),luceneOptions,null));
        
        logger.debug("outputForm added " + sf);
    }

    @Override
    public void setParameterValues(Map<String, String> parameters)
    {
        logger.debug(parameters);
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
