/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.convertors;

import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.forms.ConfigurationForm;
import cz.muni.fi.mir.tools.Tools;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Class responsible for converting values inside HTML &lt;select /&gt; tag. Since spring somehow
 * is not able to determine type of object stored in &lt;select /&gt; tag we have to help
 * it by registering this Converter.
 *
 * @author siska
 */
public class StringToConfigurationForm implements Converter<String, ConfigurationForm>
{
    @Autowired private ConfigurationService configurationService;
    @Autowired private Mapper mapper;


    @Override
    public ConfigurationForm convert(String source)
    {
        if(Tools.getInstance().stringIsEmpty(source))
        {
            return null;
        }
        else
        {
            if(source.equals("-1"))
            {
                return null;
            }
            else
            {
                return mapper.map(configurationService.getConfigurationByID(new Long(source)), ConfigurationForm.class);
            }
        }
    }
}
