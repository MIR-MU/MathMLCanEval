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
package cz.muni.fi.mir.mathmlcaneval.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import cz.muni.fi.mir.mathmlcaneval.services.WorkingDirectoryLocker;

@Component
public class WorkingDirectoryLockerImpl implements WorkingDirectoryLocker
{
    private boolean operationRunning = false;
    private static final Logger LOGGER = LogManager.getLogger(WorkingDirectoryLockerImpl.class);

    @Override
    public synchronized void lock() throws IllegalStateException
    {
        if (operationRunning)
        {
            throw new IllegalStateException("Operation is running.");
        }
        operationRunning = true;
        LOGGER.info("GitOperationProgres is going into locked state.");
    }

    @Override
    public synchronized void unlock() throws IllegalStateException
    {
        if (!operationRunning)
        {
            throw new IllegalStateException("Operation is not running.");
        }
        operationRunning = false;
        LOGGER.info("GitOperationProgres is going into unlocked state.");
    }

    @Override
    public synchronized boolean locked()
    {
        return operationRunning;
    }

}
