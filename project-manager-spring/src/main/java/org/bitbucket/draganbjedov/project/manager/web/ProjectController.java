package org.bitbucket.draganbjedov.project.manager.web;

import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.services.ProjectService;
import org.bitbucket.draganbjedov.project.manager.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationService validationService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        final var error = validationService.checkForErrors(bindingResult);
        if (error.isPresent())
            return error.get();

        projectService.saveOrUpdate(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable String id) {
        return new ResponseEntity<>(projectService.getProjectByIdentifier(id), HttpStatus.OK);
    }

    @GetMapping()
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        projectService.deleteProjectByIdentifier(id);
        return new ResponseEntity<>("Project is successfully deleted", HttpStatus.OK);
    }
}
