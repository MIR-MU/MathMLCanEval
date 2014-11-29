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
package cz.muni.fi.mir.mathmlevaluation.services;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface FileService
{
    /**
     * Method checks whether given canonicalizer with specified revision hash exists in file directory.
     * @param revisionHash of canonicalizer which presence is checked
     * @return true if canonicalizer exists, false otherwise.
     */
    boolean canonicalizerExists(String revisionHash);
}
