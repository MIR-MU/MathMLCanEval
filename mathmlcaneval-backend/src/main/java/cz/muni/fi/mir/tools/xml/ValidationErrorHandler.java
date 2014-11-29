/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools.xml;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ValidationErrorHandler implements MathMLErrorHandler
{
    private static final Logger logger = Logger.getLogger(ValidationErrorHandler.class);
    private String error = StringUtils.EMPTY;

    @Override
    public void warning(SAXParseException exception) throws SAXException
    {
        error = exception.getMessage();
        logger.warn(exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException
    {
        error = exception.getMessage();
        logger.error(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException
    {
        error = exception.getMessage();
        logger.fatal(exception);
    }

    @Override
    public String getError()
    {
        return this.error;
    }
    
}
