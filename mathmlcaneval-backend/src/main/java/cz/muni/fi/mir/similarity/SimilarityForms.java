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
package cz.muni.fi.mir.similarity;

import java.util.Map;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class SimilarityForms
{
    private String defaultForm;
    private String distanceForm;
    private Map<String,Integer> countForm;    

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
        return "SimilarityForms{" + "defaultForm=" + defaultForm + ", distanceForm=" + distanceForm + ", countForm=" + countForm + '}';
    }
}
