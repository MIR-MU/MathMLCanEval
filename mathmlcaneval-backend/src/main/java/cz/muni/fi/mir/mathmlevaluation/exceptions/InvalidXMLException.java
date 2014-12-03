/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.exceptions;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@SuppressWarnings("serial")
public class InvalidXMLException extends MathMLEvaluationException
{

    public InvalidXMLException()
    {
    }

    public InvalidXMLException(String message)
    {
        super(message);
    }

    public InvalidXMLException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidXMLException(Throwable cause)
    {
        super(cause);
    }

    public InvalidXMLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
