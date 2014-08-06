package cz.muni.fi.mir.similarity;

/**
 *
 * @author emptak
 */
public class SimilarityForms
{
    private String defaultForm;
    private String distanceForm;
    private String countForm;
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

    public String getCountForm()
    {
        return countForm;
    }

    public void setCountForm(String countForm)
    {
        this.countForm = countForm;
    }

    @Override
    public String toString()
    {
        return "SimilarityForms{" + "defaultForm=" + defaultForm + ", distanceForm=" + distanceForm + ", countForm=" + countForm + ", longestBranch=" + longestBranch + '}';
    }
}
