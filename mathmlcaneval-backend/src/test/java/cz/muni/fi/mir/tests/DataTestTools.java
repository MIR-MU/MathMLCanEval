/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.tools.EntityFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Empt
 */
public class DataTestTools
{
    /**
     * Method provides List of AnnotationFlags with following values:
     * <ul>
     * <li>VALUE_PROPER_RESULT</li>
     * <li>VALUE_MIGHT_BE_PROPER</li>
     * <li>VALUE_WRONG</li>
     * <li>VALUE_CHECK_PLS</li>
     * </ul>
     * @return List of AnnotationFlags 
     */
    public static List<AnnotationFlag> provideAnnotationFlagList()
    {
        List<AnnotationFlag> result = new ArrayList<>();
        
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_MIGHT_BE_PROPER));
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_WRONG));
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_CHECK_PLS));
        
        return result;
    }
    
}
