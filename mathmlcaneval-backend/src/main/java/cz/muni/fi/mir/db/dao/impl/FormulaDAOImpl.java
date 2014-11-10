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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.hibernate.Hibernate;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SearchResponse;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.similarity.SimilarityFormConverter;
import cz.muni.fi.mir.similarity.SimilarityForms;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Repository(value = "formulaDAO")
public class FormulaDAOImpl extends GenericDAOImpl<Formula, Long> implements FormulaDAO
{
    @Autowired
    private SimilarityFormConverter similarityFormConverter;
    private static final Logger logger = Logger.getLogger(FormulaDAOImpl.class);
    
    public FormulaDAOImpl()
    {
        super(Formula.class);
    }

    @Override
    public void deleteFormula(Formula formula)
    {
        Formula toDelete = entityManager.find(Formula.class, formula.getId());
        if (toDelete != null)
        {
            if (toDelete.getSimilarFormulas() != null && !toDelete.getSimilarFormulas().isEmpty())
            {
                toDelete.setSimilarFormulas(null);

                entityManager.merge(toDelete);
            }

            List<Formula> haveReference = entityManager
                    .createQuery("SELECT f FROM formula f where :formula MEMBER OF f.similarFormulas", Formula.class)
                    .setParameter("formula", formula)
                    .getResultList();

            if (!haveReference.isEmpty())
            {
                logger.info("References has been found");
                for (Formula referenced : haveReference)
                {
                    List<Formula> newSimilar = referenced.getSimilarFormulas();

                    for (Formula similar : newSimilar)
                    {
                        if (similar.equals(formula))
                        {
                            newSimilar.remove(similar);
                            break;
                        }
                    }

                    referenced.setSimilarFormulas(newSimilar);

                    entityManager.merge(referenced);
                }
            }
            logger.info("Deleting formula [" + formula.getId() + "]");
            entityManager.remove(toDelete);
        }
        else
        {
            logger.info("Trying to delete Formula with ID that has not been found. The ID is [" + formula.getId().toString() + "]");
        }
    }

    @Override
    public Formula getFormulaByID(Long id)
    {
        Formula f = entityManager.find(Formula.class, id);
        if (f != null)
        {
            Hibernate.initialize(f);
        }

        return f;
    }

    @Override
    public List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.sourceDocument = :sourceDocument", Formula.class)
                    .setParameter("sourceDocument", sourceDocument).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<Formula> getFormulasByProgram(Program program)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.program = :program", Formula.class)
                    .setParameter("program", program).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<Formula> getFormulasByUser(User user)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.user = :user", Formula.class)
                    .setParameter("user", user).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public Formula getFormulaByAnnotation(Annotation annotation)
    {
        Formula result = null;

        try
        {
            result = entityManager.createQuery("SELECT f FROM formula f WHERE :annotationID MEMBER OF f.annotations", Formula.class)
                    .setParameter("annotationID", annotation.getId()).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        return result;
    }

    @Override
    public List<Formula> getAllFormulas(Pagination pagination)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f ORDER BY f.id DESC", Formula.class)
                    .setFirstResult(pagination.getPageSize() * (pagination.getPageNumber() - 1))
                    .setMaxResults(pagination.getPageSize())
                    .getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public int getNumberOfRecords()
    {
        int result = 0;
        try
        {
            result = entityManager.createQuery("SELECT COUNT(f) FROM formula f", Long.class).getSingleResult().intValue();
        }
        catch (NoResultException nre)
        {
            logger.error(nre);
        }

        return result;
    }

    @Override
    public Long exists(String hash)
    {
        Formula f = getFormulaByHash(hash);

        return f == null ? null : f.getId();
    }

    @Override
    public Formula getFormulaByHash(String hash)
    {
        Formula f = null;
        try
        {
            f = entityManager.createQuery("SELECT f FROM formula f WHERE f.hashValue = :hashValue", Formula.class).setParameter("hashValue", hash).getSingleResult();
        }
        catch (NoResultException nre)
        {
            logger.debug("No formula found with hash ["+hash+"]");
        }

        return f;
    }

    @Override
    public void reindex()
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        try
        {
            logger.info("Reindexing has started");
            fullTextEntityManager.createIndexer(Formula.class).startAndWait();
        }
        catch (InterruptedException ex)
        {
            logger.fatal(ex);
        }
    }

    @Override
    public SearchResponse<Formula> findSimilar(Formula formula, Map<String, String> properties, boolean override, boolean directWrite, Pagination pagination)
    {        
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        org.hibernate.search.jpa.FullTextQuery ftq = null;
        
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Formula.class)
                .overridesForField("co.element", "keywordAnalyzer")
                .get();
        
