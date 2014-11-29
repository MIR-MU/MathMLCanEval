/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.tools.xml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.EventReaderDelegate;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class DTDReplacer extends EventReaderDelegate
{
    private final XMLEvent dtd;
    private boolean sendDtd = false;

    public DTDReplacer(XMLEventReader reader, XMLEvent dtd)
    {
        super(reader);
        if (dtd.getEventType() != XMLEvent.DTD)
        {
            throw new IllegalArgumentException("" + dtd);
        }

        this.dtd = dtd;
    }

    @Override
    public XMLEvent nextEvent() throws XMLStreamException
    {
        if (sendDtd)
        {
            sendDtd = false;
            return dtd;
        }
        XMLEvent evt = super.nextEvent();
        if (evt.getEventType() == XMLEvent.START_DOCUMENT)
        {
            sendDtd = true;
        }
        else if (evt.getEventType() == XMLEvent.DTD)
        {
            // discard old DTD
            return super.nextEvent();
        }
        return evt;
    }
}