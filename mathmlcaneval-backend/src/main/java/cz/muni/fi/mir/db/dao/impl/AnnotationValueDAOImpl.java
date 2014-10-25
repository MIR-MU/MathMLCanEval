/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.AnnotationValueDAO;
import cz.muni.fi.mir.db.domain.AnnotationValue;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository
public class AnnotationValueDAOImpl extends GenericDAOImpl<AnnotationValue, Long> implements AnnotationValueDAO
{
    private static final Logger logger = Logger.getLogger(AnnotationValueDAOImpl.class);
    
    public AnnotationValueDAOImpl()
    {
        super(AnnotationValue.class);
    }

    @Override
    public List<AnnotationValue> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnotationValue getByValue(String value)
    {
        AnnotationValue result = null;
        try
        {
            result = entityManager.createQuery("SELECT av FROM annotationValue av WHERE av.value = :value", AnnotationValue.class)
                    .setParameter("value", value).getSingleResult();
        }
        catch(NoResultException ex)
        {
            logger.info(ex);
        }
        
        return result;
    }
}
