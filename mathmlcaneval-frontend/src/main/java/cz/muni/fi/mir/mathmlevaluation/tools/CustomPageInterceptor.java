/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.mathmlevaluation.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author emptak
 */
public class CustomPageInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger logger = Logger.getLogger(CustomPageInterceptor.class);
    @Autowired private GitPropertiesModel gitPropertiesModel;

    public CustomPageInterceptor()
    {
        logger.info("Custom page interceptor created");
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        if(modelAndView != null)
        {
            modelAndView.addObject("gitPropertiesModel", gitPropertiesModel);
        }
    }
    
    
    
}
