package org.bitbucket.draganbjedov.project.manager.web;

import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.bitbucket.draganbjedov.project.manager.services.UserService;
import org.bitbucket.draganbjedov.project.manager.services.ValidationService;
import org.bitbucket.draganbjedov.project.manager.validation.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private PasswordValidator passwordValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            passwordValidator.validate(user, bindingResult);

        final var error = validationService.checkForErrors(bindingResult);
        if (error.isPresent())
            return error.get();

        userService.createUser(user);
        user.setPassword("");
        user.setConfirmPassword("");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
