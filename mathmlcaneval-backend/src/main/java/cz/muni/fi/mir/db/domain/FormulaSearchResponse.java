/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.domain;

import java.util.List;

/**
 *
 * @author emptak
 */
public class FormulaSearchResponse
{
    private List<Formula> formulas;
    private int totalResultSize;
    private int viewSize;
    private int offset;

    public List<Formula> getFormulas()
    {
        return formulas;
    }

    public void setFormulas(List<Formula> formulas)
    {
        this.formulas = formulas;
    }

    public int getTotalResultSize()
    {
        return totalResultSize;
    }

    public void setTotalResultSize(int totalResultSize)
    {
        this.totalResultSize = totalResultSize;
    }

    public int getViewSize()
    {
        return viewSize;
    }

    public void setViewSize(int viewSize)
    {
        this.viewSize = viewSize;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }
    
    
    
}
