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

import cz.muni.fi.mir.mathmlcaneval.services.tasks.FormulaLoadTask;
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
    private final FormulaLoadTask formulaLoadTask;
    private static final Logger LOGGER = LogManager.getLogger(FolderVisitor.class);
    private final PathMatcher matcher;

    public FolderVisitor(FormulaLoadTask formulaLoadTask ,String regex)
    {
        this.formulaLoadTask = formulaLoadTask;
        
        if(StringUtils.isEmpty(regex))
        {
            LOGGER.info("No regex set for matcher. Using default one: *.xml");
            matcher = FileSystems.getDefault().getPathMatcher("glob:*.xml");
        }
        else
        {
            LOGGER.info("Following regex obtained for matcher {}.",regex);
            matcher = FileSystems.getDefault().getPathMatcher("glob:"+regex);
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
        LOGGER.debug("Entering directory {}",dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        if (matcher.matches(file.getFileName()))
        {
            LOGGER.debug("Following file matched as formula source {}",file);
            formulaLoadTask.getPaths().offer(file);
        }
        else
        {
            LOGGER.debug("Following file was denied by matcher {}",file);
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
        return FileVisitResult.CONTINUE;
    }

}
