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

/**
 *
 * @author Empt
 */
public interface FormulaDAO
{
    void createFormula(Formula formula);
    void updateFormula(Formula formula);
    void deleteFormula(Formula formula);

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
}
