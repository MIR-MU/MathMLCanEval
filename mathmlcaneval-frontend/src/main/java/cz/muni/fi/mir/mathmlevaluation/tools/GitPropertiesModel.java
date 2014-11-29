/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.tools;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class GitPropertiesModel
{

    private String branch;                  // =${git.branch}
    private String describe;                // =${git.commit.id.describe}
    private String shortDescribe;           // =${git.commit.id.describe-short}
    private String commitId;                // =${git.commit.id}
    private String commitIdAbbrev;          // =${git.commit.id.abbrev}
    private String buildUserName;           // =${git.build.user.name}
    private String buildUserEmail;          // =${git.build.user.email}
    private String buildTime;               // =${git.build.time}
    private String commitUserName;          // =${git.commit.user.name}
    private String commitUserEmail;         // =${git.commit.user.email}
    private String commitMessageFull;       // =${git.commit.message.full}
    private String commitMessageShort;      // =${git.commit.message.short}
    private String commitTime;              // =${git.commit.time}

    public String getBranch()
    {
        return branch;
    }

    public void setBranch(String branch)
    {
        this.branch = branch;
    }

    public String getDescribe()
    {
        return describe;
    }

    public void setDescribe(String describe)
    {
        this.describe = describe;
    }

    public String getShortDescribe()
    {
        return shortDescribe;
    }

    public void setShortDescribe(String shortDescribe)
    {
        this.shortDescribe = shortDescribe;
    }

    public String getCommitId()
    {
        return commitId;
    }

    public void setCommitId(String commitId)
    {
        this.commitId = commitId;
    }

    public String getCommitIdAbbrev()
    {
        return commitIdAbbrev;
    }

    public void setCommitIdAbbrev(String commitIdAbbrev)
    {
        this.commitIdAbbrev = commitIdAbbrev;
    }

    public String getBuildUserName()
    {
        return buildUserName;
    }

    public void setBuildUserName(String buildUserName)
    {
        this.buildUserName = buildUserName;
    }

    public String getBuildUserEmail()
    {
        return buildUserEmail;
    }

    public void setBuildUserEmail(String buildUserEmail)
    {
        this.buildUserEmail = buildUserEmail;
    }

    public String getBuildTime()
    {
        return buildTime;
    }

    public void setBuildTime(String buildTime)
    {
        this.buildTime = buildTime;
    }

    public String getCommitUserName()
    {
        return commitUserName;
    }

    public void setCommitUserName(String commitUserName)
    {
        this.commitUserName = commitUserName;
    }

    public String getCommitUserEmail()
    {
        return commitUserEmail;
    }

    public void setCommitUserEmail(String commitUserEmail)
    {
        this.commitUserEmail = commitUserEmail;
    }

    public String getCommitMessageFull()
    {
        return commitMessageFull;
    }

    public void setCommitMessageFull(String commitMessageFull)
    {
        this.commitMessageFull = commitMessageFull;
    }

    public String getCommitMessageShort()
    {
        return commitMessageShort;
    }

    public void setCommitMessageShort(String commitMessageShort)
    {
        this.commitMessageShort = commitMessageShort;
    }

    public String getCommitTime()
    {
        return commitTime;
    }

    public void setCommitTime(String commitTime)
    {
        this.commitTime = commitTime;
    }

    @Override
    public String toString()
    {
        return "GitPropertiesModel{" + "branch=" + branch + ", describe=" + describe + ", shortDescribe=" + shortDescribe + ", commitId=" + commitId + ", commitIdAbbrev=" + commitIdAbbrev + ", buildUserName=" + buildUserName + ", buildUserEmail=" + buildUserEmail + ", buildTime=" + buildTime + ", commitUserName=" + commitUserName + ", commitUserEmail=" + commitUserEmail + ", commitMessageFull=" + commitMessageFull + ", commitMessageShort=" + commitMessageShort + ", commitTime=" + commitTime + '}';
    }
}
