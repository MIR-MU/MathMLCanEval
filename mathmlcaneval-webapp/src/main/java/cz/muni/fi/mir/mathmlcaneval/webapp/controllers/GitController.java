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
package cz.muni.fi.mir.mathmlcaneval.webapp.controllers;

import cz.muni.fi.mir.mathmlcaneval.api.GitService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitBranchDTO;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping("/git")
public class GitController
{
    private static final Logger LOGGER = LogManager.getLogger(GitController.class);
    @Autowired
    private GitService gitService;
    
    @RequestMapping("/")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        try
        {
            mm.addAttribute("currentBranch", gitService.getCurrentBranch());
            mm.addAttribute("branches", gitService.getBranches());
            mm.addAttribute("persistedRevisions", gitService.getRevisions());
            mm.addAttribute("revisions", gitService.listRevisions(gitService.getCurrentBranch()));
        }
        catch (IOException ex)
        {
            LOGGER.error(ex);
        }
        return new ModelAndView("git", mm);
    }
    
    @RequestMapping("/branch/{branch}/checkout/")
    public ModelAndView checkout(@PathVariable("branch") String branch) throws IOException
    {
        GitBranchDTO gitBranchDTO = new GitBranchDTO();
        gitBranchDTO.setName(branch);
        
        gitService.checkout(gitBranchDTO);
        
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping("/pull/")
    public ModelAndView pull()
    {
        return null;
    }
    
    @RequestMapping("/synchronize/")
    public ModelAndView synchronize() throws IOException
    {
        gitService.synchronize();
        
        return new ModelAndView("redirect:/git/");
    }
    
    @RequestMapping("/build/")
    public ModelAndView build()
    {
        return null;
    }
    
    @RequestMapping("/revision/{revision}/persist/")
    public ModelAndView persistRevision(@PathVariable("revision") String revision)
    {
        return null;
    }
    
    @RequestMapping("/branch/{branch}revision/")
    public ModelAndView listRevisions(@PathVariable("branch") String branch)
    {
        return null;
    }
}
