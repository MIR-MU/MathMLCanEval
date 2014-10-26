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
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author emptak
 */
@Entity(name = "revision")
public class Revision implements Serializable
{

    private static final long serialVersionUID = -2258957018668800641L;

    @Id
    @Column(name = "revision_id",nullable = false)
    @SequenceGenerator(name="revisionid_seq", sequenceName="revisionid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "revisionid_seq")
    private Long id;

    @Column(name = "revisionHash")
    private String revisionHash;

    @Column(name = "note")
    private String note;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRevisionHash()
    {
        return revisionHash;
    }

    public void setRevisionHash(String revisionHash)
    {
        this.revisionHash = revisionHash;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Revision other = (Revision) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Revision{" + "id=" + id + ", revisionHash=" + revisionHash + ", note=" + note + '}';
    }

}
