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

import cz.muni.fi.mir.mathmlcaneval.api.SourceService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.SourceDTO;
import cz.muni.fi.mir.mathmlcaneval.database.SourceDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.Source;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class SourceServiceImpl implements SourceService
{
    private static final Logger LOGGER = LogManager.getLogger(SourceServiceImpl.class);
    @Autowired
    private SourceDAO sourceDAO;
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void createSource(SourceDTO source) throws IllegalArgumentException
    {
        checkNull(source);
        checkFields(source);
        Source s = mapper.map(source, Source.class);
        sourceDAO.create(s);
        source.setId(s.getId());
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void deleteSource(SourceDTO source) throws IllegalArgumentException
    {
        checkNull(source);
        checkID(source);
        sourceDAO.delete(source.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_STAFF")
    public List<SourceDTO> getAll()
    {
        return mapper.mapList(sourceDAO.getAll(), SourceDTO.class);
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public SourceDTO getByID(Long id) throws IllegalArgumentException
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Given is is invalid.");
        }

        return mapper.map(sourceDAO.getById(id),SourceDTO.class);
    }

    private void checkNull(SourceDTO source) throws IllegalArgumentException
    {
        if (source == null)
        {
            throw new IllegalArgumentException("Given source is null.");
        }
    }

    private void checkID(SourceDTO source) throws IllegalArgumentException
    {
        if (source.getId() == null)
        {
            throw new IllegalArgumentException("Given source has no valid ID.");
        }
    }

    private void checkFields(SourceDTO source) throws IllegalArgumentException
    {
        if (StringUtils.isEmpty(source.getName()))
        {
            throw new IllegalArgumentException("Given source has invalid name.");
        }
        if (source.getRootPath() == null)
        { // TODO: check even if we can access path && exists
            throw new IllegalArgumentException("Given source has invalid path.");
        }
    }

}
