/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.similarity.SimilarityFormConverter;
import cz.muni.fi.mir.similarity.SimilarityForms;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.lucene.search.Explanation;
import org.hibernate.Hibernate;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "formulaDAO")
public class FormulaDAOImpl implements FormulaDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SimilarityFormConverter similarityFormConverter;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FormulaDAOImpl.class);
    
    @Override
    public void createFormula(Formula formula)
    {
        entityManager.persist(formula);
    }

    @Override
    public void updateFormula(Formula formula)
    {
        entityManager.merge(formula);
    }

    @Override
    public void deleteFormula(Formula formula)
    {
        Formula m = entityManager.find(Formula.class, formula.getId());
        if(m != null)
        {
            entityManager.remove(m);
        }
        else
        {
            logger.info("Trying to delete Formula with ID that has not been found. The ID is ["+formula.getId().toString()+"]");
        }
    }

    @Override
    public Formula getFormulaByID(Long id)
    {
        Formula f = entityManager.find(Formula.class, id);
        if(f != null)
        {
            Hibernate.initialize(f.getAnnotations());
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
        catch(NoResultException nre)
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
        catch(NoResultException nre)
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
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<Formula> getAllFormulas()
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f ORDER BY f.id DESC", Formula.class).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }
 
    @Override
    public List<Formula> getAllFormulas(int skip, int number)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f ORDER BY f.id DESC", Formula.class)
                    .setFirstResult(skip)
                    .setMaxResults(number)
                    .getResultList();
        }
        catch(NoResultException nre)
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
        catch(NoResultException nre)
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
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return f;
    }

    @Override
    public List<Formula> getAllForHashing()
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.hashValue IS NULL OR f.hashValue <> ''", Formula.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Formula> getFormulasByElements(Collection<Element> collection,int start, int end)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f IN ("
                    + "SELECT ff from formula ff "
                    + "INNER JOIN ff.elements ffe "
                    + "WHERE ffe IN (:elements) "
                    + "GROUP BY ff "
                    + "HAVING COUNT(DISTINCT ff) = (:elementsSize))", Formula.class)
                    .setParameter("elements", collection).setParameter("elementsSize", collection.size())
                    .setFirstResult(start).setMaxResults(end)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Formula> getAllFormulas(boolean force)
    {
        List<Formula> resultList = Collections.emptyList();
        if(force)
        {
            resultList = getAllFormulas();
        }
        else
        {
            try
            {
                resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.elements IS EMPTY", Formula.class)
                        .getResultList();
            }
            catch(NoResultException nre)
            {
                logger.debug(nre);
            }
        }
        
        return resultList;        
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
    public List<Formula> findSimilar(Formula formula,Map<String,String> properties)
    {
        Set<Formula> resultSet = new HashSet<>();
        List<SimilarityForms> sFormsList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(CanonicOutput co : formula.getOutputs())
        {
            SimilarityForms sf = similarityFormConverter.process(co);
            sb.append(sf.getDistanceForm()).append(" ");
            sFormsList.add(sf);
        }
        
        
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(Formula.class)
                .get();
        
        org.apache.lucene.search.Query luceneQuery = qb
                .keyword().fuzzy().withThreshold(Float.valueOf(properties.get(FormulaService.VALUE_DISTANCEMETHOD)))
                .withPrefixLength(1)
                .onField("distanceForm")
                //.ignoreAnalyzer()
                .ignoreFieldBridge()                //https://forum.hibernate.org/viewtopic.php?f=9&t=1008943
                .matching(sb.toString())
                .createQuery();
        
        org.hibernate.search.jpa.FullTextQuery ftq = fullTextEntityManager
                .createFullTextQuery(luceneQuery, Formula.class);
        
        
        
        resultSet.addAll(ftq.getResultList());
        List<Object[]> explains = ftq.setProjection("distanceForm",FullTextQuery.EXPLANATION).getResultList();
        
        for(Object[] o :explains)
        {
            String form = (String) o[0];
            Explanation e = (Explanation) o[1];
            
            logger.info(form+"\n"+e);
        }
     
        
        return new ArrayList<>(resultSet);                
    }

    @Override
    public void findSimilarMass(Map<String,String> properties)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
