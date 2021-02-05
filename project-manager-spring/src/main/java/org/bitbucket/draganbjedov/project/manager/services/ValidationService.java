package org.bitbucket.draganbjedov.project.manager.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    @SuppressWarnings("ConstantConditions")
    public Optional<ResponseEntity<Map<String, String>>> checkForErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .map(fr -> Map.entry(fr.getField(), fr.getDefaultMessage()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
            return Optional.of(new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }
}
