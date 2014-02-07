package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.AnnotationFlag;
import java.util.List;

/**
 * Interface used as implementation for service layer for {@link cz.muni.fi.mir.domain.AnnotationFlag} class in form
 * of implementation of its DAO class {@link cz.muni.fi.mir.dao.AnnotationFlagDAO}. The purpose of this layer is to provide
 * Transactions for DAO layer. And necessary ID checking.
 * 
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public interface AnnotationFlagService
{
    /**
     * 
     * @param annotationFlag 
     */
    void createFlagAnnotation(AnnotationFlag annotationFlag);
    
    /**
     * 
     * @param annotationFlag 
     * @throws IllegalArgumentException 
     */
    void updateFlagAnnotation(AnnotationFlag annotationFlag) throws IllegalArgumentException;
    
    /**
     * 
     * @param annotationFlag 
     */
    void deleteFlagAnnotation(AnnotationFlag annotationFlag) throws IllegalArgumentException;
    
    /**
     * 
     * @param id
     * @return 
     */
    AnnotationFlag getAnnotationFlagByID(Long id) throws IllegalArgumentException;
    
    /**
     * 
     * @return 
     */
    List<AnnotationFlag> getAllAnnotationFlags();
    
    /**
     * 
     * @param value
     * @return 
     */
    List<AnnotationFlag> findAnnotationFlagByValue(String value) throws IllegalArgumentException;     
}
