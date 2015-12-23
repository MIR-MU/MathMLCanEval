/*
 * Copyright 2015 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.database.impl;

import cz.muni.fi.mir.mathmlcaneval.database.UserDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.User;
import javax.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User,Long> implements UserDAO
{
    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);
    public UserDAOImpl()
    {
        super(User.class,"User.getAll");
    }

    @Override
    public User getByUsername(String username)
    {
        User result = null;
        try
        {
            result = entityManager
                    .createNamedQuery("User.getByUsername", type)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            LOG.debug(nre.getMessage());
        }
        
        return result;
    }    
}
