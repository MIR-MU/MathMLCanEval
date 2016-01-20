/*
 * Copyright 2016 Dominik Szalai - emptulik at gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.web.sitetitle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class SiteTitleInterceptor implements HandlerInterceptor, MessageSourceAware, InitializingBean
{
    private MessageSource messageSource;
    private String mainTitle;
    private String separator;
    private String suffix;
    private String modelVariable;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        if (handler instanceof HandlerMethod && modelAndView != null)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            SiteTitle annotation = handlerMethod.getMethodAnnotation(SiteTitle.class);
            String prefix = null;
            if (annotation != null)
            {
                if (!annotation.value().isEmpty())
                {
                    prefix = annotation.value();
                }
                else if (!annotation.i18n().isEmpty())
                {
                    prefix = messageSource.getMessage(annotation.i18n(), null, LocaleContextHolder.getLocale());
                }
                else
                {
                    prefix = "";
                }
            }

            modelAndView.addObject(modelVariable, prefix + suffix);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }

    @Override
    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public void setMainTitle(String mainTitle)
    {
        this.mainTitle = mainTitle;
    }

    public void setSeparator(String separator)
    {
        this.separator = separator;
    }

    public void setModelVariable(String modelVariable)
    {
        this.modelVariable = modelVariable;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        suffix = " " + separator + " " + mainTitle;
    }
}
