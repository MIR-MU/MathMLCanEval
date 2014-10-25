/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.AnnotationValue;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AnnotationValueSerivce
{

    void createAnnotationValue(AnnotationValue annotationValue);

    void updateAnnotationValue(AnnotationValue annotationValue);

    void deleteAnnotationValue(AnnotationValue annotationValue);

    AnnotationValue getAnnotationValueByID(AnnotationValue annotationValue);

    /**
     * Method selects all annotations in systems and looks for annotation.
     * Annotation always starts with <b>&#35;</b> and when it is matched its
     * stored inside db. This method does not delete previous annotation just
     * add new ones.
     */
    void populate();

    List<AnnotationValue> getAll();
}
