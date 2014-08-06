/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.forms;

/**
 *
 * @author emptak
 */
public class FindSimilarForm
{
    private boolean useDistance;
    private boolean useCount;
    private boolean useBranch;
    
    private boolean override;
    private boolean directWrite;
    
    private String distanceCondition;
    private String countCondition;
    private String branchCondition;
    
    private String distanceMethodValue;
    private String countElementMethodValue;
    private String branchMethodValue;
    
    
    private Long formulaID;

    public boolean isUseDistance()
    {
        return useDistance;
    }

    public void setUseDistance(boolean useDistance)
    {
        this.useDistance = useDistance;
    }

    public boolean isUseCount()
    {
        return useCount;
    }

    public void setUseCount(boolean useCount)
    {
        this.useCount = useCount;
    }

    public boolean isUseBranch()
    {
        return useBranch;
    }

    public void setUseBranch(boolean useBranch)
    {
        this.useBranch = useBranch;
    }

    public String getDistanceCondition()
    {
        return distanceCondition;
    }

    public void setDistanceCondition(String distanceCondition)
    {
        this.distanceCondition = distanceCondition;
    }

    public String getCountCondition()
    {
        return countCondition;
    }

    public void setCountCondition(String countCondition)
    {
        this.countCondition = countCondition;
    }

    public String getBranchCondition()
    {
        return branchCondition;
    }

    public void setBranchCondition(String branchCondition)
    {
        this.branchCondition = branchCondition;
    }

    public String getDistanceMethodValue()
    {
        return distanceMethodValue;
    }

    public void setDistanceMethodValue(String distanceMethodValue)
    {
        this.distanceMethodValue = distanceMethodValue;
    }

    public boolean isOverride()
    {
        return override;
    }

    public void setOverride(boolean override)
    {
        this.override = override;
    }

    public Long getFormulaID()
    {
        return formulaID;
    }

    public void setFormulaID(Long formulaID)
    {
        this.formulaID = formulaID;
    }

    public String getCountElementMethodValue()
    {
        return countElementMethodValue;
    }

    public void setCountElementMethodValue(String countElementMethodValue)
    {
        this.countElementMethodValue = countElementMethodValue;
    }

    public String getBranchMethodValue()
    {
        return branchMethodValue;
    }

    public void setBranchMethodValue(String branchMethodValue)
    {
        this.branchMethodValue = branchMethodValue;
    }

    public boolean isDirectWrite()
    {
        return directWrite;
    }

    public void setDirectWrite(boolean directWrite)
    {
        this.directWrite = directWrite;
    }
    
    

    @Override
    public String toString()
    {
        return "FindSimilarForm{" + "useDistance=" + useDistance + 
                ", useCount=" + useCount + ", useBranch=" + useBranch + 
                ", override=" + override + ", distanceCondition=" + 
                distanceCondition + ", countCondition=" + countCondition + 
                ", branchCondition=" + branchCondition + ", distanceMethodValue=" + 
                distanceMethodValue + ", countElementMethodValue=" + countElementMethodValue + 
                ", branchMethodValue=" + branchMethodValue + ", formulaID=" + formulaID + '}';
    }
    
}
