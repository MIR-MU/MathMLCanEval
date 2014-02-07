/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
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
    
    List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument);
    List<Formula> getFormulasByProgram(Program program);
    List<Formula> getFormulasByUser(User user);
    List<Formula> getFormulasBySimilarOutput(CanonicOutput canonicOutput);
}