        BooleanJunction<BooleanJunction> junction = qb.bool();
        
        SimilarityForms sf = similarityFormConverter.process(formula.getOutputs().get(formula.getOutputs().size()-1));
        
        if(Boolean.valueOf(properties.get(FormulaService.USE_DISTANCE)))
        {
            junction.must(qb.keyword()
                    .fuzzy()
                    .withThreshold(Float.valueOf(properties.get(FormulaService.VALUE_DISTANCEMETHOD)))
                    .withPrefixLength(1)
                    .onField("co.distanceForm")
                    .ignoreFieldBridge()
                    .matching(sf.getDistanceForm())
                    .createQuery());
        }
        
        if(Boolean.valueOf(properties.get(FormulaService.USE_COUNT)))
        {
            if("must".equalsIgnoreCase(properties.get(FormulaService.CONDITION_COUNT)))
            {
                BooleanJunction<BooleanJunction> junctionElements = qb.bool();
                if("must".equalsIgnoreCase(properties.get(FormulaService.VALUE_COUNTELEMENTMETHOD)))
                {
                    for(String stringElement : sf.getCountForm().keySet())
                    {
                        junctionElements.must(qb.keyword()
                                .onField("co.element")
                                .ignoreFieldBridge()
                                .matching(stringElement+"="+sf.getCountForm()
                                        .get(stringElement))
                                .createQuery());
                    }
                }
                else
                {
                    for(String stringElement : sf.getCountForm().keySet())
                    {
                        junctionElements.should(qb.keyword()
                                .onField("co.element")
                                .ignoreFieldBridge()
                                .matching(stringElement+"="+sf.getCountForm()
                                        .get(stringElement))
                                .createQuery());
                    }
                }
                
                junction.must(junctionElements.createQuery());
            }
            else if("should".equalsIgnoreCase(properties.get(FormulaService.CONDITION_COUNT)))
            {
                BooleanJunction<BooleanJunction> junctionElements = qb.bool();
                if("must".equalsIgnoreCase(properties.get(FormulaService.VALUE_COUNTELEMENTMETHOD)))
                {
                    for(String stringElement : sf.getCountForm().keySet())
                    {
                        junctionElements.must(qb.keyword()
                                .onField("co.element")
                                .ignoreFieldBridge()
                                .matching(stringElement+"="+sf.getCountForm()
                                        .get(stringElement))
                                .createQuery());
                    }
                }
                else
                {
                    for(String stringElement : sf.getCountForm().keySet())
                    {
                        junctionElements.should(qb.keyword()
                                .onField("co.element")
                                .ignoreFieldBridge()
                                .matching(stringElement+"="+sf.getCountForm()
                                        .get(stringElement))
                                .createQuery());
                    }
                }
                
                junction.should(junctionElements.createQuery());
            }
            else
            {
                throw new IllegalArgumentException("condi");
            }
        }
        
        Query query = junction.createQuery();
        logger.info(query);
        
        ftq = fullTextEntityManager.createFullTextQuery(query, Formula.class);
        
        
        
        SearchResponse<Formula> fsr = new SearchResponse<>();
        fsr.setTotalResultSize(ftq.getResultSize());
        List<Formula> resultList = ftq.setFirstResult(pagination.getPageSize() * (pagination.getPageNumber() - 1))
                .setMaxResults(pagination.getPageSize())
                .getResultList();
        
        // we would like to write results immediately
        if (directWrite)
        {   // override old results ?
            if (override)
            {
                formula.setSimilarFormulas(new ArrayList<>(resultList));
            }
            else
            {   // check if null and append to earlier
                List<Formula> similars = new ArrayList<>();
                if (formula.getSimilarFormulas() != null)
                {
                    similars.addAll(formula.getSimilarFormulas());
                    similars.addAll(resultList);

                    formula.setSimilarFormulas(similars);
                }
                else
                {
                    similars.addAll(resultList);

                    formula.setSimilarFormulas(similars);
                }
            }
            // update
            super.update(formula);
        }
        
        fsr.setResults(resultList);
        
