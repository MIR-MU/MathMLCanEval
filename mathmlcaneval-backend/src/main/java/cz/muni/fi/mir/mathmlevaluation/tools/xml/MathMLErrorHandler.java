/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.tools.xml;

import org.xml.sax.ErrorHandler;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MathMLErrorHandler extends ErrorHandler
{
    String getError();
}
