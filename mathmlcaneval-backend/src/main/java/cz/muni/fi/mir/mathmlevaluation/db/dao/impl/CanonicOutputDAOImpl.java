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

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cz.muni.fi.mir.mathmlevaluation.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Annotation;
import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Formula;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Pagination;
import cz.muni.fi.mir.mathmlevaluation.db.domain.SearchResponse;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository(value = "canonicOutputDAO")
public class CanonicOutputDAOImpl extends GenericDAOImpl<CanonicOutput, Long> implements CanonicOutputDAO
{

    private static final Logger logger = Logger.getLogger(CanonicOutputDAOImpl.class);

    public CanonicOutputDAOImpl()
    {
        super(CanonicOutput.class);
    }

    @Override
    public SearchResponse<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun, Pagination pagination)
    {
        SearchResponse<CanonicOutput> sr = new SearchResponse<>();
        if (pagination == null)
        {
            List<CanonicOutput> resultList = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :appRun", CanonicOutput.class)
                    .setParameter("appRun", applicationRun)
                    .getResultList();
            sr.setTotalResultSize(resultList.size());
            sr.setResults(resultList);
            
            return sr;
        }
        else
        {
            try
            {
                Query query = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :appRun", CanonicOutput.class)
                        .setParameter("appRun", applicationRun);
                sr.setTotalResultSize(query.getResultList().size());
                sr.setResults(query.setFirstResult(pagination.getPageSize() * (pagination.getPageNumber() - 1))
                        .setMaxResults(pagination.getPageSize())
                        .getResultList());
            }
            catch (NoResultException nre)
            {
                logger.debug(nre);
            }

            return sr;
        }
    }

    @Override
    public CanonicOutput getCanonicOutputByAnnotation(Annotation annotation)
    {
        CanonicOutput result = null;

        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE :annotationID MEMBER OF co.annotations", CanonicOutput.class)
                    .setParameter("annotationID", annotation.getId()).getSingleResult();
        }
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }
        return result;
    }

    @Override
    public CanonicOutput getCanonicOuputByHashValue(String hashValue)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfCanonicOutputs()
    {
        return entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co", Long.class)
                .getSingleResult().intValue();
    }

    @Override
    public List<CanonicOutput> getSubListOfOutputs(int start, int end)
    {
        return entityManager.createQuery("SELECT co FROM canonicOutput co", CanonicOutput.class)
                .setFirstResult(start).setMaxResults(end-start)
                .getResultList();
    }

    @Override
    public CanonicOutput nextInRun(CanonicOutput current) throws IllegalArgumentException
    {
        CanonicOutput result = null;
        
        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :appRun AND co.id > :coID ORDER BY co.id ASC", CanonicOutput.class)
                    .setParameter("coID",current.getId())
                    .setParameter("appRun", current.getApplicationRun())
                    .setMaxResults(1)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return result;
    }

    @Override
    public CanonicOutput previousInRun(CanonicOutput current) throws IllegalArgumentException
    {
        CanonicOutput result = null;
        
        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :appRun AND co.id < :coID ORDER BY co.id DESC", CanonicOutput.class)
                    .setParameter("coID",current.getId())
                    .setParameter("appRun", current.getApplicationRun())
                    .setMaxResults(1)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return result;
    }

    @Override
    public CanonicOutput firstInRun(CanonicOutput current)
    {
        CanonicOutput result = null;
        
        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :apprun ORDER BY co.id ASC", CanonicOutput.class)
                    .setParameter("apprun", current.getApplicationRun())
                    .setMaxResults(1)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        if(current.equals(result))
        {
            return null;
        }
        else
        {
            return result;
        }
    }

    @Override
    public CanonicOutput lastInRun(CanonicOutput current)
    {
        CanonicOutput result = null;
        
        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :apprun ORDER BY co.id DESC", CanonicOutput.class)
                    .setParameter("apprun", current.getApplicationRun())
                    .setMaxResults(1)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        if(current.equals(result))
        {
            return null;
        }
        else
        {
            return result;
        }
    }

    @Override
    public CanonicOutput lastOfFormula(Formula formula)
    {
        CanonicOutput result = null;

        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE :formulaID MEMBER of co.parents ORDER BY co.applicationRun.stopTime DESC", CanonicOutput.class)
                    .setParameter("formulaID", formula.getId())
                    .setMaxResults(1)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return result;
    }
}
