/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.convertors;

import cz.muni.fi.mir.mathmlevaluation.db.service.RevisionService;
import cz.muni.fi.mir.mathmlevaluation.forms.RevisionForm;
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
public class StringToRevisionForm implements Converter<String, RevisionForm>
{
    @Autowired private RevisionService revisionService;
    @Autowired private Mapper mapper;


    @Override
    public RevisionForm convert(String source)
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
                return mapper.map(revisionService.getRevisionByID(Long.valueOf(source)), RevisionForm.class);
            }
        }
    }
}