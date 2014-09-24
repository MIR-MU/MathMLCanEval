/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import java.util.Collection;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

/**
 *
 * @author emptak
 */
public class CollectionBridge implements FieldBridge
{
    private static final Logger logger = Logger.getLogger(CollectionBridge.class);

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions)
    {
        if(value instanceof Collection<?>)
        {
            Collection c = (Collection) value;
            
            document.add(FieldTools.newField("coRuns", String.valueOf(c.size()), luceneOptions, null));
        }
        
    }
}
