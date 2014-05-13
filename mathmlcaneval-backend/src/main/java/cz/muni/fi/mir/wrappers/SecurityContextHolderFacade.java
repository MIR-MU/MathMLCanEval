/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.wrappers;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai <a href="mailto:emptulik&#64;gmail.com">emptulik&#64;gmail.com</a>
 */
@Component(value = "securityContext")
public class SecurityContextHolderFacade implements SecurityContextFacade
{
    private static final String NOT_FOUND = "#null";
    @Autowired
    private UserService userService;
    
    @Override
    public SecurityContext getContext()
    {
        return SecurityContextHolder.getContext();
    }

    @Override
    public void setContext(SecurityContext securityContext)
    {
        SecurityContextHolder.setContext(securityContext);
    }

    @Override
    public String getLoggedUser()
    {
        if (getContext().getAuthentication() != null && !Tools.getInstance().stringIsEmpty(getContext().getAuthentication().getName()))
        {
            return getContext().getAuthentication().getName();
        } 
        else
        {
            return NOT_FOUND;
        }
    }

    @Override
    public User getLoggedEntityUser()
    {
        String current = getLoggedUser();
        if(!current.equals(NOT_FOUND))
        {
            return userService.getUserByUsername(current);
        }
        else
        {
            return null;
        }
    }
}
