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

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@NamedQueries(
{
    @NamedQuery(name = "Source.getAll", query = "SELECT s FROM source s ORDER BY s.id DESC")
})
@Entity(name = "source")
public class Source implements Serializable
{
    private static final long serialVersionUID = -8062769058303589060L;
    @Id
    @Column(name = "source_id", nullable = false)
    @SequenceGenerator(name = "sourceid_seq", sequenceName = "sourceid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sourceid_seq")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "note")
    private String note;
    @Transient
    private Path rootPath;
    @ManyToOne
    private Program program;
    @Column(name = "rootpath")
    private String rootPathString;

    @PrePersist
    private void handlePathPersist()
    {
        rootPathString = rootPath.toString();
    }

    @PostLoad
    private void handlePathLoad()
    {
        rootPath = Paths.get(rootPathString);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Path getRootPath()
    {
        return rootPath;
    }

    public void setRootPath(Path rootPath)
    {
        this.rootPath = rootPath;
    }

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    public String getRootPathString()
    {
        return rootPathString;
    }

    public void setRootPathString(String rootPathString)
    {
        this.rootPathString = rootPathString;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Source other = (Source) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Source{" + "id=" + id + ", name=" + name + ", note=" + note + ", rootPath=" + rootPath + ", program=" + program + ", rootPathString=" + rootPathString + '}';
    }
}
