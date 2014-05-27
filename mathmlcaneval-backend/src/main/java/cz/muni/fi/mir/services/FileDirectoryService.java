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

import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for translating files on given path into Formulas.
 * None of method ensures persistency. It just reads the file and translates
 * them into Formula objects.
 *
 *
 * @author Dominik Szalai
 * @version 2.0
 * @since 1.0
 */
@Component("fileDirectoryService")
public class FileDirectoryService
{

    private static final Logger logger = Logger.getLogger(FileDirectoryService.class);
    private static final String MATCH_PATTERN = "*.xml";

    @Autowired
    private SecurityContextFacade securityContext;
    
    
    /**
     * Method used for obtaining SourceDocuments out of given path including
     * subdirectories matching fileName. Check out {@link MathFileVisitor} class
     * which is used for walking, to see the structure of fileName. Method
     * internally uses {@link #exploreDirectory(java.nio.file.Path, java.lang.String)
     * }
     * If any error occurs during the reading of file exception is
     * <b>suppressed</b> and logged with error level.
     *
     * @param path root path in which are desired files
     * @param filter regex value used for file matching. If empty default based
     * on {@link #MATCH_PATTERN} is set.
     * @return Formulas found in given folder matched against filter.
     * @throws FileNotFoundException if root path does not exist
     */
    public List<Formula> exploreDirectory(String path, String filter) throws FileNotFoundException
    {
        Path p = Paths.get(path);

        return exploreDirectory(p, filter);
    }

    /**
     * Method used for obtaining SourceDocuments out of given path including
     * subdirectories matching fileName. Check out {@link MathFileVisitor} class
     * which is used for walking, to see the structure of fileName. Method
     * ensures following out of path that has been matched:
     * <ul>
     * <li>creates formula object</li>
     * <li>as output is set empty ArrayList</li>
     * <li>as content content of given file is set</li>
     * <li>current time is set as insert time</li>
     * <li>result is added into resultList</li>
     * <li>sets logged user as creator</li>
     * </ul>
     * If any error occurs during the reading of file exception is
     * <b>suppressed</b> and logged with error level.
     *
     * @param path root path in which are desired files
     * @param filter regex value used for file matching. If empty default based
     * on {@link #MATCH_PATTERN} is set.
     * @return Formulas found in given folder matched against filter.
     * @throws FileNotFoundException if root path does not exist
     */
    public List<Formula> exploreDirectory(Path path, String filter) throws FileNotFoundException
    {
        if (!Files.exists(path))
        {
            throw new FileNotFoundException("Root directory " + path + " not found.");
        }
        if (Tools.getInstance().stringIsEmpty(filter))
        {
            filter = MATCH_PATTERN;
        }

        MathFileVisitor mfv = new MathFileVisitor(filter, MathFileVisitor.MathFileVisitorType.GLOB);

        try
        {
            Files.walkFileTree(path, mfv);
        }
        catch (IOException ex)
        {
            logger.error(ex);
        }

        List<Formula> result = new ArrayList<>(mfv.done().size());
        User u = securityContext.getLoggedEntityUser();
        for (Path p : mfv.done())
        {
            Formula f = EntityFactory.createFormula();
            f.setOutputs(new ArrayList<CanonicOutput>());
            InputStream is = null;
            try
            {
                is = Files.newInputStream(p);
                f.setXml(IOUtils.toString(is));
                f.setInsertTime(DateTime.now());
                f.setUser(u);
                result.add(f);
            }
            catch (IOException ex)
            {
                logger.error(ex);
            }
            finally
            {
                if(is != null)
                {
                    try
                    {
                        is.close();
                    }
                    catch (IOException ex)
                    {
                        logger.fatal(ex);
                    }
                }
            }
        }

        return result;
    }
}