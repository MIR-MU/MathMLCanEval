/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.convertors;

import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.forms.UserRoleForm;
import cz.muni.fi.mir.tools.Tools;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Class responsible for converting values inside HTML &lt;select /&gt; tag. Since spring somehow
 * is not able to determine type of object stored in &lt;select /&gt; tag we have to help
 * it by registering this Converter.
 * 
 * @author Dominik Szalai
 * @since 1.0
 * @version 1.0
 */
public class StringToUserRole implements Converter<String, UserRoleForm>
{
    @Autowired private UserRoleService userRoleService;
    @Autowired private Mapper mapper;
    
    @Override
    public UserRoleForm convert(String source)
    {
        if(Tools.getInstance().stringIsEmpty(source))
        {
            return null;
        }
        else
        {
            return mapper.map(userRoleService.getUserRoleByID(new Long(source)), UserRoleForm.class);
        }
    }    
}
