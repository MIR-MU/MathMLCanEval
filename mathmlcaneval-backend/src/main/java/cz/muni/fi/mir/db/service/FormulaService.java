/*
 * Copyright 2014 Dominik Szalai.
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

import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dominik Szalai
 */
public interface FormulaService
{
    // following values are used for property map in similar form
    // searches
    
    //TODO jdoc
    public static final String USE_DISTANCE = "useDistance";
    public static final String USE_COUNT = "useCount";
    public static final String USE_BRANCH = "useBranch";
    public static final String USE_OVERRIDE = "useOverride";
    
    public static final String CONDITION_DISTANCE = "distanceCondition";
    public static final String CONDITION_COUNT = "countCondition";
    public static final String CONDITION_BRANCH ="branchCondition";
    
    public static final String VALUE_DISTANCEMETHOD = "distanceMethodValue";
    public static final String VALUE_COUNTELEMENTMETHOD = "countElementMethodValue";
    public static final String VALUE_BRANCHMETHOD = "branchMethodValue";

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
     * {@link MathCanonicalizerLoader#execute(java.util.List, cz.muni.fi.mir.db.domain.ApplicationRun)
     * } which executes canonicalization of all extracted formulas. If all
     * formulas we are trying to import are already inside database notification
     * with level INFO is logged. Method creates ApplicationRun out of revision
     * and configuration and then calls 
     * {@link #massFormulaImport(java.lang.String, java.lang.String, cz.muni.fi.mir.db.domain.ApplicationRun, cz.muni.fi.mir.db.domain.Program, cz.muni.fi.mir.db.domain.SourceDocument)
     * }.
     *
     * @param path root directory of mass import.
     * @param filter regular expression against which files will be matched.
     * E.g. <b>*.xml</b>
     * @param revision revision of canonicalizer.
     * @param configuration configuration for canonicalizer.
     * @param program program by which formula was created.
     * @param sourceDocument source document of formula..
     */
    void massFormulaImport(String path, String filter, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument);

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
     */
    void simpleFormulaImport(String formulaXmlContent, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument);

    /**
     * Method fetches all Formulas from database and extracts all
     * {@link Element}s from XML String representation. Then these formulas are
     * update in database
     *
     * @param force if set to true method will fetch <b>all</b> formulas,
     * otherwise if set to false only those that do not have any extracted
     * Elements are fetched.
     */
    void recalculateElements(boolean force);

    /**
     * Method recalculates all hashes inside database.
     *
     * @param force if set to false method will skip formulas that already have
     * hash.
     */
    void recalculateHashes(boolean force);

    /**
     * Method recalculates hash for given Formula.
     *
     * @param formula upon which hash should be calculated.
     * @throws IllegalArgumentException if formula does not have set it's ID.
     */
    void recalculateHash(Formula formula) throws IllegalArgumentException;

    /**
     * Method fetches Formula out of database based on given ID.
     *
     * @param id of formula to be fetched
     * @return Formula with given ID, null if there is no match
     * @throws IllegalArgumentException if ID is null, or less than 1.
     */
    Formula getFormulaByID(Long id) throws IllegalArgumentException;

    /**
     * Method fetches formula from database based on given Hash. Since hash is
     * unique as ID, there will never be 2 formulas with the same hash. Only if
     * collision occurs.
     *
     * @param hash of formula to be obtained.
     * @return formula with given hash, null if there is no match
     * @throws IllegalArgumentException if hash is null, does not contain any
     * characters or length is less than 40.
     */
    Formula getFormulaByHash(String hash) throws IllegalArgumentException;

    List<Formula> getAllFormulas();

    List<Formula> getAllFormulas(int skip, int number);

    List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument);

    List<Formula> getFormulasByProgram(Program program);

    List<Formula> getFormulasByUser(User user);

    /**
     * Method returns all formulas out of database based on given elements, from range &lt;start;end&gt;.
     * @param collection of elements to be found
     * @param start start position of result set
     * @param end end position of result set
     * @return list of formulas having given elements as sublist.
     */
    List<Formula> getFormulasByElements(Collection<Element> collection, int start, int end);

    /**
     * Method returns total number of formulas inside database.
     *
     * @return number of formulas.
     */
    int getNumberOfRecords();
    
    void reindex();
    
    List<Formula> findSimilar(Formula formula,Map<String,String> properties);
    
    void findSimilarMass(Map<String,String> properties);
}
