/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.services.MailService;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@ControllerAdvice
public class ExceptionHandlingController
{

    private static final Logger logger = Logger.getLogger(ExceptionHandlingController.class);

    @Autowired
    private MailService mailservice;
    
    
    // just example, there might be different handlign process
    // for each exception. If any error occurs in hibernate / jpa
    // related class then spring translates it into its hierarchy
    // see http://docs.spring.io/spring/docs/current/spring-framework-reference/html/dao.html#dao-exceptions
    
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ModelAndView handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex)
    {
        logger.info("InvalidDataAccessApiUsageException has occured.");
        logger.error(ex);
        
        return processException(ex);
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException npe)
    {
        logger.info("Nullpointer exception has occured.");
        logger.error(npe);
        
        

        return processException(npe);
    }
    
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e)
    {
        logger.info("Exception caught, but general one. Consider creating separate handler for \""+e.getClass().getSimpleName()+"\" exception.");
        logger.info(e);
        

        
        return processException(e);
    }
    
    
    private ModelAndView processException(Exception e)
    {
        StringWriter writer = new StringWriter();

        e.printStackTrace(new PrintWriter(writer));
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("An exception from following class has occurred: ")
                .append(e.getClass().getName()).append("\n");
        
        sb.append("Message was: ").append(e.getMessage()).append("\n");
        sb.append("With following stacktrace:\n").append(writer.toString());
        
        mailservice.sendMail("Error occured", sb.toString());

        return new ModelAndView("errors/error", 
                prepareModelMap(e, writer.toString()));
    }

    private ModelMap prepareModelMap(Exception e, String stacktraceOutput)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("exception", e.getClass().getSimpleName());
        mm.addAttribute("message", e.getMessage());
        mm.addAttribute("stackTrace", stacktraceOutput);

        return mm;
    }

}
