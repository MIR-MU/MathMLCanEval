/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.convertors;

import cz.muni.fi.mir.mathmlevaluation.db.service.ElementService;
import cz.muni.fi.mir.mathmlevaluation.forms.ElementForm;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author emptak
 */
public class StringToElementForm implements Converter<String, ElementForm>
{
    @Autowired private ElementService elementService;
    @Autowired private Mapper mapper;
    
    @Override
    public ElementForm convert(String source)
    {
        if(source != null && source.length() < 1)
        {
            return null;
        }
        
        return mapper.map(elementService.getElementByID(Long.valueOf(source)), ElementForm.class);
    }
    
}
