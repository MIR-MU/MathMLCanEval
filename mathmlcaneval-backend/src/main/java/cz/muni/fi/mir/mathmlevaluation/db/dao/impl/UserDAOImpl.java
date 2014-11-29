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

import cz.muni.fi.mir.mathmlevaluation.db.dao.UserDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.User;
import cz.muni.fi.mir.mathmlevaluation.db.domain.UserRole;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Repository(value = "userDAO")
public class UserDAOImpl extends GenericDAOImpl<User,Long> implements UserDAO
{
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    public UserDAOImpl()
    {
        super(User.class);
    }
    
    @Override
    public User getUserByUsername(String username)
    {
        User u = null;
        try
        {
            u = entityManager.createQuery("SELECT u FROM users u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return u;
    }

    @Override
    public List<User> getAllUsers()
    {
        List<User> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT u FROM users u", User.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<User> getUsersByRole(UserRole userRole)
    {
        List<User> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT u FROM users u WHERE :userRoleId MEMBER OF u.userRoles", User.class)
                    .setParameter("userRoleId", userRole.getId()).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
