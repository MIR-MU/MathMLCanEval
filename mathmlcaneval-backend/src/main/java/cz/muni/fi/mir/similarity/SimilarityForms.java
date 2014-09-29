package cz.muni.fi.mir.similarity;

import java.util.Map;

/**
 *
 * @author emptak
 */
public class SimilarityForms
{
    private String defaultForm;
    private String distanceForm;
    private Map<String,Integer> countForm;
    private String longestBranch;

    public String getLongestBranch()
    {
        return longestBranch;
    }

    public void setLongestBranch(String longestBranch)
    {
        this.longestBranch = longestBranch;
    }

    public String getDefaultForm()
    {
        return defaultForm;
    }

    public void setDefaultForm(String defaultForm)
    {
        this.defaultForm = defaultForm;
    }

    public String getDistanceForm()
    {
        return distanceForm;
    }

    public void setDistanceForm(String distanceForm)
    {
        this.distanceForm = distanceForm;
    }

    public Map<String, Integer> getCountForm()
    {
        return countForm;
    }

    public void setCountForm(Map<String, Integer> countForm)
    {
        this.countForm = countForm;
    }

    @Override
    public String toString()
    {
        return "SimilarityForms{" + "defaultForm=" + defaultForm + ", distanceForm=" + distanceForm + ", countForm=" + countForm + ", longestBranch=" + longestBranch + '}';
    }
}
