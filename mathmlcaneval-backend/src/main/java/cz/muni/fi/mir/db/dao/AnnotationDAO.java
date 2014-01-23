/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.User;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface AnnotationDAO
{
    void createAnnotation(Annotation annotation);
    void updateAnnotation(Annotation annotation);
    void deleteAnnotation(Annotation annotation);
    
    Annotation getAnnotationByID(Long id);
    
    List<Annotation> getAllAnnotations();
    List<Annotation> getAnnotationByUser(User user);
    List<Annotation> getAnnotationByFlag(AnnotationFlag flag);
    List<Annotation> findByNote(String note);
}
