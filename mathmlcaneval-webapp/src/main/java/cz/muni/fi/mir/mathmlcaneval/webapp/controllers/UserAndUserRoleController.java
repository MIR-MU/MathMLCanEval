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

import cz.muni.fi.mir.mathmlcaneval.api.UserRoleService;
import cz.muni.fi.mir.mathmlcaneval.api.UserService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import cz.muni.fi.mir.mathmlcaneval.webapp.forms.UserForm;
import cz.muni.fi.mir.mathmlcaneval.webapp.forms.UserRoleForm;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.sitetitle.SiteTitle;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@RequestMapping(value = "/users")
@Controller
public class UserAndUserRoleController
{
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private Mapper mapper;

    @RequestMapping("/")
    @SiteTitle(value = "User & roles")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userList", userService.getAll());
        mm.addAttribute("userRoleList", userRoleService.getAll());
        mm.addAttribute("userForm", new UserForm());
        mm.addAttribute("userRoleForm", new UserRoleForm());
        mm.addAttribute("tabToDisplay", "userTab");

        return new ModelAndView("users", mm);
    }

    @RequestMapping("/user/submit/")
    public ModelAndView submitUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result, Model model)
    {
        userService.create(mapper.map(userForm, UserDTO.class));

        return new ModelAndView("redirect:/users/");
    }

    @RequestMapping("/userrole/submit/")
    public ModelAndView submitUserRole(@ModelAttribute("userRoleForm") @Valid UserRoleForm userRoleForm, BindingResult result, Model model)
    {
        userRoleService.create(mapper.map(userRoleForm, UserRoleDTO.class));
        return new ModelAndView("redirect:/users/");
    }
}
