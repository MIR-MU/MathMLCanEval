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
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitTagDTO;
import cz.muni.fi.mir.mathmlcaneval.database.GitBranchDAO;
import cz.muni.fi.mir.mathmlcaneval.database.GitRevisionDAO;
import cz.muni.fi.mir.mathmlcaneval.database.GitTagDAO;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class GitServiceImpl implements GitService
{
    private static final Logger LOGGER = LogManager.getLogger(GitServiceImpl.class);
    @Autowired
    private GitRevisionDAO gitRevisionDAO;
    @Autowired
    private GitBranchDAO gitBranchDAO;
    @Autowired
    private GitTagDAO gitTagDAO;
    @Autowired
    private Mapper mapper;
    
    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public List<GitBranchDTO> getBranches()
    {
        return mapper.mapList(gitBranchDAO.getAll(), GitBranchDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_STAFF")
    public List<GitRevisionDTO> getRevisions(GitBranchDTO gitBranchDTO)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_STAFF")
    public List<GitTagDTO> getTags()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void createRevision(GitRevisionDTO gitRevision) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void deleteRevision(GitRevisionDTO gitRevision) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void createBranch(GitBranchDTO gitBranch) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void deleteBranch(GitBranchDTO gitBranch) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void createTag(GitTagDTO gitTag) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
