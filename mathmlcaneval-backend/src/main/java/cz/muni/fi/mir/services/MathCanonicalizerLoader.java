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
package cz.muni.fi.mir.services;

import java.io.FileNotFoundException;
import java.util.List;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Formula;

/**
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public interface MathCanonicalizerLoader
{

    /**
     * Method used for running given formula in given applicationRun. The execution of method is following:
     * <ul>
     * <li>we load jar file as URL</li>
     * <li>we create new URL Class loader and append file to it</li>
     * <li>we load main class from jar file which was specified when creating instance of this class</li>
     * <li>we obtain main method</li>
     * <li>we pass formula and configuration as arguments to main method</li>
     * <li>we invoke method</li>
     * <li>we collect result and create canonic output which is stored into formula</li>
     * <li>we delete temp files</li>
     * </ul>
     * @param formulas
     * @param applicationRun wrapper containing some information required for run
     */
    //void execute(Formula formula, ApplicationRun applicationRun);
    
    
    void execute(List<Formula> formulas, ApplicationRun applicationRun);
    
    
     /**
     * Method checks whether file at given path exists. If file does not exist 
     * exception is thrown instead of returning false value. The reason is that 
     * we can later show message with missing path (part of exception message)
     * in page view.
     * @return true if jar file exists
     * @throws FileNotFoundException if file does not exist
     */
    boolean jarFileExists() throws FileNotFoundException;
}
