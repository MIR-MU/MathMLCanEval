/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.pagination;

/**
 *
 * @author siska
 */
public class Pagination {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_PAGINATOR_SIZE = 8;

    private int pageNumber;
    private int pageSize;
    private int pages;

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
}
