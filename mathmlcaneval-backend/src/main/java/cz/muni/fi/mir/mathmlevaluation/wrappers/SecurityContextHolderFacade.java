/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlevaluation.wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cz.muni.fi.mir.mathmlevaluation.db.domain.User;
import cz.muni.fi.mir.mathmlevaluation.db.service.UserService;
import cz.muni.fi.mir.mathmlevaluation.tools.Tools;

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
