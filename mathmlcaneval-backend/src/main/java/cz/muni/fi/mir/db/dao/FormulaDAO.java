/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Empt
 */
public interface FormulaDAO
{

    void createFormula(Formula formula);

    void updateFormula(Formula formula);

    void deleteFormula(Formula formula);

    /**
     * Method fetches formula with given id out of database. This method also
     * loads annotations, as they are part of output. By default annotations are
     * set to fetch type lazy. Thus accessing annotations out of session will
     * cause exception.
     *
     * @param id of formula to be obtained
     * @return formula with given ID.
     */
    Formula getFormulaByID(Long id);

    Formula getFormulaByHash(String hash);

    List<Formula> getAllFormulas();

    List<Formula> getAllFormulas(int skip, int number);

    List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument);

    List<Formula> getFormulasByProgram(Program program);

    List<Formula> getFormulasByUser(User user);

    List<Formula> getAllForHashing();

    List<Formula> getFormulasByElements(Collection<Element> collection, int start, int end);

    List<Formula> getAllFormulas(boolean force);

    int getNumberOfRecords();

    Long exists(String hash);
    
    void reindex();
    
    List<Formula> findSimilar(Formula formula,Map<String,String> properties);
    
    void findSimilarMass(Map<String,String> properties);
}
