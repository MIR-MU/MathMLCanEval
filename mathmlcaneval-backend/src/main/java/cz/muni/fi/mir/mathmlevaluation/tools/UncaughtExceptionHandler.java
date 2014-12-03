/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.tools;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component("uncaughtExceptionHandler")
public class UncaughtExceptionHandler implements AsyncUncaughtExceptionHandler
{
    private static final Logger logger = Logger.getLogger(UncaughtExceptionHandler.class);
    @Override
    public void handleUncaughtException(Throwable thrwbl, Method method, Object... os)
    {
        logger.info(thrwbl);
    }
    
}
