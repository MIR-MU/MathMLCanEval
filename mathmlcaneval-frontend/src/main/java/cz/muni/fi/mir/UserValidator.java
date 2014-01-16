/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir;

import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.service.UserService;
import cz.muni.fi.mir.wrappers.ApplicationContextWrapper;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author siska
 */
public class UserValidator implements Validator
{
    private static UserService userService = ApplicationContextWrapper.getInstance().getBean("userService");

    @Override
    public boolean supports(Class c)
    {
        return User.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors)
    {
        User user = (User)obj;

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
