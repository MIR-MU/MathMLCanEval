/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface CanonicOutputDAO 
{
    void createCanonicOutput(CanonicOutput canonicOutput);
    void updateCanonicOutput(CanonicOutput canonicOutput);
    void deleteCanonicOutput(CanonicOutput canonicOutput);
    
    CanonicOutput getCanonicOutputByID(Long id);
    
    List<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun);
    List<CanonicOutput> getCanonicOutputByFormula(Formula formula);
    List<CanonicOutput> getCanonicOutputByParentFormula(Formula formula);

    List<CanonicOutput> getSimilarCanonicOutputs(CanonicOutput canonicOutput, int skip, int maxResults);
    
}
