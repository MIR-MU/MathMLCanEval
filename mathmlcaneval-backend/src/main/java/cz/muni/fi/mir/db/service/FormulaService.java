/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import java.util.List;

/**
 *
 * @author Dominik Szalai
 */
public interface FormulaService
{
    void createFormula(Formula formula);
    void updateFormula(Formula formula);
    void deleteFormula(Formula formula);

    Formula getFormulaByID(Long id);

    List<Formula> getAllFormulas();
    List<Formula> getAllFormulas(int skip, int number);

    List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument);
    List<Formula> getFormulasByProgram(Program program);
    List<Formula> getFormulasByUser(User user);
}
