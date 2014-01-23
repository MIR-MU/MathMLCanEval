/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir;

import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author siska
 */
public class UserValidator implements Validator
{
    @Autowired private UserService userService;

    @Override
    public boolean supports(Class c)
    {
        return UserForm.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors)
    {
        UserForm user = (UserForm)obj;
        
        if (userService.getUserByUsername(user.getUsername()) != null)
        {
            errors.rejectValue("username", "register.alreadyExists");
        }

        if (!user.getPassword().equals(user.getPasswordVerify()))
        {
            errors.rejectValue("passwordVerify", "register.passwordsDontMatch");
        }
    }
}
