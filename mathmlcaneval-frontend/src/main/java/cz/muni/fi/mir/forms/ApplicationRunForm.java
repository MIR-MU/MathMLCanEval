/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.joda.time.DateTime;

/**
 *
 * @author Empt
 */
public class ApplicationRunForm
{
    private Long id;
    private String note;
    private DateTime startTime;
    private DateTime stopTime;
    private UserForm user;
    @NotNull(message = "{validator.appruns.config.empty}")
    private ConfigurationForm configurationForm;
    @NotNull(message = "{validator.appruns.revision.empty}")
    private RevisionForm revisionForm;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public DateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(DateTime startTime)
    {
        this.startTime = startTime;
    }

    public DateTime getStopTime()
    {
        return stopTime;
    }

    public void setStopTime(DateTime stopTime)
    {
        this.stopTime = stopTime;
    }

    public UserForm getUser()
    {
        return user;
    }

    public void setUser(UserForm user)
    {
        this.user = user;
    }

    public ConfigurationForm getConfigurationForm()
    {
        return configurationForm;
    }

    public void setConfigurationForm(ConfigurationForm configurationForm)
    {
        this.configurationForm = configurationForm;
    }

    public RevisionForm getRevisionForm()
    {
        return revisionForm;
    }

    public void setRevisionForm(RevisionForm revision)
    {
        this.revisionForm = revision;
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
        final ApplicationRunForm other = (ApplicationRunForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "ApplicationRun{" + "id=" + id + ", note=" + note + ", startTime=" + startTime + ", stopTime=" + stopTime + ", user=" + user + ", configuration=" + configurationForm + ", revision=" + revisionForm + '}';
    }
}
