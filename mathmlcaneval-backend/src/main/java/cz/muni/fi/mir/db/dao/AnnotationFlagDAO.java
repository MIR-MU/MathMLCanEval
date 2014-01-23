/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.AnnotationFlag;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface AnnotationFlagDAO
{
    void createFlagAnnotation(AnnotationFlag annotationFlag);
    void updateFlagAnnotation(AnnotationFlag annotationFlag);
    void deleteFlagAnnotation(AnnotationFlag annotationFlag);
    
    
    AnnotationFlag getAnnotationFlagByID(Long id);   
    
    List<AnnotationFlag> getAllAnnotationFlags();
    List<AnnotationFlag> findAnnotationFlagByValue(String value);    
}
