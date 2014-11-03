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
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationValue;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;

/**
 * TODO Service layer should take DTO objects as input, not domain one. Then DTO
 * objects may implement e.g. DTO interface that requires .getID() method. Once
 * this method is required there is no need for multiple methods here as the
 * second paramter will be add Class&lt;T&gt; type and this will just serve as
 * swtich between output messages.
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class InputChecker
{

    /**
     * Method checks application run for validity.
     *
     * @param applicationRun to be checked
     * @throws IllegalArgumentException if application run is null or does not
     * have set id.
     */
    public static void checkInput(ApplicationRun applicationRun) throws IllegalArgumentException
    {
        if (applicationRun == null)
        {
            throw new IllegalArgumentException("Given application run is null");
        }
        if (applicationRun.getId() == null || Long.valueOf("0").compareTo(applicationRun.getId()) >= 0)
        {
            throw new IllegalArgumentException("Invalid ApplicationRun id should be greater than 0 but was [" + applicationRun.getId() + "]");
        }
    }

    /**
     * Method checks annotation on input.
     *
     * @param annotation to be checked
     * @throws IllegalArgumentException if canonic output is null, or does not
     * have valid id.
     */
    public static void checkInput(Annotation annotation) throws IllegalArgumentException
    {
        if (annotation == null)
        {
            throw new IllegalArgumentException("Given Annotation is null.");
        }
        if (annotation.getId() == null || Long.valueOf("0").compareTo(annotation.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given Annotation does not have valid id should be greater than one but was [" + annotation.getId() + "]");
        }
    }

    /**
     * Method checks given canonic output on input.
     *
     * @param canonicOutput to be checked
     * @throws IllegalArgumentException if canonic output is null, or does not
     * have valid id.
     */
    public static void checkInput(CanonicOutput canonicOutput) throws IllegalArgumentException
    {
        if (canonicOutput == null)
        {
            throw new IllegalArgumentException("Given CanonicOutput is null.");
        }
        if (canonicOutput.getId() == null || Long.valueOf("0").compareTo(canonicOutput.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given CanonicOutput does not have valid id should be greater than one but was [" + canonicOutput.getId() + "]");
        }
    }

    /**
     * Method checks given configuration on input.
     *
     * @param configuration to be checked
     * @throws IllegalArgumentException if configuration is null, or does not
     * have valid id.
     */
    public static void checkInput(Configuration configuration) throws IllegalArgumentException
    {
        if (configuration == null)
        {
            throw new IllegalArgumentException("Given Configuration is null.");
        }
        if (configuration.getId() == null || Long.valueOf("0").compareTo(configuration.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given Configuration does not have valid id should be greater than one but was [" + configuration.getId() + "]");
        }
    }

    /**
     * Method validates input
     *
     * @param program to be checked
     * @throws IllegalArgumentException if program is null or does not have
     * valid id.
     */
    public static void checkInput(Program program) throws IllegalArgumentException
    {
        if (program == null)
        {
            throw new IllegalArgumentException("Given Program is null.");
        }
        if (program.getId() == null || Long.valueOf("0").compareTo(program.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given Program does not have valid id should be greater than one but was [" + program.getId() + "]");
        }
    }

    /**
     * Method validates input
     *
     * @param revision to be checked
     * @throws IllegalArgumentException if revision is null or does not have
     * valid id.
     */
    public static void checkInput(Revision revision) throws IllegalArgumentException
    {
        if (revision == null)
        {
            throw new IllegalArgumentException("Given Revision is null.");
        }
        if (revision.getId() == null || Long.valueOf("0").compareTo(revision.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given Revision does not have valid id should be greater than one but was [" + revision.getId() + "]");
        }
    }

    /**
     * Method validates input
     *
     * @param sourceDocument to be checked
     * @throws IllegalArgumentException if sourceDocument is null or does not have
     * valid id.
     */
    public static void checkInput(SourceDocument sourceDocument) throws IllegalArgumentException
    {
        if (sourceDocument == null)
        {
            throw new IllegalArgumentException("Given SourceDocument is null.");
        }
        if (sourceDocument.getId() == null || Long.valueOf("0").compareTo(sourceDocument.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given SourceDocument does not have valid id should be greater than one but was [" + sourceDocument.getId() + "]");
        }
    }    

    /**
     * Method validates input
     *
     * @param userRole to be checked
     * @throws IllegalArgumentException if userrole is null or does not have
     * valid id.
     */
    public static void checkInput(UserRole userRole) throws IllegalArgumentException
    {
        if (userRole == null)
        {
            throw new IllegalArgumentException("Given UserRole is null.");
        }
        if (userRole.getId() == null || Long.valueOf("0").compareTo(userRole.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given UserRole does not have valid id should be greater than one but was [" + userRole.getId() + "]");
        }
    }
    
    /**
     * Method validates input
     *
     * @param user to be checked
     * @throws IllegalArgumentException if user is null or does not have
     * valid id.
     */
    public static void checkInput(User user) throws IllegalArgumentException
    {
        if (user == null)
        {
            throw new IllegalArgumentException("Given User is null.");
        }
        if (user.getId() == null || Long.valueOf("0").compareTo(user.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given User does not have valid id should be greater than one but was [" + user.getId() + "]");
        }
    }
    
    /**
     * Method validates input
     *
     * @param formula to be checked
     * @throws IllegalArgumentException if formula is null or does not have
     * valid id.
     */
    public static void checkInput(Formula formula) throws IllegalArgumentException
    {
        if (formula == null)
        {
            throw new IllegalArgumentException("Given Formula is null.");
        }
        if (formula.getId() == null || Long.valueOf("0").compareTo(formula.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given Formula does not have valid id should be greater than one but was [" + formula.getId() + "]");
        }
    }
    
    /**
     * Method checks pagination.
     * @param pagination to be checked
     * @throws IllegalArgumentException if pagination is null 
     */
    public static void checkInput(Pagination pagination) throws IllegalArgumentException
    {
        if(pagination == null)
        {
            throw new IllegalArgumentException("Given pagination is null.");
        }
    }
    
    /**
     * Method validates input
     * @param annotationValue to be checked
     * @throws IllegalArgumentException if value is null, or id is null or less than one
     */
    public static void checkInput(AnnotationValue annotationValue) throws IllegalArgumentException
    {
        if(annotationValue == null)
        {
            throw new IllegalArgumentException("Given annotation value is null");
        }
        if (annotationValue.getId() == null || Long.valueOf("0").compareTo(annotationValue.getId()) >= 0)
        {
            throw new IllegalArgumentException("Given annotation value does not have valid id should be greater than one but was [" + annotationValue.getId() + "]");
        }
    }
}
