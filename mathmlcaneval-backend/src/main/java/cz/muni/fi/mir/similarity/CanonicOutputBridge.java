package cz.muni.fi.mir.similarity;

import cz.muni.fi.mir.db.domain.CanonicOutput;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
        
        SimilarityForms sf = sfc.process((CanonicOutput) value);
        
        Field outputForm = new Field("outputForm", sf.getDefaultForm(),
                luceneOptions.getStore(),
                luceneOptions.getIndex(),
                luceneOptions.getTermVector()
        );
        outputForm.setBoost(luceneOptions.getBoost());
        
        Field distanceForm = new Field("distanceForm",sf.getDistanceForm(),
                luceneOptions.getStore(),
                luceneOptions.getIndex(),
                luceneOptions.getTermVector()
        );
        distanceForm.setBoost(luceneOptions.getBoost());
        
        Field countElementForm = new Field("countElementForm",sf.getCountForm(),
                luceneOptions.getStore(),
                luceneOptions.getIndex(),
                luceneOptions.getTermVector()
        );
        countElementForm.setBoost(luceneOptions.getBoost());
        
        Field longestBranch = new Field("longestBranch",String.valueOf(sf.getLongestBranch()),
                luceneOptions.getStore(),
                luceneOptions.getIndex(),
                luceneOptions.getTermVector()
        );
        longestBranch.setBoost(luceneOptions.getBoost());
        
        document.add(outputForm);
        document.add(distanceForm);
        document.add(countElementForm);
        document.add(longestBranch);
        logger.debug("outputForm added " + sf);
    }

    @Override
    public void setParameterValues(Map<String, String> parameters)
    {
        logger.debug(parameters);
    }
}
