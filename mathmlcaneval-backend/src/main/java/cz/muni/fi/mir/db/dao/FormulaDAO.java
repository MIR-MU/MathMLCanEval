/* 
 * Copyright 2014 MIR@MU.
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
package cz.muni.fi.mir.db.dao;

import java.util.List;
import java.util.Map;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SearchResponse;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
public interface FormulaDAO extends GenericDAO<Formula, Long>
{

    /**
     * Method deletes given formula from database. Method has also ensure that
     * all existing relations between this formula are removed aswell.
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

    /**
     * Method fetches formula from database based on given Hash. Since hash is
     * unique as ID, there will never be 2 formulas with the same hash. Only if
     * collision occurs.
     *
     * @param hash of formula to be obtained.
     * @return formula with given hash, null if there is no match
     */
    Formula getFormulaByHash(String hash);

    /**
     * Method obtains filtered list of formulas based on current position in
     * pagination.
     *
     * @param pagination position holding from, to based on which is result
     * altered.
     * @return list of formulas from given page.
     */
    List<Formula> getAllFormulas(Pagination pagination);

    /**
     * Method obtains formula, that has given annotation in relation.
     *
     * @param annotation of formula
     * @return formula with given annotation, null if there is no match.
     */
    Formula getFormulaByAnnotation(Annotation annotation);

    /**
     * Method returns number of formulas in system.
     *
     * @return number of formulas in system
     */
    int getNumberOfRecords();

    /**
     * Method checks if formula with given hash exists
     *
     * @param hash of formulas
     * @return ID of formula with given hash, null if there is no match
     */
    Long exists(String hash);

    /**
     * Method attempts to find similar formulas based on given input parameters
     * (specified in <b>properties</b>). Similarity is always calculated from
     * <b>LAST</b> canonic output.
     *
     * @param formula source formula which holds the canonic outputs
     * @param properties holding criteria for similarity match
     * @param override whether old similar formulas should be remembered
     * @param directWrite whether found similar forms are written without user
     * prompt.
     * @param pagination current page of similarity result.
     * @return response based on given parameters
     */
    SearchResponse<Formula> findSimilar(Formula formula, Map<String, String> properties, boolean override, boolean directWrite, Pagination pagination);

    /**
     * Search method used to obtained filtered result from entire set of
     * formulas.
     *
     * @param formulaSearchRequest request containing values on which result
     * will be filtered
     * @param pagination current step of search
     * @return container holding result of request
     */
    SearchResponse<Formula> findFormulas(FormulaSearchRequest formulaSearchRequest, Pagination pagination);

    /**
     * Method returns formula of which is given canonicOutput descendant.
     *
     * @param canonicOutput which parent we are looking for
     * @return formula containing given canonic output. null if there is no
     * match (which should not occur)
     */
    Formula getFormulaByCanonicOutput(CanonicOutput canonicOutput);    

    /**
     * Method obtains list of formulas belonging to given source document.
     *
     * @param sourceDocument desired target source
     * @return list of formulas from same source, empty list if there are none.
     */
    List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument);

    /**
     * Method obtains list of formulas belonging to given program.
     *
     * @param program program under which formulas were made.
     * @return list of formulas from same program, empty list if there are none.
     */
    List<Formula> getFormulasByProgram(Program program);

    /**
     * Method obtains list of formulas imported by specific user.
     *
     * @param user father of formulas
     * @return list of formulas from given user, empty list if there are none.
     */
    List<Formula> getFormulasByUser(User user);

    /**
     * Method fetches formulas whose canonic output has given hash. List may
     * contain same formula multiple times.
     *
     * @param hash of canonic output
     * @return list of of formulas whose co has input hash
     */
    List<Formula> getFormulasByCanonicOutputHash(String hash);
}
