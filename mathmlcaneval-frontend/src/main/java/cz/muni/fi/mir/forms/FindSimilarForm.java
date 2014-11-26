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
    
    private boolean override;
    private boolean crosslink;
    private boolean directWrite;
    
    private String distanceCondition;
    private String countCondition;
    
    private String distanceMethodValue;
    private String countElementMethodValue;
    
    
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

    public boolean isCrosslink()
    {
        return crosslink;
    }

    public void setCrosslink(boolean crosslink)
    {
        this.crosslink = crosslink;
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
                ", useCount=" + useCount +  
                ", override=" + override + ", distanceCondition=" + 
                distanceCondition + ", countCondition=" + countCondition + 
                ", distanceMethodValue=" + 
                distanceMethodValue + ", countElementMethodValue=" + countElementMethodValue + 
                ", formulaID=" + formulaID + '}';
    }
    
}
