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
package cz.muni.fi.mir.tools;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cz.muni.fi.mir.similarity.SimilarityFormConverter;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author emptak
 */
@Component(value = "xmlUtils")
public class XMLUtils
{

    private static final Logger logger = Logger.getLogger(SimilarityFormConverter.class);

    @Autowired
    @Qualifier(value = "documentBuilder")
    private DocumentBuilder documentBuilder; // wired bean used for creating documents

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

}
