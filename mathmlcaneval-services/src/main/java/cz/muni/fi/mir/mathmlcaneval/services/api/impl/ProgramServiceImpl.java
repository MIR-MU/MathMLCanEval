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

import cz.muni.fi.mir.mathmlcaneval.api.ProgramService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.ProgramDTO;
import cz.muni.fi.mir.mathmlcaneval.database.ProgramDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.Program;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import org.apache.commons.lang3.StringUtils;
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
public class ProgramServiceImpl implements ProgramService
{
    @Autowired
    private ProgramDAO programDAO;
    @Autowired
    private Mapper mapper;

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void createProgram(ProgramDTO program) throws IllegalArgumentException
    {
        checkNull(program);
        checkFields(program);
        Program p = mapper.map(program, Program.class);
        programDAO.create(p);
        program.setId(p.getId());
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void deleteProgram(ProgramDTO program) throws IllegalArgumentException
    {
        checkNull(program);
        checkID(program);
        programDAO.delete(program.getId());
        program.setId(null);
    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_STAFF")
    public void updateProgram(ProgramDTO program) throws IllegalArgumentException
    {
        checkNull(program);
        checkID(program);
        checkFields(program);
        programDAO.update(mapper.map(program, Program.class));
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = "ROLE_USER")
    public ProgramDTO getProgramByID(Long id) throws IllegalArgumentException
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Invalid ID. Should not be null.");
        }
        return mapper.map(programDAO.getById(id), ProgramDTO.class);

    }

    @Override
    @Transactional(readOnly = false)
    @Secured(value = "ROLE_USER")
    public List<ProgramDTO> getAll()
    {
        return mapper.mapList(programDAO.getAll(), ProgramDTO.class);
    }

    private void checkNull(ProgramDTO program) throws IllegalArgumentException
    {
        if (program == null)
        {
            throw new IllegalArgumentException("Given program is null.");
        }
    }

    private void checkID(ProgramDTO program) throws IllegalArgumentException
    {
        if (program.getId() == null)
        {
            throw new IllegalArgumentException("Given program does not have valid id.");
        }
    }

    private void checkFields(ProgramDTO program) throws IllegalArgumentException
    {
        if (StringUtils.isEmpty(program.getName()))
        {
            throw new IllegalArgumentException("Given program has no valid name.");
        }
    }
}
