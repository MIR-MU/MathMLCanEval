/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers.exceptions.handlers;

import org.dozer.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@ControllerAdvice
public class ExpectionsHandler
{
    @ExceptionHandler(MappingException.class)
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT,reason = "null obtained")
    public ModelAndView handleMappingException(MappingException me)
    {
        return null;
    }
}
