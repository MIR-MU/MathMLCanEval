/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.FormulaSearchResponse;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.similarity.SimilarityFormConverter;
import cz.muni.fi.mir.similarity.SimilarityForms;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.lucene.search.Query;
import org.hibernate.Hibernate;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
    private static final Pattern p = Pattern.compile("\\d+");

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
            logger.info("Deleting formula [" + formula.getId() + ", " + formula.getHashValue() + "]");
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
    public List<Formula> getAllFormulas()
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f ORDER BY f.id DESC", Formula.class).getResultList();
        }
        catch (NoResultException nre)
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
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<Formula> getFormulasByElements(Collection<Element> collection, int start, int end)
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
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<Formula> getAllFormulas(boolean force)
    {
        List<Formula> resultList = Collections.emptyList();
        if (force)
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
            catch (NoResultException nre)
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
    public List<Formula> findSimilar(Formula formula, Map<String, String> properties, boolean override, boolean directWrite)
    {
        // worst method i have ever written
        // this one might need lot of rework later.
        // because all values are stored in index rework into projection might be nice
        // so we dont have to access db (like at the end of this method)

//       {countCondition=AND, useBranch=false, 
//       branchCondition=null, useOverride=false, 
//       useDistance=true, branchMethodValue=, 
//       countElementMethodValue=EXACT, 
//       distanceCondition=AND, useCount=false, distanceMethodValue=0.8}
        logger.info(properties);
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        org.hibernate.search.jpa.FullTextQuery ftq = null;  // actual query hitting database
        Set<Formula> resultSet = new HashSet<>();

        List<Formula> distanceResult = new ArrayList<>();
        List<Formula> countResult = new ArrayList<>();
        List<Formula> branchResult = new ArrayList<>();

        SimilarityForms sf = similarityFormConverter.process(formula.getOutputs().get(0));

        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(Formula.class)
                .overridesForField("co.countElementForm", "countElementFormAnalyzer") //we have to override default analyzer
                .get();

        org.apache.lucene.search.Query distanceFormQuery = null;
        org.apache.lucene.search.Query countElementQuery = null;
        org.apache.lucene.search.Query branchQuery = null;

        // user selected that he wants use DISTANCE method
        if (Boolean.valueOf(properties.get(FormulaService.USE_DISTANCE)))
        {
            distanceFormQuery = qb
                    .keyword().fuzzy()
                    .withThreshold(Float.valueOf(properties.get(FormulaService.VALUE_DISTANCEMETHOD)))
                    .withPrefixLength(1)
                    .onField("co.distanceForm")
                    //.ignoreAnalyzer()
                    .ignoreFieldBridge() //https://forum.hibernate.org/viewtopic.php?f=9&t=1008943
                    .matching(sf.getDistanceForm())
                    .createQuery();

            logger.debug("Distance query:" + distanceFormQuery.toString());

            ftq = fullTextEntityManager
                    .createFullTextQuery(distanceFormQuery, Formula.class);

            //todo pagination
            distanceResult.addAll(ftq.setMaxResults(80).getResultList());
        }

        // user selected that he wants COUNT method
        if (Boolean.valueOf(properties.get(FormulaService.USE_COUNT)))
        {
            // TODO exact / partial match from FormulaService.VALUE_COUNTELEMENTMETHOD
            // need rework because tokens are treated  should instead of must
            countElementQuery
                    = qb.keyword().onField("co.countElementForm")
                    .ignoreFieldBridge()
                    .matching(sf.getCountForm()).createQuery();

            logger.debug("Count element query:" + countElementQuery.toString());

            ftq = fullTextEntityManager
                    .createFullTextQuery(countElementQuery, Formula.class);

            //TODO pagination
            countResult.addAll(ftq.setMaxResults(80).getResultList());
        }

        // user selected that he wants BRANCH method
        if (Boolean.valueOf(properties.get(FormulaService.USE_BRANCH)))
        {
            // we obtain user input from form which might be variable A
            // case A = exact length
            // case -A = [currentLength-A;currentLength]
            // cae +A = [currentLength;currentLength+A]
            // case +-A = [currentLength-A;currentLength+A]
            // case -+A = same as above
            String input = properties.get(FormulaService.VALUE_BRANCHMETHOD);
            //we substract number from input
            Matcher m = p.matcher(input);
            m.find();
            int difference = Integer.parseInt(m.group()); // the difference from inpitu

            boolean from = input.contains("-");                                 // flag for case -A
            boolean to = input.contains("+");                                   // flag for case +A
            int branchLength = Integer.parseInt(sf.getLongestBranch());         // current length of formula

            // case +- A || -+ A
            if (from && to)
            {
                branchQuery = qb.range().onField("co.longestBranch")
                        .from(branchLength - difference)
                        .to(branchLength + difference)
                        .createQuery();
            }
            // case -A
            else
            {
                if (from && !to)
                {
                    branchQuery = qb.range().onField("co.longestBranch")
                            .from(branchLength - difference)
                            .to(branchLength)
                            .createQuery();
                }

                // case +A
                else
                {
                    if (!from && to)
                    {
                        branchQuery = qb.range().onField("co.longestBranch")
                                .from(branchLength)
                                .to(branchLength + difference)
                                .createQuery();
                    }
                    // case A
                    else
                    {
                        branchQuery = qb.keyword().onField("co.longestBranch")
                                .matching(branchLength)
                                .createQuery();
                    }
                }
            }

            logger.debug("Branch query: " + branchQuery.toString());

            ftq = fullTextEntityManager
                    .createFullTextQuery(branchQuery, Formula.class);
            //TODO pagination
            branchResult.addAll(ftq.setMaxResults(80).getResultList());

        }

        if (!distanceResult.isEmpty() && "AND".equalsIgnoreCase(properties.get(FormulaService.CONDITION_DISTANCE)))
        {
            resultSet.addAll(distanceResult);
        }

        if (!countResult.isEmpty())
        {
            if ("AND".equalsIgnoreCase(properties.get(FormulaService.CONDITION_COUNT)))
            {
                resultSet.retainAll(countResult);
            }
            else
            {
                resultSet.addAll(countResult);
            }
        }

        if (!branchResult.isEmpty())
        {
            if ("AND".equalsIgnoreCase(properties.get(FormulaService.CONDITION_BRANCH)))
            {
                resultSet.retainAll(branchResult);
            }
            else
            {
                resultSet.addAll(branchResult);
            }
        }

        //resultSet.addAll(ftq.getResultList());
//        List<Object[]> explains = ftq.setProjection("distanceForm","countElementForm",FullTextQuery.EXPLANATION).getResultList();
//        
//        for(Object[] o :explains)
//        {
//            String form = (String) o[0];
//            String form2 = (String) o[1];
//            Explanation e = (Explanation) o[2];
//            
//            logger.info(form+"$"+form2+"\n"+e);
//        }
        // formula itself shouldnt be between results
        resultSet.remove(formula);

        // we would like to write results immediately
        if (directWrite)
        {   // override old results ?
            if (override)
            {
                formula.setSimilarFormulas(new ArrayList<>(resultSet));
            }
            else
            {   // check if null and append to earlier
                List<Formula> similars = new ArrayList<>();
                if (formula.getSimilarFormulas() != null)
                {
                    similars.addAll(formula.getSimilarFormulas());
                    similars.addAll(resultSet);

                    formula.setSimilarFormulas(similars);
                }
                else
                {
                    similars.addAll(resultSet);

                    formula.setSimilarFormulas(similars);
                }
            }
            // update
            updateFormula(formula);
        }

        return new ArrayList<>(resultSet);
    }

    @Override
    public void findSimilarMass(Map<String, String> properties)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormulaSearchResponse findFormulas(FormulaSearchRequest formulaSearchRequest)
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        org.hibernate.search.jpa.FullTextQuery ftq = null;  // actual query hitting database
        boolean isEmpty = true;

        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Formula.class)
                .overridesForField("co.annotation", "standardAnalyzer")
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
                        .ignoreAnalyzer()
                        .matching(e.getElementName()+formulaSearchRequest.getElements().get(e))
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
        
        FormulaSearchResponse fsr = new FormulaSearchResponse();
        
        if(!isEmpty)
        {
            Query query = junction.createQuery();
            logger.info(query);

            ftq = fullTextEntityManager.createFullTextQuery(query, Formula.class);
            
            fsr.setFormulas(ftq.getResultList());
        }
        else
        {
            //TODO
            fsr.setFormulas(getAllFormulas(0, 20));
        }        
        
        return fsr;        
    }
}
