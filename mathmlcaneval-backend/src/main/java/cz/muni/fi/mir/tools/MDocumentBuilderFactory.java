/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.EntityResolver;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MDocumentBuilderFactory
{
    private static final Logger logger = Logger.getLogger(MDocumentBuilderFactory.class);    
    private DocumentBuilderFactory documentBuilderFactory;
    private EntityResolver entityResolver;

    public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory)
    {
        this.documentBuilderFactory = documentBuilderFactory;
    }

    public void setEntityResolver(EntityResolver entityResolver)
    {
        this.entityResolver = entityResolver;
    }
    
    public DocumentBuilder newInstanceValidating()
    {
        DocumentBuilder documentBuilder = null;
        try
        {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        }
        catch(ParserConfigurationException pce)
        {
            logger.error(pce);
        }
        
        if(documentBuilder != null)
        {
            documentBuilder.setEntityResolver(entityResolver);
        }
        
        return documentBuilder;
    }
    
    public DocumentBuilder newInstance() throws ParserConfigurationException
    {
        return documentBuilderFactory.newDocumentBuilder();
    }
}
