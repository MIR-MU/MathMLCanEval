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
package cz.muni.fi.mir.services;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.mir.tools.Tools;

/**
 * This class is replaces custom made recursive, or any other File Explorer to
 * find specific files. It uses {@link java.nio.file.PathMatcher} matcher to
 * match the proper files.
 *
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public class MathFileVisitor extends SimpleFileVisitor<Path>
{

    public static enum MathFileVisitorType
    {

        GLOB, REGEX
    }

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MathFileVisitor.class);

    private static final String GLOB = "glob:";
    private static final String REGEX = "regex:";

    private final List<Path> files = new ArrayList<>();
    private PathMatcher matcher;

    /**
     * Default and the only constructor for this class. See {@link java.nio.file.FileSystem#getPathMatcher(java.lang.String)
     * for more information.
     *
     * @param fileName name of file, or pattern to be matched
     * @param type type of pattern
     * @throws IllegalArgumentException
     */
    public MathFileVisitor(String fileName, MathFileVisitorType type) throws IllegalArgumentException
    {
        if (Tools.getInstance().stringIsEmpty(fileName))
        {
            throw new IllegalArgumentException("Wrong extension.");
        }

        matcher = FileSystems.getDefault().getPathMatcher((type == MathFileVisitorType.GLOB ? GLOB : REGEX) + fileName);
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
    {
        logger.debug("Visited directory: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
    {
        logger.error(exc);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        if (matcher.matches(file.getFileName()))
        {
            logger.debug("Matched: " + file);
            files.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    /**
     * Method returns all paths that were matched by matcher.
     *
     * @return List of paths based on given properties such as filename, and
     * root folder upon which is Visitor executed.
     */
    public List<Path> done()
    {
        return this.files;
    }
}
