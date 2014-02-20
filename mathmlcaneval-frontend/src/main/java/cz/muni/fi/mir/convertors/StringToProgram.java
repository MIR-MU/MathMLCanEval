/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.convertors;

import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.forms.ProgramForm;
import cz.muni.fi.mir.tools.Tools;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author siska
 */
public class StringToProgram implements Converter<String, ProgramForm>
{
    @Autowired private ProgramService programService;
    @Autowired private Mapper mapper;


    @Override
    public ProgramForm convert(String source)
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
                return mapper.map(programService.getProgramByID(new Long(source)), ProgramForm.class);
            }
        }
    }
}
