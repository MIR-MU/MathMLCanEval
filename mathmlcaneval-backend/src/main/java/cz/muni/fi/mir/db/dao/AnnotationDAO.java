package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.User;
import java.util.List;

/**
 *  The purpose of this interface is to provide basic CRUD operations and search 
 * functionality on Annotation objects persisted inside any given database engine 
 * specified by configuration. Since there might be some functionality that requires
 * more operation calls, no transaction management should be managed on this layer.
 * Also no validation is made on this layer so make sure you do not pass non-valid
 * objects into implementation of this DAO (Database Access Object) layer.
 * 
 * @author Dominik Szalai
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public interface AnnotationDAO
{
    /**
     * Method creates given annotation inside database.
     * @param annotation to be persisted
     */
    void createAnnotation(Annotation annotation);
    
    /**
     * Method updates given annotation inside database.
     * @param annotation to be updated
     */
    void updateAnnotation(Annotation annotation);
    
    /**
     * Method removes given annotation from database. Because Entity manager
     * just checks ID, no other values than ID have to be set.
     * @param annotation to be deleted
     */
    void deleteAnnotation(Annotation annotation);
    
    
    /**
     * Method obtains annotation from database based on given ID.
     * @param id of annotation to be fetched
     * @return Annotation with given ID, null if there is no match for given ID
     */
    Annotation getAnnotationByID(Long id);
    
    
    /**
     * Method obtains all annotations from database in <b>DESCENDING</b> order.
     * So newer annotation are in the front, and the old ones in the back of list.
     * @return List with annotation in descending order. If there are no annotations
     * yet then empty List returned.
     */
    List<Annotation> getAllAnnotations();
    
    /**
     * Method obtains all annotations from database in <b>DESCENDING</b> order
     * from given user passed as method argument.
     * @param user whose annotations we want to obtain
     * @return List of annotation created by given User. If there are no annotations
     * made by this User empty List returned.
     */
    List<Annotation> getAnnotationByUser(User user);
    
    /**
     * Method fetches all annotations from database in <b>DESCENDING</b> order based
     * on AnnotationFlag argument passed as method argument.
     * @param flag specifying annotation having this flag.
     * @return List of annotations with given flag. If there are no such annotations
     * with this flag empty List is returned then.
     */
    List<Annotation> getAnnotationByFlag(AnnotationFlag flag);
    
    /**
     * Method obtains all annotations with given note. Note might be full note,
     * or any substring that note may contain.
     * @param note any string to be found in annotation note
     * @return List of annotations containing given note keyword, or empty list
     * if there is no match
     */
    List<Annotation> findByNote(String note);
}
