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
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitBranchDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitRevisionDTO;
import cz.muni.fi.mir.mathmlcaneval.database.GitBranchDAO;
import cz.muni.fi.mir.mathmlcaneval.database.GitRevisionDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.GitBranch;
import cz.muni.fi.mir.mathmlcaneval.database.domain.GitRevision;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import cz.muni.fi.mir.mathmlcaneval.services.MavenService;
import cz.muni.fi.mir.mathmlcaneval.services.WorkingDirectoryLocker;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class GitServiceImpl implements GitService, InitializingBean, DisposableBean
{
    private static final Logger LOGGER = LogManager.getLogger(GitServiceImpl.class);
    @Autowired
    private GitRevisionDAO gitRevisionDAO;
    @Autowired
    private GitBranchDAO gitBranchDAO;
    @Autowired
    private WorkingDirectoryLocker gitOperationProgress;
    @Autowired
    private Environment environment;
    @Autowired
    private Mapper mapper;
    private Path repoFolder;
    private Repository repository = null;
    private Git git = null;

    @Autowired
    private MavenService mavenService;

    @Override
    @Transactional(readOnly = true)
    public void createRevision(GitRevisionDTO gitRevision) throws IllegalArgumentException
    {
        GitRevision revision = mapper.map(gitRevision, GitRevision.class);
        gitRevisionDAO.create(revision);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GitRevisionDTO> getRevisions()
    {
        return mapper.mapList(gitRevisionDAO.getAll(), GitRevisionDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GitBranchDTO> getBranches() throws IOException
    {
        List<GitBranchDTO> result = new ArrayList<>();
        for (GitBranch gb : gitBranchDAO.getAll())
        {
            GitBranchDTO dto = mapper.map(gb, GitBranchDTO.class);
            if (dto.getName().equals(getCurrentBranch().getName()))
            {
                dto.setActive(true);
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    public void checkout(GitBranchDTO gitBranch) throws IOException
    {
        GitBranch dbBranch = gitBranchDAO.getBranchByName(gitBranch.getName());

        //TODO
        // fetch branches from database
        // check branches on filesystem
        // if branch exist on filesysystem && exists in database
        // do checkout
        // if branch does not exist in database create and do checkout
        // throw exception because branch does not exist
        boolean isLocal = false;

        try
        {
            for (Ref ref : listLocalBranches())
            {
                LOGGER.info("Going thru branch {}", ref.getName());
                if (ref.getName().startsWith("refs/heads/" + gitBranch.getName()))
                {
                    LOGGER.info("Requested branch is local");
                    isLocal = true;

                    break;
                }
            }
            if (!isLocal)
            {
                LOGGER.info("Request branch is remote");
                Ref ref = git.checkout().setCreateBranch(true).setName(gitBranch.getName())
                        .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                        .setStartPoint("origin/" + gitBranch.getName())
                        .call();
            }
            else
            {
                LOGGER.info("Checking out local branch");
                git.checkout().setName(gitBranch.getName()).call();
            }

        }
        catch (GitAPIException ex)
        {
            throw new IOException(ex);
        }
    }

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
    @Transactional(readOnly = false)
    public void synchronize() throws IOException
    {
        List<GitBranch> persistedBranches = gitBranchDAO.getAll();
        GitBranchDTO currentBranchDTO = getCurrentBranch();
        Collection<Ref> branches = null;

        try
        {
            branches = listAllBranches();
        }
        catch (GitAPIException ex)
        {
            LOGGER.fatal(ex);
        }
        Map<String, Boolean> remotes = new HashMap<>();
        List<String> localBranches = new ArrayList<>();

        for (Ref ref : branches)
        {
            if (ref.getName().startsWith("refs/heads/"))
            {
                localBranches.add(StringUtils.substringAfter(ref.getName(), "refs/heads/"));
                LOGGER.debug("Branch {} added to local branches.", ref.getName());
            }
            else if (ref.getName().startsWith("refs/remotes/origin/") && !ref.getName().endsWith("HEAD"))
            {
                remotes.put(StringUtils.substringAfter(ref.getName(), "refs/remotes/origin/"), Boolean.FALSE);
                LOGGER.debug("Branch {} added to remotes.", ref.getName());
            }
            else
            {
                LOGGER.debug("Ref is head {}", ref.getName());
            }
        }

        for (String local : localBranches)
        {
            if (remotes.containsKey(local))
            {
                remotes.put(local, Boolean.TRUE);
            }
        }

        for (Map.Entry<String, Boolean> entry : remotes.entrySet())
        {
            if (entry.getValue().equals(Boolean.FALSE))
            {
                GitBranchDTO branchDTO = new GitBranchDTO();
                branchDTO.setName(entry.getKey());
                try
                {
                    remoteCheckout(branchDTO);
                    LOGGER.info("Checkouting following branch {} because its not local.", branchDTO.getName());
                    localBranches.add(branchDTO.getName());
                }
                catch (GitAPIException ex)
                {
                    LOGGER.error(ex);
                }
            }
        }

        Map<String, Boolean> persisted = new HashMap<>();

        for (String local : localBranches)
        {
            persisted.put(local, Boolean.FALSE);
        }

        for (GitBranch branch : persistedBranches)
        {
            if (persisted.containsKey(branch.getName()))
            {
                persisted.put(branch.getName(), Boolean.TRUE);
            }
        }

        for (Map.Entry<String, Boolean> entry : persisted.entrySet())
        {
            if (!entry.getValue())
            {
                GitBranch newBranch = new GitBranch();
                newBranch.setName(entry.getKey());
                gitBranchDAO.create(newBranch);
                LOGGER.debug("{} persisted.", newBranch.getName());
            }
        }

        checkout(currentBranchDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GitRevisionDTO> listRevisions(GitBranchDTO gitBranch) throws IOException
    {
        List<GitRevisionDTO> result = new ArrayList<>(15);
        if (getCurrentBranch().equals(gitBranch))
        {
            try
            {
                for (RevCommit commit : git.log().setMaxCount(15).call())
                {
                    GitRevisionDTO rev = new GitRevisionDTO();
                    rev.setRevisionHash(commit.getName());
                    result.add(rev);
                }
            }
            catch (GitAPIException ex)
            {
                throw new IOException(ex);
            }
        }
        else
        {
            //todo
        }

        return result;
    }

    //http://stackoverflow.com/a/16327910/1203690
    private Collection<Ref> listLocalBranches() throws GitAPIException
    {
        Collection<Ref> toFilter = listAllBranches();
        toFilter.removeAll(listRemoteBranches());
        return toFilter;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        System.err.println(environment.getProperty("services.git.folder"));
        repoFolder = Paths.get(environment.getProperty("services.git.folder"));
        repository = new FileRepositoryBuilder().setGitDir(repoFolder.toFile()).build();
        git = new Git(repository);
    }

    @Override
    public void destroy() throws Exception
    {
        git.close();
    }

    private void remoteCheckout(GitBranchDTO gitBranch) throws GitAPIException
    {
        git.checkout().setCreateBranch(true).setName(gitBranch.getName())
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                .setStartPoint("origin/" + gitBranch.getName())
                .call();
    }

    private Collection<Ref> listRemoteBranches() throws GitAPIException
    {
        return listBranches(ListBranchCommand.ListMode.REMOTE);
    }

    private Collection<Ref> listAllBranches() throws GitAPIException
    {
        return listBranches(ListBranchCommand.ListMode.ALL);
    }

    private Collection<Ref> listBranches(ListBranchCommand.ListMode listModes) throws GitAPIException
    {
        return git.branchList().setListMode(listModes).call();
    }

    @Override
    @Transactional(readOnly = true)
    public GitBranchDTO getCurrentBranch() throws IOException
    {
        String branchName = git.getRepository().getBranch();

//        GitBranch gitBranch = gitBranchDAO.getBranchByName(branchName);
        GitBranchDTO dto = mapper.map(gitBranchDAO.getBranchByName(branchName),GitBranchDTO.class);
        if(dto == null)
        {
            if(!StringUtils.isEmpty(branchName))
            {
                GitBranch branch = new GitBranch();
                branch.setName(branchName);
                gitBranchDAO.create(branch);
                
                dto = mapper.map(gitBranchDAO.getBranchByName(branchName),GitBranchDTO.class);
            }
        }
        
        if(dto == null)
        {
            throw new RuntimeException("Unable to get current branch.");
        }
        dto.setActive(true);
        return dto;
    }
}
