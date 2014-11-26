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
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.audit.FormulaAuditor;

import java.util.List;
import java.util.Map;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SearchResponse;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
public interface FormulaService
{
    // following values are used for property map in similar form
    // searches

    //TODO jdoc
    public static final String USE_DISTANCE = "useDistance";
    public static final String USE_COUNT = "useCount";
    public static final String USE_OVERRIDE = "useOverride";

    public static final String CONDITION_DISTANCE = "distanceCondition";
    public static final String CONDITION_COUNT = "countCondition";

    public static final String VALUE_DISTANCEMETHOD = "distanceMethodValue";
    public static final String VALUE_COUNTELEMENTMETHOD = "countElementMethodValue";
    public static final String DIRECT_WRITE = "directWrite";

    /**
     * Method creates given formula inside database.
     *
     * @param formula to be created.
     * @throws IllegalArgumentException if formula is null.
     */
    void createFormula(Formula formula) throws IllegalArgumentException;

    /**
     * Method updates formula inside database.
     *
     * @param formula to be updated
     * @throws IllegalArgumentException if formula does not have set it's ID, or
     * formula is null.
     */
    void updateFormula(Formula formula) throws IllegalArgumentException;

    /**
     * Method deletes formula from database.
     *
     * @param formula to be deleted
     * @throws IllegalArgumentException if formula is null, or does not have
     * valid ID (null or less than 1).
     */
    void deleteFormula(Formula formula) throws IllegalArgumentException;

    /**
     * Method executes mass import upon given path with specified method
     * arguments. First we create ApplicationRun out of revision and
     * configuration and then path with filter to {@link FileDirectoryService#exploreDirectory(java.lang.String, java.lang.String)
     * } is passed which extracts all XML based on input. When all files are
     * read we store prepared ApplicationRun inside database. For each extracted
     * formula hash is calculated if it is not found already inside database, we
     * store it. After all formulas are persisted inside database we execute
     * null null     {@link MathCanonicalizerLoader#execute(java.util.List, cz.muni.fi.mir.db.domain.ApplicationRun)
     * } which executes canonicalization of all extracted formulas. If all
     * formulas we are trying to import are already inside database notification
     * with level INFO is logged. Method creates ApplicationRun out of revision
     * and configuration and then calls null null     {@link #massFormulaImport(java.lang.String, java.lang.String, cz.muni.fi.mir.db.domain.Revision, cz.muni.fi.mir.db.domain.Configuration, cz.muni.fi.mir.db.domain.Program, cz.muni.fi.mir.db.domain.SourceDocument, cz.muni.fi.mir.db.domain.User) 
     * }. Because method is annotated by @Async security context is not
     * propagated into any newly created sub threads we need to pass logged user
     * (Obtained one via SecurityContext fascade would be null)
     *
     * @param path root directory of mass import.
     * @param filter regular expression against which files will be matched.
     * E.g. <b>*.xml</b>
     * @param revision revision of canonicalizer.
     * @param configuration configuration for canonicalizer.
     * @param program program by which formula was created.
     * @param sourceDocument source document of formula..
     * @param user that started the import
     * @throws IllegalArgumentException if any of parameters is empty, null or
     * does not have valid id.
     */
    void massFormulaImport(String path, String filter, Revision revision,
            Configuration configuration, Program program,
            SourceDocument sourceDocument, User user) throws IllegalArgumentException;

    /**
     * Method does the same as {@link #massFormulaImport(java.lang.String, java.lang.String, cz.muni.fi.mir.db.domain.Revision, cz.muni.fi.mir.db.domain.Configuration, cz.muni.fi.mir.db.domain.Program, cz.muni.fi.mir.db.domain.SourceDocument)
     * }. Instead of folder import we import specified String representing
     * formula.
     *
     * @param formulaXmlContent String representation of formula
     * @param revision revision of canonicalizer.
     * @param configuration configuration for canonicalizer.
     * @param program program by which formula was created.
     * @param sourceDocument source document of formula..
     * @param user user that started the import
     * @throws IllegalArgumentException if any of parameters is empty, null or
     * does not have valid id.
     */
    void simpleFormulaImport(String formulaXmlContent, Revision revision,
            Configuration configuration, Program program,
            SourceDocument sourceDocument, User user) throws IllegalArgumentException;

    /**
     * Method fetches Formula out of database based on given ID.
     *
     * @param id of formula to be fetched
     * @return Formula with given ID, null if there is no match
     * @throws IllegalArgumentException if ID is null, or less than 1.
     */
    Formula getFormulaByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains filtered list of formulas based on current position in
     * pagination.
     *
     * @param pagination position holding from, to based on which is result
     * altered.
     * @return list of formulas from given page.
     * @throws IllegalArgumentException if pagination is null
     */
    List<Formula> getAllFormulas(Pagination pagination) throws IllegalArgumentException;

    /**
     * Method obtains filtered list of formulas based on current position in
     * pagination.
     *
     * @return list of formulas from given page.
     * @throws IllegalArgumentException if pagination is null
     */
    List<Formula> getAllFormulas() throws IllegalArgumentException;

    /**
     * Method obtains formula, that has given annotation in relation.
     *
     * @param annotation of formula
     * @return formula with given annotation, null if there is no match.
     * @throws IllegalArgumentException if annotation is null or does not have
     * set valid id.
     */
    Formula getFormulaByAnnotation(Annotation annotation) throws IllegalArgumentException;

    /**
     * Method returns total number of formulas inside database.
     *
     * @return number of formulas.
     */
    int getNumberOfRecords();

