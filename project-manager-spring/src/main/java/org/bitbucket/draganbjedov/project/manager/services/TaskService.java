package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Backlog;
import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.domain.Task;
import org.bitbucket.draganbjedov.project.manager.exceptions.TaskNotFoundException;
import org.bitbucket.draganbjedov.project.manager.repositories.BacklogRepository;
import org.bitbucket.draganbjedov.project.manager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    @Transactional
    public void addTask(Task task, String username) {
        task.setProjectIdentifier(task.getProjectIdentifier().toUpperCase());

        Backlog backlog = projectService.getProjectByIdentifier(task.getProjectIdentifier(), username).getBacklog();

        int taskSequence = backlog.getTaskSequence();
        task.setProjectSequence(task.getProjectIdentifier() + "-" + taskSequence++);
        backlog.setTaskSequence(taskSequence);

        if (task.getPriority() == null || task.getPriority() == 0)
            task.setPriority(3);
        if (task.getStatus() == null || task.getStatus().isBlank())
            task.setStatus("TO_DO");

        backlog.addTask(task);

        taskRepository.save(task);
    }

    @Transactional
    public List<Task> getTasks(String projectIdentifier, String username) {
        final Project project = projectService.getProjectByIdentifier(projectIdentifier, username);
        return project.getBacklog().getTasks();
    }

    @Transactional
    public Task getTask(String projectIdentifier, String projectSequence, String username) {
        projectIdentifier = projectIdentifier.toUpperCase();
        projectService.getProjectByIdentifier(projectIdentifier, username);

        projectSequence = projectSequence.toUpperCase();
        final Task task = taskRepository.findByProjectSequence(projectSequence);
        if (task == null)
            throw new TaskNotFoundException(projectSequence);
        if (!task.getProjectIdentifier().equals(projectIdentifier))
            throw new TaskNotFoundException(projectIdentifier, projectSequence);
        return task;
    }

    @Transactional
    public Task updateTask(Task task, String projectIdentifier, String projectSequence, String username) {
        Task taskFromDB = getTask(projectIdentifier, projectSequence, username);
        taskFromDB.setSummary(task.getSummary());
        taskFromDB.setAcceptanceCriteria(task.getAcceptanceCriteria());
        taskFromDB.setDueDate(task.getDueDate());
        taskFromDB.setStatus(task.getStatus());
        taskFromDB.setPriority(task.getPriority());

        taskRepository.save(taskFromDB);

        return taskFromDB;
    }

    @Transactional
    public void deleteTask(String projectIdentifier, String projectSequence, String username) {
        Task taskFromDB = getTask(projectIdentifier, projectSequence, username);
        taskRepository.delete(taskFromDB);
    }
}