        return fsr;
    }

    @Override
    public SearchResponse<Formula> findFormulas(FormulaSearchRequest formulaSearchRequest, Pagination pagination)
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        org.hibernate.search.jpa.FullTextQuery ftq = null;  // actual query hitting database
        boolean isEmpty = true;

        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Formula.class)
                .overridesForField("co.annotation", "standardAnalyzer")
                .overridesForField("co.element", "keywordAnalyzer")
                .get();

        //logger.info("$"+qb.keyword().onField("annotation").ignoreFieldBridge().matching(formulaSearchRequest.getAnnotationContent()).createQuery().toString());
        BooleanJunction<BooleanJunction> junction = qb.bool();

        if (formulaSearchRequest.getSourceDocument() != null && formulaSearchRequest.getSourceDocument().getId() != null)
        {
            junction.must(qb.keyword()
                    .onField("sourceDocument.id")
                    .matching(formulaSearchRequest.getSourceDocument().getId())
                    .createQuery()
            );
            
            isEmpty = false;
        }
        
//        if(formulaSearchRequest.getConfiguration() != null && formulaSearchRequest.getConfiguration().getId() != null)
//        {
//            junction.must(qb.keyword()
//                    .onField("configuration.id")
//                    .matching(formulaSearchRequest.getConfiguration().getId())
//                    .createQuery()
//            );
//            
//            isEmpty = false;
//        }

        if (formulaSearchRequest.getProgram() != null && formulaSearchRequest.getProgram().getId() != null)
        {
            junction.must(qb.keyword()
                    .onField("program.id")
                    .matching(formulaSearchRequest.getProgram().getId())
                    .createQuery()
            );
            
            isEmpty = false;
        }

        if (formulaSearchRequest.getElements() != null && !formulaSearchRequest.getElements().isEmpty())
        {
            BooleanJunction<BooleanJunction> junctionElements = qb.bool();
            for(Element e : formulaSearchRequest.getElements().keySet())
            {
                junctionElements.must(qb.keyword()
                        .onField("co.element")
                        .ignoreFieldBridge()
                        .matching(e.getElementName()+"="+formulaSearchRequest.getElements().get(e))
                        .createQuery()
                );        
                
                isEmpty = false;
            }
            
            junction.must(junctionElements.createQuery());
        }

        if (formulaSearchRequest.getAnnotationContent() != null && !StringUtils.isEmpty(formulaSearchRequest.getAnnotationContent()))
        {
            junction.must(qb.keyword()
                    .onField("co.annotation")
                    .ignoreFieldBridge()
                    .matching(formulaSearchRequest.getAnnotationContent())
                    .createQuery()
            );
            
            isEmpty = false;
        }

        if (formulaSearchRequest.getFormulaContent() != null && !StringUtils.isEmpty(formulaSearchRequest.getFormulaContent()))
        {
            junction.must(qb.keyword()
                    .wildcard()
                    .onField("co.distanceForm")
                    .ignoreFieldBridge()
                    .matching(formulaSearchRequest.getFormulaContent()+"*")
                    .createQuery()
            );
            
            isEmpty = false;
        }
        
        if(formulaSearchRequest.getCoRuns() != null)
        {
            junction.must(qb.keyword()
                    .onField("coRuns")
                    .ignoreFieldBridge()
                    .matching(formulaSearchRequest.getCoRuns())
                    .createQuery()
            );
            
            isEmpty = false;
        }
        
        SearchResponse<Formula> fsr = new SearchResponse<Formula>();
        
        if(!isEmpty)
        {
            Query query = junction.createQuery();
            logger.info(query);

            ftq = fullTextEntityManager.createFullTextQuery(query, Formula.class);
            fsr.setTotalResultSize(ftq.getResultSize());
            
            ftq.setFirstResult(pagination.getPageSize() * (pagination.getPageNumber() - 1));
            ftq.setMaxResults(pagination.getPageSize());
            fsr.setResults(ftq.getResultList());
        }
        else
        {
            fsr.setTotalResultSize(getNumberOfRecords());
            fsr.setResults(getAllFormulas(pagination));
        }
        
        return fsr;        
    }

    @Override
    public Formula getFormulaByCanonicOutput(CanonicOutput canonicOutput)
    {
        Formula f = null;
        try
        {
            f = entityManager.createQuery("SELECT f FROM formula f WHERE :co MEMBER OF f.outputs", Formula.class)
                    .setParameter("co", canonicOutput).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.error(nre);
        }
        
        return f;
    }

    @Override
    public void index(Formula f)
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.index(f);
    }

    @Override
    public List<Formula> getFormulasByCanonicOutputHash(String hash)
    {
        return entityManager.createQuery("SELECT DISTINCT f FROM formula f LEFT JOIN f.outputs o WHERE o.hashValue = :hashValue", Formula.class)
                .setParameter("hashValue", hash).getResultList();
    }
}