    /**
     * Method reindexes database into index.
     */
    void reindexAndOptimize();

    /**
     * Method attempts to find similar formulas based on given input parameters
     * (specified in <b>properties</b>). Similarity is always calculated from
     * <b>LAST</b> canonic output.
     *
     * @param formula source formula which holds the canonic outputs
     * @param properties holding criteria for similarity match
     * @param override whether old similar formulas should be remembered
     * @param crosslink whether to crosslink similar formulas with each other
     * @param directWrite whether found similar forms are written without user
     * prompt.
     * @param pagination current page of similarity result.
     * @return response based on given parameters
     * @throws IllegalArgumentException if any of input is null, empty, or does
     * not have valid id
     */
    SearchResponse<Formula> findSimilar(Formula formula, Map<String, String> properties, boolean override, boolean crosslink, boolean directWrite, Pagination pagination) throws IllegalArgumentException;

    /**
     * TODO refactors similarIDs to list of formulas. Method attaches to given
     * formula formulas specified by array of IDs.
     *
     * @param formula parent for similar attach
     * @param similarIDs array of formulas selected as similar
     * @param override direct override without user prompt
     * @throws IllegalArgumentException if formula is null, does not have id or
     * similarIDs array is empty
     */
    void attachSimilarFormulas(Formula formula, Long[] similarIDs, boolean override) throws IllegalArgumentException;

    /**
     * Method deletes list of given formulas.
     *
     * @param toBeRemoved formulas to be deleted
     * @throws IllegalArgumentException if list is null or empty
     */
    void massRemove(List<Formula> toBeRemoved) throws IllegalArgumentException;

    /**
     * Method adds given annotation to formula. Method is tracked from {@link FormulaAuditor#arroundCreateAnnotation(cz.muni.fi.mir.db.domain.Formula, cz.muni.fi.mir.db.domain.Annotation)
     * }
     *
     * @param formula to be annotated
     * @param annotation annotation to be attached to formula
     * @throws IllegalArgumentException if any of input is null, or formula does
     * not have valid id.
     */
    void annotateFormula(Formula formula, Annotation annotation) throws IllegalArgumentException;

    /**
     * Method removes given annotation to formula. Method is tracked from {@link FormulaAuditor#arroundDeleteAnnotation(cz.muni.fi.mir.db.domain.Formula, cz.muni.fi.mir.db.domain.Annotation) }
     * }
     *
     * @param formula to be modified
     * @param annotation annotation to be removed from formula
     * @throws IllegalArgumentException if any of input is null, or does not
     * have valid id.
     */
    void deleteAnnotationFromFormula(Formula formula, Annotation annotation) throws IllegalArgumentException;

    /**
     * Search method used to obtained filtered result from entire set of
     * formulas.
     *
     * @param formulaSearchRequest request containing values on which result
     * will be filtered
     * @param pagination current step of search
     * @return container holding result of request
     * @throws IllegalArgumentException if any of input is null, or if any of
     * entities stored in request is not null, but does not have set valid id.
     * If its null then its ignored.
     */
    SearchResponse<Formula> findFormulas(FormulaSearchRequest formulaSearchRequest, Pagination pagination) throws IllegalArgumentException;

    /**
     * Search method used to obtained filtered result from entire set of
     * formulas.
     *
     * @param formulaSearchRequest request containing values on which result
     * will be filtered
     * @return container holding result of request
     * @throws IllegalArgumentException if any of input is null, or if any of
     * entities stored in request is not null, but does not have set valid id.
     * If its null then its ignored.
     */
    SearchResponse<Formula> findFormulas(FormulaSearchRequest formulaSearchRequest) throws IllegalArgumentException;

    /**
     * Method executes mass canonicalization upon given list of formulas stored
     * inside list of ids, with specific revision, configuration for given user.
     *
     * @param listOfIds formulas marked for mass canonicalization
     * @param revision revision for canonicalizer
     * @param configuration configuration for canonicalizer
     * @param user user executing this action
     * @throws IllegalArgumentException if any of input is null, does not have
     * valid id, or list is empty.
     */
    void massCanonicalize(List<Long> listOfIds, Revision revision, Configuration configuration, User user) throws IllegalArgumentException;

    /**
     * Method obtains list of formulas belonging to given source document.
     *
     * @param sourceDocument desired target source
     * @return list of formulas from same source, empty list if there are none.
     * @throws IllegalArgumentException if source is null, or does not have
     * valid id.
     */
    List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;

    /**
     * Method obtains list of formulas belonging to given program.
     *
     * @param program program under which formulas were made.
     * @return list of formulas from same program, empty list if there are none.
     * @throws IllegalArgumentException if program is null, or does not have
     * valid id.
     */
    List<Formula> getFormulasByProgram(Program program) throws IllegalArgumentException;

    /**
     * Method obtains list of formulas imported by specific user.
     *
     * @param user father of formulas
     * @return list of formulas from given user, empty list if there are none.
     * @throws IllegalArgumentException if user is null, or does not have valid
     * id.
     */
    List<Formula> getFormulasByUser(User user) throws IllegalArgumentException;
    
    /**
     * Method fetches formulas whose canonic output has given hash. 
     *
     * @param hash of canonic output
     * @return list of of formulas whose co has input hash
     * @throws IllegalArgumentException if hash is null or size is not 40
     * characters.
     */
    List<Formula> getFormulasByCanonicOutputHash(String hash) throws IllegalArgumentException;
}
