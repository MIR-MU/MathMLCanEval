package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.User;
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
@Repository(value = "annotationDAO")
public class AnnotationDAOImpl implements AnnotationDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationDAOImpl.class);

    @Override
    public void createAnnotation(Annotation annotation)
    {
        entityManager.persist(annotation);
    }

    @Override
    public void updateAnnotation(Annotation annotation)
    {
        entityManager.merge(annotation);
    }

    @Override
    public void deleteAnnotation(Annotation annotation)
    {
        Annotation a = entityManager.find(Annotation.class, annotation.getId());
        if(a != null)
        {
            entityManager.remove(a);
        }
        else
        {
            logger.info("Trying to delete Annotation with ID that has not been found. The ID is ["+annotation.getId().toString()+"]");
        }
    }

    @Override
    public Annotation getAnnotationByID(Long id)
    {
        return entityManager.find(Annotation.class, id);
    }

    @Override
    public List<Annotation> getAllAnnotations()
    {
        List<Annotation> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT a FROM annotation a ORDER BY a.id DESC", Annotation.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Annotation> getAnnotationByUser(User user)
    {
        List<Annotation> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT a FROM annotation a WHERE a.user = :user ORDER BY a.id DESC", Annotation.class)
                    .setParameter("user", user).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(resultList);
        }
        
        return resultList;
    }

    @Override
    public List<Annotation> getAnnotationByFlag(AnnotationFlag flag)
    {
        List<Annotation> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT a FROM annotation a WHERE a.annotationFlag = :aFlag ORDER BY a.id DESC", Annotation.class)
                    .setParameter("aFlag", flag).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Annotation> findByNote(String note)
    {
        List<Annotation> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT a FROM annotation a WHERE a.note LIKE :note ORDER BY a.id DESC", Annotation.class)
                    .setParameter("note", "%"+note+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }    
}
