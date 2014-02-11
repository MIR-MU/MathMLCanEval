package cz.muni.fi.mir.tools;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai
 * 
 * @version 1.0
 * @since 1.0
 */
public class JodaTimeConverter extends DozerConverter<DateTime, DateTime>
{

    public JodaTimeConverter()
    {
        super(DateTime.class, DateTime.class);
    }

    @Override
    public DateTime convertTo(DateTime source, DateTime destination)
    {
        if (source == null)
        {
            return null;
        } 
        else
        {
            return new DateTime(source);
        }
    }

    @Override
    public DateTime convertFrom(DateTime source, DateTime destination)
    {
        if (source == null)
        {
            return null;
        } 
        else
        {
            return new DateTime(source);
        }
    }

}
