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
package cz.muni.fi.mir.db.domain;

/**
 *
 * @author siska
 * @author Dominik Szalai
 */
public class Pagination {
    public static final int DEFAULT_PAGE_SIZE = 40;

    private int pageNumber;
    private int pageSize;
    private int numberOfRecords;

    private int pages;  // read-only field indicating number of pages

    public int getPageNumber() {
        if (pageNumber < 1)
            return 1;
        return pageNumber;
    }

    public int getPageSize() {
        if (pageSize < 1)
            return DEFAULT_PAGE_SIZE;

        return pageSize;
    }

    public int getPages() {
        if(numberOfRecords == 0) {
            return 1;
        }
        boolean isFixed = numberOfRecords % getPageSize() == 0;
        if(isFixed)
        {
            return numberOfRecords / getPageSize();
        }
        else
        {
            return (numberOfRecords / getPageSize()) +1;
        }
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }
    
    public int getDefaultPageSize()
    {
        return DEFAULT_PAGE_SIZE;
    }

    @Override
    public String toString()
    {
        return "Pagination{" + "pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", pages=" + pages + '}';
    }
}
