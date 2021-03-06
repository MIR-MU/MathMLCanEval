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

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class FileServiceImpl implements FileService
{
    @Value("${mathml-canonicalizer.default.jarFolder}")
    private String jarsFolder;

    @Override
    public boolean canonicalizerExists(String revisionHash)
    {
        Path temp = FileSystems.getDefault().getPath(this.jarsFolder, revisionHash + ".jar");
        
        return Files.exists(temp);
    }
}
