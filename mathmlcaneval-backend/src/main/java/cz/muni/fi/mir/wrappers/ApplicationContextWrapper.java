/* 
 * Copyright 2014 MIR@MU.
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
package cz.muni.fi.mir.wrappers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class used as Wrapper for Application context. It also provides several methods
 * upon this AppCtx.
 * 
 * @author Dominik Szalai <a href="mailto:emptulik&#64;gmail.com">emptulik&#64;gmail.com</a>
 */
public class ApplicationContextWrapper implements ApplicationContextAware
{
    private static ApplicationContext appContext;
    private static ApplicationContextWrapper instance;  
    
    
    private ApplicationContextWrapper()
    {
        setApplicationContext(new ClassPathXmlApplicationContext("spring/applicationContext.xml"));
    }
    
    
    /**
     * Returns unique instance of this wrapper. Check singleton design pattern for
     * more information.
     * @return Instance of this class.
     */
    public static ApplicationContextWrapper getInstance()
    {
        if(instance == null)
        {
            instance = new ApplicationContextWrapper();
        }
        
        return instance;
    }
    
    /**
     * Method sets AppCtx for this class.
     * @param applicationContext to be set
     * @throws BeansException if any Spring related error occurs
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException 
    {        
        appContext = applicationContext;
    }
    
    /**
     * Method returns application context
     * @return AppCtx
     */
    public ApplicationContext getApplicationContext()
    {
        return appContext;
    }
    
    /**
     * Method returns bean with given Name.
     * @param <T> Return type of method
     * @param beanName name of bean (service,dao,etc...)
     * @return Bean with given name
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName)
    {
        return (T) appContext.getBean(beanName);
    }
}
