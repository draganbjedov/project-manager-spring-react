package org.bitbucket.draganbjedov.project.manager.validation;

import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(User.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        if (user.getPassword().length() < 6) {
            errors.rejectValue("password", "password.length", "Password must be at least 6 characters long");
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("password", "password.match", "Passwords doesn't match");
            errors.rejectValue("confirmPassword", "confirmPassword.match", "Passwords doesn't match");
        }
    }
}
