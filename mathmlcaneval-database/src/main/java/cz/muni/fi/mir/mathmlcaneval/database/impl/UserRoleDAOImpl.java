/*
 * Copyright 2015 Dominik Szalai - emptulik at gmail.com.
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

import cz.muni.fi.mir.mathmlcaneval.database.UserRoleDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import javax.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Robert Siska
 * @version 2.0
 * @since 1.0
 */
@Repository
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole, Long> implements UserRoleDAO
{
    private static final Logger LOG = LogManager.getLogger(UserRoleDAOImpl.class);

    public UserRoleDAOImpl()
    {
        super(UserRole.class, "UserRole.getAll");
    }

    @Override
    public UserRole getByName(String roleName)
    {
        UserRole ur = null;
        try
        {
            ur = entityManager.createNamedQuery("UserRole.getByName", UserRole.class)
                    .setParameter("rolename", roleName)
                    .getSingleResult();
        }
        catch (NoResultException nre)
        {
            LOG.debug(nre.getMessage());
        }
        
        return ur;
    }
}
