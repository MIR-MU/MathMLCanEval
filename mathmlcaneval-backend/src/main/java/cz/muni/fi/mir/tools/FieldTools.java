/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.LuceneOptions;

/**
 *
 * @author emptak
 */
public class FieldTools
{
    public static Field newField(String name, String value, LuceneOptions luceneOptions,Analyzer analyzer)
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
