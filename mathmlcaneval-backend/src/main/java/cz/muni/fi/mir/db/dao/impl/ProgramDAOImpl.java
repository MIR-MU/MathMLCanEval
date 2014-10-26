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
package cz.muni.fi.mir.db.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cz.muni.fi.mir.db.dao.ProgramDAO;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.tools.Tools;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository(value = "programDAO")
public class ProgramDAOImpl extends GenericDAOImpl<Program, Long> implements ProgramDAO
{    
    private static final Logger logger = Logger.getLogger(ProgramDAOImpl.class);

    public ProgramDAOImpl()
    {
        super(Program.class);
    }

    @Override
    public List<Program> getProgramByNameAndVersion(String name, String version)
    {
        List<Program> resultList = Collections.emptyList();
        if(Tools.getInstance().stringIsEmpty(name) && Tools.getInstance().stringIsEmpty(version))
        {
            return getAllPrograms();
        }
        
        try
        {           
            resultList = entityManager.createQuery("SELECT p FROM program p WHERE p.name = :name AND p.version = :version", Program.class)
                    .setParameter("name", name).setParameter("version", version).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Program> getAllPrograms()
    {
        List<Program> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT p FROM program p", Program.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
