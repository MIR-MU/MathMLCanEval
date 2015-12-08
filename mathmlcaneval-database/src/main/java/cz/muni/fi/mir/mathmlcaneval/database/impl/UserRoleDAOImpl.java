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
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Robert Siska
 * @version 2.0
 * @since 1.0
 */
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole, Long> implements UserRoleDAO
{
    public UserRoleDAOImpl()
    {
        super(UserRole.class);
    }
    
    @Override
    public UserRole getByName(String roleName) throws IllegalArgumentException
    {
        UserRole ur = null;
        try
        {
            ur = entityManager.createQuery("SELECT ur FROM ", UserRole.class)
                    .setParameter("rolename", roleName)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            
        }
        
        return ur;
    }

    @Override
    public List<UserRole> getAllRoles()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
