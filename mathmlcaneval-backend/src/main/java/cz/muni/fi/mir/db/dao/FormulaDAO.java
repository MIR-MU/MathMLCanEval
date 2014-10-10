/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.FormulaSearchResponse;
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

    /**
     * Method deletes given formula from database.
     *
     * @param formula to be deleted
     */
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

    Formula getFormulaByAnnotation(Annotation annotation);

    List<Formula> getAllForHashing();

    List<Formula> getFormulasByElements(Collection<Element> collection, int start, int end);

    List<Formula> getAllFormulas(boolean force);

    int getNumberOfRecords();

    Long exists(String hash);

    void reindex();

    List<Formula> findSimilar(Formula formula, Map<String, String> properties, boolean override, boolean directWrite);

    void findSimilarMass(Map<String, String> properties);

    FormulaSearchResponse findFormulas(FormulaSearchRequest formulaSearchRequest);

    /**
     * Method returns formula of which is given canonicOutput descendant.
     * @param canonicOutput which parent we are looking for
     * @return formula containing given canonic output. null if there is no match (which should not occur)
     */
    Formula getFormulaByCanonicOutput(CanonicOutput canonicOutput);

    /**
     * Method manually indexes given formula. For indexing entire database use {@link #reindex()
     * }. Writing to index occurs only after transaction is commit, therefore TX
     * has to be managed on upper level.
     *
     * @param f to be indexed.
     */
    void index(Formula f);
}
