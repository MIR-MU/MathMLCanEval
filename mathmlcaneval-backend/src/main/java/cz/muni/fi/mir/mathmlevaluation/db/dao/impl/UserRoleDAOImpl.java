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
package cz.muni.fi.mir.mathmlevaluation.db.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cz.muni.fi.mir.mathmlevaluation.db.dao.UserRoleDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.UserRole;

/**
 *
 * @author Dominik Szalai
 * @author Robert Siska
 * @version 1.0
 * @since 1.0
 */
@Repository(value = "userRoleDAO")
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole, Long> implements UserRoleDAO
{    
    private static final Logger logger = Logger.getLogger(UserRoleDAOImpl.class);
    
    public UserRoleDAOImpl()
    {
        super(UserRole.class);
    }

    @Override
    public UserRole getUserRoleByName(String roleName)
    {
        UserRole ur = null;
        try
        {
            ur = entityManager.createQuery("SELECT ur FROM userRole ur WHERE ur.roleName = :roleName", UserRole.class)
                    .setParameter("roleName", roleName).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return ur;
    }

    @Override
    public List<UserRole> getAllUserRoles()
    {
        List<UserRole> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT ur FROM userRole ur", UserRole.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }    
}