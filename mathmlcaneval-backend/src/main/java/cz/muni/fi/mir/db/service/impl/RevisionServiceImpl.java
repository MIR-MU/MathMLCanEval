/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.RevisionDAO;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.service.RevisionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "revisionService")
public class RevisionServiceImpl implements RevisionService
{
    @Autowired private RevisionDAO revisionDAO;

    @Override
    @Transactional(readOnly = false)
    public void createRevision(Revision revision)
    {
        revisionDAO.create(revision);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteRevision(Revision revision)
    {
        revisionDAO.delete(revision.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void updateRevision(Revision revision)
    {
        revisionDAO.update(revision);
    }

    @Override
    @Transactional(readOnly = true)
    public Revision getRevisionByID(Long id)
    {
        return revisionDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revision> getAllRevisions()
    {
        return revisionDAO.getAllRevisions();
    }  
}
