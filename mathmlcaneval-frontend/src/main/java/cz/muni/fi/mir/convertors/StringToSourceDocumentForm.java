/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.convertors;

import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.forms.SourceDocumentForm;
import cz.muni.fi.mir.tools.Tools;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author siska
 */
public class StringToSourceDocumentForm implements Converter<String, SourceDocumentForm>
{
    @Autowired private SourceDocumentService sourceDocumentService;
    @Autowired private Mapper mapper;


    @Override
    public SourceDocumentForm convert(String source)
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
                return mapper.map(sourceDocumentService.getSourceDocumentByID(new Long(source)), SourceDocumentForm.class);
            }
        }
    }
}
