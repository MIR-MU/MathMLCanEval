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
package cz.muni.fi.mir.mathmlcaneval.database;

import cz.muni.fi.mir.mathmlcaneval.database.domain.GitBranch;
import cz.muni.fi.mir.mathmlcaneval.database.domain.GitRevision;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface GitRevisionDAO extends GenericDAO<GitRevision, Long>
{
    List<GitRevision> getRevisionsForBranch(GitBranch gitBranch);
}
