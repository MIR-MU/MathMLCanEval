/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping(value = "/ajax")
public class AjaxController
{
    @Autowired
    private FileService fileService;
    
    
    @RequestMapping(value = {"/revisionexists","/revisionexists/"})
    public @ResponseBody Boolean checkRevisionExists(@RequestParam(value = "revisionHash") String revisionHash)
    {
        return fileService.canonicalizerExists(revisionHash);
    }
}
