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

import cz.muni.fi.mir.mathmlcaneval.api.dto.GitBranchDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitRevisionDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitTagDTO;
import cz.muni.fi.mir.mathmlcaneval.services.GitServiceIO;
import cz.muni.fi.mir.mathmlcaneval.services.api.impl.GitServiceImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class GitServiceIOImpl extends GitServiceImpl implements GitServiceIO, InitializingBean, DisposableBean
{
    private static final Logger LOGGER = LogManager.getLogger(GitServiceIOImpl.class);
    private static final Path REPO_FOLDER = Paths.get("C:\\Users\\emptak\\Documents\\NetBeansProjects\\MathMLCan\\.git");
    private Repository repository = null;
    private Git git;

    @Override
    public void pull() throws IOException
    {
        try
        {
            git.pull().call();
        }
        catch (GitAPIException ex)
        {
            LOGGER.error(ex);
        }

    }

    @Override
    public void checkout(GitRevisionDTO revision, GitBranchDTO branch) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void checkout(GitBranchDTO gitBranch) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        repository = new FileRepositoryBuilder().setGitDir(REPO_FOLDER.toFile()).build();
        git = new Git(repository);
    }

    @Override
    public void destroy() throws Exception
    {
        this.repository.close();
        git.close();
    }

    @Override
    public List<GitTagDTO> listTags() throws IOException
    {
        List<GitTagDTO> result = new ArrayList<>();

        try
        {
            for (Ref ref : git.tagList().call())
            {
                GitTagDTO tag = new GitTagDTO();
                tag.setVersion(ref.getName());

                result.add(tag);
            }
        }
        catch (GitAPIException ex)
        {
            throw new IOException(ex);
        }

        return result;
    }

    @Override
    public List<GitBranchDTO> listBranches() throws IOException
    {
        List<GitBranchDTO> result = new ArrayList<>();

        try
        {
            for(Ref ref : git.branchList().call())
            {
                GitBranchDTO branch = new GitBranchDTO();
                branch.setName(ref.getName());
                result.add(branch);
            }
        }
        catch (GitAPIException ex)
        {
            throw new IOException(ex);
        }
        return result;
    }

}
