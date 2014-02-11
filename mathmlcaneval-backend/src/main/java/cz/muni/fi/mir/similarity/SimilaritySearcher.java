/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.similarity;

import cz.muni.fi.mir.db.domain.CanonicOutput;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
public class SimilaritySearcher
{

    @PersistenceContext
    private EntityManager entityManager;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SimilaritySearcher.class);

    /**
     * Method used for finding similar canonic forms to given one. To see how
     * canonic form used for similarity match is made see
     * {@link cz.muni.fi.mir.tools.SimilarityFormConverter} <u>convert()</u>
     * methods. The algorithm for finding possible similar form is as follows:
     *
     * At first we map CanonicOutput entity into simplified
     * {@link cz.muni.fi.mir.db.domain.SimilarForm} entity, because JPA does not
     * allow to convert SQL result in form
     * <pre>SELECT id,simForm FROM tableX</pre> into
     * <pre>HashMap&lt;Long,String&gt;</pre> thus we need separate class for it.
     * If <i>optimizeLength</i> parameter is set to <b>true</b> only similar
     * forms with exact length will be selected from database. Once loading is
     * done (we have to obtain all pairs) we start calculating distance which
     * determines the difference between our desired canonic output and others
     * persisted inside database. For calculation is used Levensthein distance
     * see <a
     * href="http://en.wikipedia.org/wiki/Levenshtein_distance">http://en.wikipedia.org/wiki/Levenshtein_distance</a>
     * based on Apache StringUtils calculation see
     * {@link org.apache.commons.lang3.StringUtils#getLevenshteinDistance(java.lang.CharSequence, java.lang.CharSequence) }.
     * The maximum allowed distance is specified by given input parameter. After
     * distance is calculated and distance is in allowed range the <b>id</b> of
     * matched output is added into temp list which is later used for obtaining
     * matched similar forms for given input.
     *
     * @param canonicOutput output of which similar forms are to be found inside
     * database
     * @param allowedDistance maximum allowed distance used for calculation
     * @param optimizeLength parameter whether length should be optimized
     * @return similar canonic forms, if no match is found empty list is
     * returned
     */
    @Transactional(readOnly = true)
    public List<CanonicOutput> findSimilarFormsUsingLevensthein(CanonicOutput canonicOutput, int allowedDistance, boolean optimizeLength)
    {
        List<SimilarForm> sForms = new ArrayList<>();           // list used as temp result obtained from database
        List<Long> ids = new ArrayList<>();                     // list from storing matched id
        List<CanonicOutput> resultList = new ArrayList<>();     // resultlist
        try
        {
            if (optimizeLength)
            {
                sForms = entityManager.createQuery("SELECT sf FROM SimilarForm sf WHERE LENGTH(sf.similarForm) = :stringLength", SimilarForm.class)
                        .setParameter("stringLength", canonicOutput.getSimilarForm().length()).getResultList();
            } 
            else
            {
                sForms = entityManager.createQuery("SELECT sf FROM SimilarForm sf", SimilarForm.class).getResultList();
            }

        } 
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        for (SimilarForm sf : sForms)
        {
            int distance = StringUtils.getLevenshteinDistance(canonicOutput.getSimilarForm(), sf.getSimilarForm());
            logger.debug("leventsthein(" + canonicOutput.getSimilarForm() + "," + sf.getSimilarForm() + ")=" + distance);

            if (distance <= allowedDistance)
            {
                ids.add(sf.getId());
            }
        }
        if (!ids.isEmpty())
        {
            try
            {
                resultList = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.id IN :idList", CanonicOutput.class)
                        .setParameter("idList", ids).getResultList();
            } 
            catch (NoResultException nre)
            {
                logger.debug(nre);
            }
        }

        return resultList;
    }

    /**
     * Method is same as {@link #findSimilarFormsUsingLevensthein(cz.muni.fi.mir.db.domain.CanonicOutput, int, boolean)
     * }. The only difference is in optimizeLength parameter which serves as
     * direct String upper bound length limitation. If set to 0 or any negative
     * number {@link #findSimilarFormsUsingLevensthein(cz.muni.fi.mir.db.domain.CanonicOutput, int, boolean)
     * }
     * with false is called. So if we call method with
     * <u>optimizeLength=</u><b>3</b> only strings that has length 3 or less are
     * selected from database.
     *
     * @param canonicOutput output of which similar forms are to be found inside
     * database
     * @param allowedDistance maximum allowed distance used for calculation
     * @param optimizeLength parameter whether in which length should be
     * optimized
     * @return similar canonic forms, if no match is found empty list is
     * returned
     */
    @Transactional(readOnly = true)
    public List<CanonicOutput> findSimilarFormsUsingLevensthein(CanonicOutput canonicOutput, int allowedDistance, int optimizeLength)
    {
        if (optimizeLength <= 0)
        {
            return findSimilarFormsUsingLevensthein(canonicOutput, allowedDistance, false);
        } 
        else
        {
            List<SimilarForm> sForms = new ArrayList<>();
            List<Long> ids = new ArrayList<>();
            List<CanonicOutput> resultList = new ArrayList<>();
            try
            {
                sForms = entityManager.createQuery("SELECT sf FROM SimilarForm sf WHERE LENGTH(sf.similarForm) <= :stringLength", SimilarForm.class)
                        .setParameter("stringLength", optimizeLength).getResultList();
            } 
            catch (NoResultException nre)
            {
                logger.debug(nre);
            }

            for (SimilarForm sf : sForms)
            {
                int distance = StringUtils.getLevenshteinDistance(canonicOutput.getSimilarForm(), sf.getSimilarForm());
                logger.debug("leventsthein(" + canonicOutput.getSimilarForm() + "," + sf.getSimilarForm() + ")=" + distance);

                if (distance <= allowedDistance)
                {
                    ids.add(sf.getId());
                }
            }
            if (!ids.isEmpty())
            {
                try
                {
                    resultList = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.id IN :idList", CanonicOutput.class)
                            .setParameter("idList", ids).getResultList();
                } 
                catch (NoResultException nre)
                {
                    logger.debug(nre);
                }
            }

            return resultList;
        }
    }
}
