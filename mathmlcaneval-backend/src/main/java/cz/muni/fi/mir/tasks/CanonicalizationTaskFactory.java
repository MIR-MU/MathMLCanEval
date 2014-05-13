/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.tasks;

import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.tools.SimilarityFormConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author emptak
 */
@Component(value = "canonicalizationTaskFactory")
public class CanonicalizationTaskFactory
{
    @Autowired private CanonicOutputService canonicOutputService;
    @Autowired private FormulaService formulaService;
    @Autowired private ApplicationRunService applicationRunService;
    @Autowired private SimilarityFormConverter similarityFormConverter;
    
    
    /**
     * Method provides new instance of CanonTask with set services. This is temp workaround to find out
     * how to properly obtain prototype bean e.g. from some pool.
     * @return 
     */
    public CanonTask provideInstance()
    {
        CanonTask ct = CanonTask.newInstance(canonicOutputService, formulaService, applicationRunService);
        
        return ct;
    }    
}
