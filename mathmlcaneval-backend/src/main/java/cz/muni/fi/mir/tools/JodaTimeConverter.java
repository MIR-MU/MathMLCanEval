/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emptak
 */
public class JodaTimeConverter extends DozerConverter<DateTime, DateTime>
{
    
    public JodaTimeConverter()
    {
        super(DateTime.class, DateTime.class);
    }

    @Override
    public DateTime convertTo(DateTime source, DateTime destination) {
        if(source == null)
        {
            return null;
        }
        else
        {
            return new DateTime(source);
        }
    }

    @Override
    public DateTime convertFrom(DateTime source, DateTime destination) {
        if(source == null)
        {
            return null;
        }
        else
        {
            return new DateTime(source);
        }
    }
    
}