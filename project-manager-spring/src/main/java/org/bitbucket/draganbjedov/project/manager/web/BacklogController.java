package org.bitbucket.draganbjedov.project.manager.web;

import org.bitbucket.draganbjedov.project.manager.domain.Task;
import org.bitbucket.draganbjedov.project.manager.services.TaskService;
import org.bitbucket.draganbjedov.project.manager.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin("http://localhost:3000")
public class BacklogController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ValidationService validationService;

    @PostMapping
    public ResponseEntity<?> createNewTask(@Valid @RequestBody Task task, BindingResult bindingResult) {
        final var error = validationService.checkForErrors(bindingResult);
        if (error.isPresent())
            return error.get();

        taskService.addTask(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public List<Task> getTasksForProject(@PathVariable("id") String projectIdentifier) {
        return taskService.getTasks(projectIdentifier);
    }

    @GetMapping("/{project_id}/{task_id}")
    public Task getTask(@PathVariable("project_id") String projectIdentifier, @PathVariable("task_id") String projectSequence) {
        return taskService.getTask(projectIdentifier, projectSequence);
    }

    @PatchMapping("/{project_id}/{task_id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody Task task, BindingResult bindingResult,
                                        @PathVariable("project_id") String projectIdentifier,
                                        @PathVariable("task_id") String projectSequence) {
        final var error = validationService.checkForErrors(bindingResult);
        if (error.isPresent())
            return error.get();

        taskService.updateTask(task, projectIdentifier, projectSequence);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{project_id}/{task_id}")
    public ResponseEntity<?> deleteTask(@PathVariable("project_id") String projectIdentifier, @PathVariable("task_id") String projectSequence) {
        taskService.deleteTask(projectIdentifier, projectSequence);
        return new ResponseEntity<>("Task is successfully deleted", HttpStatus.OK);
    }
}
