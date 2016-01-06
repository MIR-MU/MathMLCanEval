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
package cz.muni.fi.mir.mathmlcaneval.services.api.impl;

import cz.muni.fi.mir.mathmlcaneval.api.GitService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class GitServiceImpl implements GitService, InitializingBean
{
    private static final Path REPO_FOLDER = Paths.get("C:\\Users\\emptak\\Documents\\NetBeansProjects\\MathMLCan\\.git");
    private Repository repository = null;
    

    @Override
    public List<String> getBranches()
    {
        List<String> branches = new ArrayList<>();
        
        Map<String,Ref> map = repository.getAllRefs();
        
        branches.addAll(map.keySet());
        
        return branches;
    }

    @Override
    public List<String> getRevisions(String branch)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getTags(String branch)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        repository = new FileRepositoryBuilder().setGitDir(REPO_FOLDER.toFile()).build();
    }
    
}
