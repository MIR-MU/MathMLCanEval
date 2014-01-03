/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.dao.impl;

import cz.muni.fi.mir.dao.AnnotationFlagDAO;
import cz.muni.fi.mir.domain.AnnotationFlag;
import cz.muni.fi.mir.tools.Tools;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
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
        List<AnnotationFlag> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT af FROM annotationflag af", AnnotationFlag.class)
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
            List<AnnotationFlag> resultList = new ArrayList<>();
            
            try
            {
                resultList = entityManager.createQuery("SELECT af FROM annotationflag af WHERE af.flagValue LIKE :value", AnnotationFlag.class)
                        .setParameter("value", "%"+value+"%").getResultList();
            }
            catch(NoResultException nre)
            {
                logger.debug(nre);
            }
            
            return resultList;
        }
    }
    
}
