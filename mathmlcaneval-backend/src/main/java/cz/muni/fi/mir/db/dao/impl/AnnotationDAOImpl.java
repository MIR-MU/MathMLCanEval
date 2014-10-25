package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.User;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
@Repository(value = "annotationDAO")
public class AnnotationDAOImpl extends GenericDAOImpl<Annotation, Long> implements AnnotationDAO
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationDAOImpl.class);

    public AnnotationDAOImpl()
    {
        super(Annotation.class);
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
            resultList = entityManager.createQuery("SELECT a FROM annotation a WHERE a.annotationContent LIKE :note ORDER BY a.id DESC", Annotation.class)
                    .setParameter("note", "%"+note+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }    

    @Override
    public List<Annotation> getAllAnnotationsFromRange(int start, int end)
    {
        List<Annotation> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT a FROM annotation a ORDER BY a.id DESC",Annotation.class)
                    .setFirstResult(start).setMaxResults(end-start).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
