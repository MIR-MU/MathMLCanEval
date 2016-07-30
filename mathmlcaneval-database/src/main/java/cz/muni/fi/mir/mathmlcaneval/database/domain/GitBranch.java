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
package cz.muni.fi.mir.mathmlcaneval.database.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Entity(name = "gitbranch")
@NamedQuery(name = "GitBranch.getAll", query = "SELECT gb FROM gitbranch gb ORDER BY gb.id DESC")
@Data
public class GitBranch implements Serializable
{
    private static final long serialVersionUID = 1532327614785162342L;

    @Id
    @Column(name = "gitbranch_id", nullable = false)
    @SequenceGenerator(name = "gitbranchid_seq", sequenceName = "gitbranchid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gitbranchid_seq")
    private Long id;
    @Column(name = "name")
    private String name;
}
