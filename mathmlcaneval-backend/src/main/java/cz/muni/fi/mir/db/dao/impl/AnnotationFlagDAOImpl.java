package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.AnnotationFlagDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.tools.Tools;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
@Repository(value = "annotationFlagDAO")
public class AnnotationFlagDAOImpl implements AnnotationFlagDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationFlagDAOImpl.class);

    @Override
    public void createFlagAnnotation(AnnotationFlag annotationFlag)
    {
        entityManager.persist(annotationFlag);
    }

    @Override
    public void updateFlagAnnotation(AnnotationFlag annotationFlag)
    {
        entityManager.merge(annotationFlag);
    }

    @Override
    public void deleteFlagAnnotation(AnnotationFlag annotationFlag)
    {
        AnnotationFlag af = entityManager.find(AnnotationFlag.class, annotationFlag.getId());
        if(af != null)
        {
            entityManager.remove(af);
        }
        else
        {
            logger.info("Trying to delete AnnotationFlag with ID that has not been found. The ID is ["+annotationFlag.getId().toString()+"]");
        }
    }

    @Override
    public AnnotationFlag getAnnotationFlagByID(Long id)
    {
        return entityManager.find(AnnotationFlag.class, id);
    }

    @Override
    public List<AnnotationFlag> getAllAnnotationFlags()
    {
        List<AnnotationFlag> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT af FROM annotationflag af ORDER BY af.id DESC", AnnotationFlag.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<AnnotationFlag> findAnnotationFlagByValue(String value)
    {
        if(Tools.getInstance().stringIsEmpty(value))
        {
            return getAllAnnotationFlags();
        }
        else
        {
            List<AnnotationFlag> resultList = Collections.emptyList();
            
            try
            {
                resultList = entityManager.createQuery("SELECT af FROM annotationflag af WHERE af.flagValue LIKE :value ORDER BY af.id DESC", AnnotationFlag.class)
                        .setParameter("value", "%"+value+"%").getResultList();
            }
            catch(NoResultException nre)
            {
                logger.debug(nre);
            }
            
            return resultList;
        }
    }

    @Override
    public List<AnnotationFlag> getAllAnnotationFlagsFromRange(int start, int end)
    {
        List<AnnotationFlag> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT af FROM annotationflag af ORDER BY af.id DESC",AnnotationFlag.class)
                    .setFirstResult(start).setMaxResults(end-start).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
