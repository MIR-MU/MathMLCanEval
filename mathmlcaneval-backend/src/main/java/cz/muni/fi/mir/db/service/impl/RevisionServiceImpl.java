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
 * @author Empt
 */
@Service(value = "revisionService")
public class RevisionServiceImpl implements RevisionService
{
    @Autowired private RevisionDAO revisionDAO;

    @Override
    @Transactional(readOnly = false)
    public void createRevision(Revision revision)
    {
        revisionDAO.createRevision(revision);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteRevision(Revision revision)
    {
        revisionDAO.deleteRevision(revision);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateRevision(Revision revision)
    {
        revisionDAO.updateRevision(revision);
    }

    @Override
    @Transactional(readOnly = true)
    public Revision getRevisionByID(Long id)
    {
        return revisionDAO.getRevisionByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Revision getRevisionByHash(String hash)
    {
        return revisionDAO.getRevisionByHash(hash);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revision> getAllRevisions()
    {
        return revisionDAO.getAllRevisions();
    }    

    @Override
    @Transactional(readOnly = true)
    public List<Revision> findRevisionByNote(String note)
    {
        return revisionDAO.findRevisionByNote(note);
    }
}
