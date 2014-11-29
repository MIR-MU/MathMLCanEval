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
package cz.muni.fi.mir.mathmlevaluation.db.service.impl;

import cz.muni.fi.mir.mathmlevaluation.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Formula;
import cz.muni.fi.mir.mathmlevaluation.db.service.ComparisonService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class ComparisonServiceImpl implements ComparisonService
{
    private static final Logger logger = Logger.getLogger(ComparisonServiceImpl.class);
    @Autowired private CanonicOutputDAO canonicOutputDAO;

    @Override
    @Transactional(readOnly = true)
    public Map<CanonicOutput, CanonicOutput> compare(ApplicationRun applicationRun1, 
            ApplicationRun applicationRun2) throws IllegalArgumentException
    {
        InputChecker.checkInput(applicationRun1);
        InputChecker.checkInput(applicationRun2);
        
        Map<CanonicOutput, CanonicOutput> resultMap = new HashMap<>();
        Map<Formula,List<CanonicOutput>> tempMap = new HashMap<>();
        
        // obtain results for 2 application runs
        List<CanonicOutput> runsFrom1 = canonicOutputDAO.getCanonicOutputByAppRun(applicationRun1, null)
                .getResults();
        logger.debug(runsFrom1);
        List<CanonicOutput> runsFrom2 = canonicOutputDAO.getCanonicOutputByAppRun(applicationRun2,null)
                .getResults();
        
        
        // this can be for sure optimized        
        for(CanonicOutput co : runsFrom1)
        {
            for(CanonicOutput co2 : runsFrom2)
            {
               if(co.getParents().get(0).equals(co2.getParents().get(0)))
               {
                   // there should be no more than 2 canonic outputs with same parent.
                   // if so it would mean that there are 2 same canonic outputs within
                   // one canonicalization which should not be possible from presentation
                   // layer
                   tempMap.put(co.getParents().get(0), Arrays.asList(co,co2));
               }
            }
        }
        
        
        for(Formula f : tempMap.keySet())
        {
            if(tempMap.get(f).size() == 2)
            {
                CanonicOutput co1 = tempMap.get(f).get(0);
                CanonicOutput co2 = tempMap.get(f).get(1);
                if(!co1.getHashValue().equals(co2.getHashValue()))
                {
                    resultMap.put(co1,co2);
                }
            }
            else
            {
                logger.fatal("Match error ? or more formulas with same parent "+f+" with data "+tempMap.get(f));
            }
        }
        
        return resultMap;
    }    
}
