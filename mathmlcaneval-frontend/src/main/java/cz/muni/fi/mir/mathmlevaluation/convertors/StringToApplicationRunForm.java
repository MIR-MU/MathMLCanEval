/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.convertors;

import cz.muni.fi.mir.mathmlevaluation.db.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlevaluation.forms.ApplicationRunForm;
import cz.muni.fi.mir.mathmlevaluation.tools.Tools;
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
public class StringToApplicationRunForm implements Converter<String, ApplicationRunForm>
{
    @Autowired private ApplicationRunService applicationRunService;
    @Autowired private Mapper mapper;


    @Override
    public ApplicationRunForm convert(String source)
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
                return mapper.map(applicationRunService.getApplicationRunByID(Long.valueOf(source)),ApplicationRunForm.class);
            }
        }
    }
}