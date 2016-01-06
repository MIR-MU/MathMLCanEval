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
package cz.muni.fi.mir.mathmlcaneval.api.dto;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class FormulaDTO implements Serializable
{
    private static final long serialVersionUID = -6538649199225913330L;
    private Long id;
    private String formulaContent;
    private String formulaHash;
    private String note;
    private DateTime importTime;
    private Path path;
    private List<AnnotationDTO> annotations;
    private SourceDTO source;
    private UserDTO user;
    private List<CanonicOutputDTO> canonicOutputs;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFormulaContent()
    {
        return formulaContent;
    }

    public void setFormulaContent(String formulaContent)
    {
        this.formulaContent = formulaContent;
    }

    public String getFormulaHash()
    {
        return formulaHash;
    }

    public void setFormulaHash(String formulaHash)
    {
        this.formulaHash = formulaHash;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public DateTime getImportTime()
    {
        return importTime;
    }

    public void setImportTime(DateTime importTime)
    {
        this.importTime = importTime;
    }

    public Path getPath()
    {
        return path;
    }

    public void setPath(Path path)
    {
        this.path = path;
    }

    public List<AnnotationDTO> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<AnnotationDTO> annotations)
    {
        this.annotations = annotations;
    }

    public SourceDTO getSource()
    {
        return source;
    }

    public void setSource(SourceDTO source)
    {
        this.source = source;
    }

    public UserDTO getUser()
    {
        return user;
    }

    public void setUser(UserDTO user)
    {
        this.user = user;
    }

    public List<CanonicOutputDTO> getCanonicOutputs()
    {
        return canonicOutputs;
    }

    public void setCanonicOutputs(List<CanonicOutputDTO> canonicOutputs)
    {
        this.canonicOutputs = canonicOutputs;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final FormulaDTO other = (FormulaDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "FormulaDTO{" + "id=" + id + ", formulaContent=" + formulaContent + ", formulaHash=" + formulaHash + ", note=" 
                + note + ", importTime=" + importTime + ", path=" + path + ", annotations=" 
                + annotations + ", source=" + source + ", user=" + user 
                + ", canonicOutputs=" + (canonicOutputs != null ? canonicOutputs.size() : 0) + '}';
    }
    
}
