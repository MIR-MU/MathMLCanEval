package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.User;
import java.io.Serializable;
import java.util.List;

/**
 * The purpose of this interface is to provide basic CRUD operations and search
 * functionality on {@link cz.muni.fi.mir.db.domain.Annotation} objects
 * persisted inside any given database engine specified by configuration. Since
 * there might be some functionality that requires more operation calls, no
 * transaction management should be managed on this layer. Also no validation is
 * made on this layer so make sure you do not pass non-valid objects into
 * implementation of this DAO (Database Access Object) layer.
 *
 * @author Dominik Szalai
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface AnnotationDAO extends GenericDAO<Annotation, Long>
{
    /**
     * Method obtains all Annotations from database in <b>DESCENDING</b> order.
     * So newer annotation are in the front, and the old ones in the back of
     * list.
     *
     * @return List with Annotations in descending order. If there are no
     * Annotations yet then empty List returned.
     */
    List<Annotation> getAllAnnotations();

    /**
     * Method obtains all Annotations from database in <b>DESCENDING</b> order
     * from given range. So newer annotations are in the front, and the old ones
     * in the back of list. This may come handy e.g. for AJAX request while
     * listing entries in pages and we do not want to retrieve all annotations
     * at once.
     *
     * @param start start position of result set
     * @param end end position of result set
     * @return List of Annotations in descending order, from given start
     * position to end one. if there are no Annotations yet, empty List is
     * returned.
     */
    List<Annotation> getAllAnnotationsFromRange(int start, int end);

    /**
     * Method obtains all Annotations from database in <b>DESCENDING</b> order
     * from given user passed as method argument.
     *
     * @param user whose Annotations we want to obtain
     * @return List of annotation created by given User. If there are no
     * Annotations made by this User empty List returned.
     */
    List<Annotation> getAnnotationByUser(User user);

    /**
     * Method obtains all Annotations with given note. Note might be full note,
     * or any substring that note may contain.
     *
     * @param note any string to be found in Annotation note
     * @return List of Annotations containing given note keyword, or empty list
     * if there is no match
     */
    List<Annotation> findByNote(String note);
}
