/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.pagination;

/**
 *
 * @author siska
 * @author Dominik Szalai
 */
public class Pagination {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_PAGINATOR_SIZE = 8;

    private int pageNumber = 0;
    private int pageSize = 0;
    private int pages = 0;

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
        if (pages < 1)
            return DEFAULT_PAGINATOR_SIZE;

        return pages;
    }

    public int getStartPage() {
        return ((getPageNumber() - 1) / getPages()) * getPages() + 1;
    }


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    
    
    public static Pagination newInstance(int numberOfRecords)
    {
        Pagination p = new Pagination();
        if(numberOfRecords == 0)
        {
            p.pages = 1;
            return p;
        }
        else
        {
            boolean isFixed = numberOfRecords % DEFAULT_PAGE_SIZE == 0;
            if(isFixed)
            {
                p.pages = numberOfRecords / DEFAULT_PAGE_SIZE;
            }
            else
            {
                p.pages = (numberOfRecords / DEFAULT_PAGE_SIZE) +1;
            }            
            return p;
        }
    }
    
    public boolean isModified()
    {
        return pages != DEFAULT_PAGINATOR_SIZE;
    }

    @Override
    public String toString()
    {
        return "Pagination{" + "pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", pages=" + pages + '}';
    }
}
