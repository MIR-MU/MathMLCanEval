/*
 * Copyright 2016 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.services.impl;

import cz.muni.fi.mir.mathmlcaneval.services.api.impl.GitServiceImpl;
import cz.muni.fi.mir.mathmlcaneval.api.GitService;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class NewMain
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        GitService gitService = new GitServiceImpl();
        InitializingBean bean = (InitializingBean) gitService;
        bean.afterPropertiesSet();
        
        System.out.println(gitService.getBranches());
        
        MavenServiceImpl mvn = new MavenServiceImpl();
        mvn.buildJar(null, null);
    }
    
}
