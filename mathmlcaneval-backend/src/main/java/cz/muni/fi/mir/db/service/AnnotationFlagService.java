package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.AnnotationFlag;
import java.util.List;

/**
 * Interface used as implementation for service layer for
 * {@link cz.muni.fi.mir.db.domain.AnnotationFlag} class in form of
 * implementation of its DAO class
 * {@link cz.muni.fi.mir.db.dao.AnnotationFlagDAO}. The purpose of this layer is
 * to provide Transactions for DAO layer. And necessary ID checking or parameter
 * checking. Objects passed as method arguments are not checked. That is the
 * purpose of Validator on frontend.
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public interface AnnotationFlagService
{

    /**
     * Method calls {@link cz.muni.fi.mir.db.dao.AnnotationDAO#createAnnotation(cz.muni.fi.mir.db.domain.Annotation)
     * }
     * with Transactional support. Because creating is not read operation we set
     * readOnly to false.
     *
     * @param annotationFlag to be created
     */
    void createFlagAnnotation(AnnotationFlag annotationFlag);

    void updateFlagAnnotation(AnnotationFlag annotationFlag) throws IllegalArgumentException;

    void deleteFlagAnnotation(AnnotationFlag annotationFlag) throws IllegalArgumentException;

    AnnotationFlag getAnnotationFlagByID(Long id) throws IllegalArgumentException;

    List<AnnotationFlag> getAllAnnotationFlags();

    List<AnnotationFlag> getAllAnnotationFlagsFromRange(int start, int end) throws IllegalArgumentException;

    List<AnnotationFlag> findAnnotationFlagByValue(String value) throws IllegalArgumentException;
}
