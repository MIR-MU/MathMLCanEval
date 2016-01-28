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

import cz.muni.fi.mir.mathmlcaneval.services.MavenService;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class MavenServiceImpl implements MavenService
{
    // locking system
    @Override
    public void buildJar()
    {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(Paths.get("C:\\Users\\emptak\\Documents\\NetBeansProjects\\MathMLCan\\pom.xml").toFile());
        request.setGoals(Arrays.asList("clean","package"));
        
        Invoker invoker = new DefaultInvoker();
        try
        {
            invoker.execute(request);
        }
        catch (MavenInvocationException ex)
        {
            Logger.getLogger(MavenServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
