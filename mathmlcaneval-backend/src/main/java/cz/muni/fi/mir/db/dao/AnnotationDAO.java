package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Annotation;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
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
}
