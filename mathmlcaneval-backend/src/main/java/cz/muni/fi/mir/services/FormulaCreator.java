/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsible for generating formula from given SourceDocument
 * passed as argument of {@link #extractFormula(cz.muni.fi.mir.db.domain.SourceDocument)
 * } method. To use this class in project use @Autowired with
 * <b>formulaCreator</b> qualifier.
 *
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public class FormulaCreator
{

    private static final Logger logger = Logger.getLogger(FileDirectoryService.class);
    @Autowired
    private SecurityContextFacade securityContext;
    @Autowired
    private UserService userService;

    /**
     * Following method converts SourceDocument into Formulas. From
     * SourceDocument we obtain paths. Then we check whether file exists. If not
     * its skipped, otherwise we read the file. If file is empty, thus has no
     * lines proper message with path is logged at <i>error</i> level. If file
     * is not empty it's content is set to newly created Formula. We also set
     * inserTime to it based on current time, we set SourceDocument which has
     * been passed as method argument and from
     * {@link cz.muni.fi.mir.wrappers.SecurityContextFacade} we obtain currently
     * logged user which is set as person who created this formula from it's
     * SourceDocument. Formula <b>is not saved</b> inside database, the purpose
     * of this method is just to extract it from paths.
     *
     * @param sourceDocument containing paths to Formulas
     * @return Formulas obtained from SourceDocument passed as method argument
     * @throws FileNotFoundException if file on given path does not exist
     * @throws IOException if any exception occurs during reading of file, or
     * when File is empty
     */
    public List<Formula> extractFormula(SourceDocument sourceDocument) throws FileNotFoundException, IOException
    {
        List<Formula> result = new ArrayList<>();

        for (String docPath : sourceDocument.getDocumentPaths())
        {
            Path tmp = Paths.get(docPath);
            if (Files.exists(tmp))
            {
                String content = FileUtils.readFileToString(tmp.toFile());
                if (Tools.getInstance().stringIsEmpty(content))
                {
                    logger.error("Content of file: [" + tmp.toString() + "] is empty.");
                }
                else
                {
                    result.add(EntityFactory.createFormula(content, new DateTime(), userService.getUserByUsername(securityContext.getLoggedUser()), sourceDocument));
                }
            }
        }

        return result;
    }
}
