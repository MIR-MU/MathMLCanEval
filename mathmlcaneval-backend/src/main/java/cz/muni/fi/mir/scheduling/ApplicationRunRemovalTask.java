/*
 * Copyright 2014 emptak.
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

package cz.muni.fi.mir.scheduling;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <b>DOES NOT WORK</b>
 * @author emptak
 */
public class ApplicationRunRemovalTask implements Runnable
{
    private static final Logger logger = Logger.getLogger(ApplicationRunRemovalTask.class);
    @Autowired 
    private ApplicationRunService applicationRunService;
    
    private ApplicationRun applicationRun;
    private boolean deleteFomulas;
    private boolean deleteCanonicOutputs;
    
    

    public void setDependencies(ApplicationRunService appRunService,ApplicationRun applicationRun,boolean deleteFormulas, boolean deleteCanonicOutputs)
    {
        logger.info("deps are being set");
        this.applicationRun = applicationRun;
        this.deleteCanonicOutputs = deleteCanonicOutputs;
        this.deleteFomulas  = deleteFormulas;
        
        //from unknown reason autowire does not work here :(
        this.applicationRunService = appRunService;
    }
    
    

    @Override
    public void run()
    {
        logger.info("yuppi yaaa runninnnngg wild");
        if(applicationRunService != null)
        {
            applicationRunService.deleteApplicationRun(applicationRun,deleteFomulas,deleteCanonicOutputs);
        }
        else
        {
            logger.info("ApplicationRunService was not autowired. Task canceled.");
        }
    }
}
