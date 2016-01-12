/*
 * Copyright 2016 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.services.impl;

import cz.muni.fi.mir.mathmlcaneval.services.tasks.Task;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class FolderVisitor extends SimpleFileVisitor<Path>
{
    private Task task;
    private static final Logger LOGGER = LogManager.getLogger(FolderVisitor.class);
    private PathMatcher matcher;

    public FolderVisitor(Task task, String regex)
    {
        LOGGER.info("constructor.");
        this.task = task;
        
        if(StringUtils.isEmpty(regex))
        {
            LOGGER.info("No regex. setting .xml");
            matcher = FileSystems.getDefault().getPathMatcher("glob:*.xml");
        }
        else
        {
            LOGGER.info("Regex obtained {}",regex);
            matcher = FileSystems.getDefault().getPathMatcher("glob:"+regex);
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
        LOGGER.debug("PreVisit {}",dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        LOGGER.debug("VisitFile {}",file);
        if (matcher.matches(file.getFileName()))
        {
            LOGGER.debug("VisitFileMathced");
            task.getFormulaLoadTask().getPaths().offer(file);
        }
        
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
    {
        LOGGER.error(exc);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
    {
        LOGGER.debug("PostVistiDirectory {}",dir);
        return FileVisitResult.CONTINUE;
    }

}
