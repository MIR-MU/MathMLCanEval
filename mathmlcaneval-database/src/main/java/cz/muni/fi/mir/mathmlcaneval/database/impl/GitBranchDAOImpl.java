/*
 * Copyright 2016 MIR@MU.
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

import cz.muni.fi.mir.mathmlcaneval.database.GitBranchDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.GitBranch;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository
public class GitBranchDAOImpl extends AbstractDAO<GitBranch, Long> implements GitBranchDAO
{


    @Override
    public GitBranch getBranchByName(String branchName)
    {
        try
        {
            return getEntityManager()
                    .createQuery("SELECT gb FROM gitbranch gb WHERE gb.name = :branchname",getClassType())
                    .setParameter("branchname",branchName)
                    .getSingleResult();
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }

    @Override
    public Class<GitBranch> getClassType()
    {
        return GitBranch.class;
    }
}
