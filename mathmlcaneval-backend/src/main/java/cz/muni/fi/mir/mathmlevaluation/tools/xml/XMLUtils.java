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
package cz.muni.fi.mir.mathmlevaluation.tools.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.dom.DOMSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author emptak
 */
@Component(value = "xmlUtils")
public class XMLUtils
{
    private static final Logger logger = Logger.getLogger(XMLUtils.class);
    private static final String ERROR_VALIDATION = "#error + error occured during validation";

    @Autowired
    @Qualifier(value = "documentBuilder")
    private DocumentBuilder documentBuilder; // wired bean used for creating documents
    @Autowired
    private XMLEvent xmlEvent;
    @Autowired
    private XMLInputFactory xmlInputFactory;
    @Autowired
    private XMLOutputFactory xmlOutputFactory;

    /**
     * Method parses given input String which is expected to be in form XML into
     * Document object. If any error occurs exception is suppressed and null is
     * returned. Method has to be synchronized due to <a
     * href="http://stackoverflow.com/q/12455602/1203690">this</a>
     *
     * @param input String representation of XML file
     * @return parsed XML String in form of Document
     */
    public synchronized Document parse(String input)
    {
        Document doc = null;
        try
        {
            doc = documentBuilder.parse(new InputSource(new StringReader(input)));
        }
        catch (SAXException | IOException ex)
        {
            logger.error(ex);
        }

        return doc;
    }
    
    public synchronized String isValid(String input)
    {
        logger.debug("Going to validate following xml:");
        logger.debug(input);
        Document doc = parse(input);
        
        XMLEventReader reader = null;
        
        try
        {
            reader = xmlInputFactory.createXMLEventReader(new DOMSource(doc));
        }
        catch(XMLStreamException ex)
        {
            logger.error(ex);
        }
        
        if(reader != null)
        {
            reader = new DTDReplacer(reader, xmlEvent);
        
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XMLEventWriter writer = null;
            
            try
            {
                writer = xmlOutputFactory.createXMLEventWriter(stream);
                writer.add(reader);
                writer.flush();
            }
            catch(XMLStreamException ex)
            {
                logger.error(ex);
            }
            
            if(writer != null)
            {
                String s = new String(stream.toByteArray());
                
                logger.debug("XML processed into following format:");
                logger.debug(s);

                //TODO
                // this should be autowired but does not work somehow....
                DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
                factory2.setValidating(true);
                factory2.setNamespaceAware(true);


                DocumentBuilder db2 = null;
                
                try
                {
                    db2 = factory2.newDocumentBuilder();
                }
                catch(ParserConfigurationException ex)
                {
                    logger.error(ex);
                }

                if(db2 != null)
                {
                    //TODO
                    //create factory & prototype bean
                    MathMLErrorHandler errorHandler = new ValidationErrorHandler();
                    db2.setErrorHandler(errorHandler);
                    Document doc2 = null;

                    try
                    {
                        doc2 = db2.parse(new InputSource(new StringReader(s)));
                    }
                    catch(SAXException | IOException ex)
                    {
                        logger.error(ex);
                    }

                    if(!errorHandler.getError().equals(StringUtils.EMPTY))
                    {
                        return StringUtils.substringBefore(errorHandler.getError(), ", it must match");
                    }

                    return errorHandler.getError();
                }                
            }
        }
        
        return ERROR_VALIDATION;        
    }
}
