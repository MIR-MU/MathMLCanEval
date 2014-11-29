/*
 * Copyright 2014 Dominik Szalai - emptulik at gmail.com.
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
package cz.muni.fi.mir.mathmlevaluation.tools;

import java.lang.annotation.Annotation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class SiteTitleInterceptor extends HandlerInterceptorAdapter implements MessageSourceAware
{

    private String mainTitle = "";
    private String separator = "";
    private String subTitle = "";
    private String modelAttributeName = "websiteTitle";
    private boolean composeOnMissingAnnotation = true;
    private MessageSource messageSource;
    private boolean i18nEnabled = false;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        if (handler instanceof HandlerMethod && modelAndView != null)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            SiteTitleContainer result = new SiteTitleContainer();

            Annotation classTitleAnotation = null;
            Annotation methodTitleAnnotation = null;
            SiteTitle classCast = null;
            SiteTitle methodCast = null;

            for (Annotation a : handlerMethod.getMethod().getDeclaringClass().getAnnotations())
            {

                if (a.annotationType().equals(SiteTitle.class))
                {
                    classTitleAnotation = a;
                    break;
                }
            }

            for (Annotation a : handlerMethod.getMethod().getAnnotations())
            {
                if (a.annotationType().equals(SiteTitle.class))
                {
                    methodTitleAnnotation = a;
                    break;
                }
            }

            if (classTitleAnotation != null)
            {
                classCast = (SiteTitle) classTitleAnotation;
            }

            if (methodTitleAnnotation != null)
            {
                methodCast = (SiteTitle) methodTitleAnnotation;
            }

            // method has annotation
            if (methodCast != null)
            {
                // current method does not have set main title
                // so main title may be at class level, or set via
                // bean initialization process
                if (StringUtils.isEmpty(methodCast.mainTitle()))
                {
                    resolveClassLevelTitle(classCast, result);
                }
                else
                {   //method has annotation and set maintitle
                    result.setMainTitle(methodCast.mainTitle());
                }

                // current method does not have set separator
                // so separator may be at class level, or set via
                // bean initialization process
                if (StringUtils.isEmpty(methodCast.separator()))
                {
                    resolveClassLevelSeparator(classCast, result);
                }
                else
                {   // method has annotation and set separator
                    result.setSeparator(methodCast.separator());
                }

                // current method does not have set subtitle
                // so subtitle may be at class level, or set via
                // bean initialization process
                if (StringUtils.isEmpty(methodCast.value()))
                {
                    resolveClassLevelSubTitle(classCast, result);
                }
                else
                {   // method has annotation and set subtitle
                    result.setSubTitle(methodCast.value());
                }

                modelAndView.addObject(modelAttributeName, result.toString());
            }
            else
            {
                // called method does not have annotation
                // so we check if there is class one
                if (classCast != null)
                {
                    resolveClassLevelTitle(classCast, result);

                    resolveClassLevelSeparator(classCast, result);

                    resolveClassLevelSubTitle(classCast, result);

                    modelAndView.addObject(modelAttributeName, result.toString());
                }
                else
                {
                    if (this.composeOnMissingAnnotation)
                    {
                        // there is no class nor method annotation
                        // if compose is set to true we create title
                        // from bean property otherwise we do nothing
                        result.setMainTitle(this.mainTitle);
                        result.setSeparator(this.separator);
                        result.setSubTitle(this.subTitle);

                        modelAndView.addObject(modelAttributeName, result.toString());
                    }
                }
            }
        }

        super.postHandle(request, response, handler, modelAndView);
    }

    private void resolveClassLevelTitle(SiteTitle siteTitle, SiteTitleContainer result)
    {
        if (!StringUtils.isEmpty(siteTitle.mainTitle()))
        {
            result.setMainTitle(siteTitle.mainTitle());
        }
        else
        {
            result.setMainTitle(this.mainTitle);
        }
    }

    private void resolveClassLevelSeparator(SiteTitle siteTitle, SiteTitleContainer result)
    {
        if (!StringUtils.isEmpty(siteTitle.separator()))
        {
            result.setSeparator(siteTitle.separator());
        }
        else
        {
            result.setSeparator(this.separator);
        }
    }

    private void resolveClassLevelSubTitle(SiteTitle siteTitle, SiteTitleContainer result)
    {
        if (!StringUtils.isEmpty(siteTitle.value()))
        {
            result.setSubTitle(siteTitle.value());
        }
        else
        {
            result.setSubTitle(this.subTitle);
        }
    }

    /**
     * Sets default main title for website title. If annotation is not present,
     * then this value is used.
     *
     * @param mainTitle main title of website. Value is shown before separator.
     */
    public void setMainTitle(String mainTitle)
    {
        this.mainTitle = mainTitle;
    }

    /**
     * Sets separator for website title. If annotation is not present, then this
     * value is used.
     *
     * @param separator value separating main title and subtitle.
     */
    public void setSeparator(String separator)
    {
        this.separator = separator;
    }

    /**
     * Sets subtitle for website title. If annotation is not present, then this
     * value is used.
     *
     * @param subTitle value after separator.
     */
    public void setSubTitle(String subTitle)
    {
        this.subTitle = subTitle;
    }

    /**
     * If annotations are missing on controller and this flag is set to true,
     * then values specified on bean creation are used. If no values are
     * specified then empty strings are set as default.
     *
     * @param composeOnMissingAnnotation flag specifying whether something
     * should be created if annotations are missing
     */
    public void setComposeOnMissingAnnotation(boolean composeOnMissingAnnotation)
    {
        this.composeOnMissingAnnotation = composeOnMissingAnnotation;
    }

    /**
     * Sets variable name under which is website title exposed in ModelAndView.
     * Default value is <b>websiteTitle</b>. If passed attributeName is null or
     * empty default one is used as well.
     *
     * @param modelAttributeName name of model variable
     */
    public void setModelAttributeName(String modelAttributeName)
    {
        if (!StringUtils.isEmpty(modelAttributeName))
        {
            this.modelAttributeName = modelAttributeName;
        }
    }

    /**
     * Sets flag whether i18n resolving should be enabled or not.
     *
     * @param i18nEnabled if true then i18n resolving based from values hold in
     * message source set by {@link #setMessageSource(org.springframework.context.MessageSource)
     * }.
     */
    public void setI18nEnabled(boolean i18nEnabled)
    {
        this.i18nEnabled = i18nEnabled;
    }

    /**
     * Set the MessageSource that this object runs in.
     *
     * @param messageSource message source to be used by this object
     */
    @Override
    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    /**
     * Helper class used for storing temporal result.
     */
    private class SiteTitleContainer
    {

        private String mainTitle;
        private String separator;
        private String subTitle;

        public void setMainTitle(String mainTitle)
        {
            this.mainTitle = mainTitle;
        }

        public void setSeparator(String separator)
        {
            this.separator = separator;
        }

        public void setSubTitle(String subTitle)
        {
            this.subTitle = subTitle;
        }

        /**
         * Method resolves given string value into i18n value. If message source
         * is not empty and <b>i18nEnabled</b> field is set to true then message
         * goes as follows:
         * <ul>
         * <li>we check if such message value exists</li>
         * <li>if there is no such message then <b>NoSuchMessageException</b> is
         * thrown & catched. In this case we return passed value</li>
         * <li>otherwise we return resolved message in given locale</li>
         * </ul>
         *
         * @param value to be resolved
         * @return resolved value if messageSource is not empty, i18n is set to
         * true and the message exists. False otherwise.
         */
        private String resolveString(String value)
        {
            if (i18nEnabled && value != null
                    && messageSource != null)
            {
                String result = null;
                
                // one of spring message source has property called
                // useCodeAsDefaultMessage this i guess suppress the exception
                // throwing and returns the passed value
                try
                {
                    result = messageSource.getMessage(value.substring(1, value.length()-1), null, LocaleContextHolder.getLocale());
                }
                catch (NoSuchMessageException ex)
                {
                    System.err.println(ex);
                    return value;
                }

                return result;
            }
            else
            {
                return value;
            }
        }

        /**
         * Returns true only if input string <b>s</b> starts with <i>{</i> and
         * ends with <i>}</i>.
         *
         * @param s to be checked against condition
         * @return true if <b>s</b> is in form of <code>\{[\w|.]*\}</code>
         */
        private boolean startsEndsWithBrace(String s)
        {
            return s.startsWith("{") && s.endsWith("}");
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();

            if (mainTitle.length() > 0 && startsEndsWithBrace(mainTitle))
            {
                sb.append(resolveString(mainTitle));
            }
            else
            {
                sb.append(mainTitle);
            }

            sb.append(separator);

            if (subTitle.length() > 0 && startsEndsWithBrace(subTitle))
            {
                sb.append(resolveString(subTitle));
            }
            else
            {
                sb.append(subTitle);
            }

            return sb.toString();
        }
    }
}