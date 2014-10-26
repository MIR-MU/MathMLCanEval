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

import java.util.List;

import cz.muni.fi.mir.db.domain.SourceDocument;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface SourceDocumentService
{

    /**
     * Method creates given source document inside database.
     *
     * @param sourceDocument to be created
     * @throws IllegalArgumentException if source document is null
     */
    void createSourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;

    /**
     * Method updates given source document inside database.
     *
     * @param sourceDocument to be updated
     * @throws IllegalArgumentException if source document is null or does not
     * have valid id.
     */
    void updateSourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;

    /**
     * Method deletes given source document from system.
     *
     * @param sourceDocument to be deleted
     * @throws IllegalArgumentException if source document is null or does not
     * have valid id.
     */
    void deleteSourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;

    /**
     * Method obtains source document based on given id.
     *
     * @param id of source document
     * @return source document with given id, null if there is no such document
     * @throws IllegalArgumentException if id is null or less than one.
     */
    SourceDocument getSourceDocumentByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains all source documents in form of list.
     *
     * @return list of all source documents, empty list if there are none yet.
     */
    List<SourceDocument> getAllDocuments();
}
