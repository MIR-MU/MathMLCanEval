package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
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
}
