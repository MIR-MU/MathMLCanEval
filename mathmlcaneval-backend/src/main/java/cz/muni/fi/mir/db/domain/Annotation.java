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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.search.annotations.Field;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Entity(name = "annotation")
public class Annotation implements Serializable
{

    private static final long serialVersionUID = -8493177720663943928L;

    @Id
    @Column(name = "annotation_id",nullable = false)
    @SequenceGenerator(name="annotationid_seq", sequenceName="annotationid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "annotationid_seq")
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "annotationContent",columnDefinition = "text")
    @Field
    private String annotationContent;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getAnnotationContent()
    {
        return annotationContent;
    }

    public void setAnnotationContent(String annotationContent)
    {
        this.annotationContent = annotationContent;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Annotation other = (Annotation) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Annotation{" + "id=" + id + ", user=" + user + ", annotationContent=" + annotationContent +  '}';
    }
}
