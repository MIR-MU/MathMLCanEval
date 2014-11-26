/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MEntityResolver implements EntityResolver
{
    private Resource resource;

    public void setResource(Resource resource)
    {
        this.resource = resource;
    }

    /**
     * http://stackoverflow.com/a/16137820/1203690
     * 
     * <b>Note that it can work only if the XML document has a DTD declaration.</b>
     * 
     * @param publicId
     * @param systemId
     * @return
     * @throws SAXException
     * @throws IOException 
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
    {
        return new InputSource(resource.getInputStream());
    }
    
}
