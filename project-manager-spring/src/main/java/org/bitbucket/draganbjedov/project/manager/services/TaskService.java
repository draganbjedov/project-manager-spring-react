package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Backlog;
import org.bitbucket.draganbjedov.project.manager.domain.Task;
import org.bitbucket.draganbjedov.project.manager.exceptions.ProjectIdentifierException;
import org.bitbucket.draganbjedov.project.manager.exceptions.TaskNotFoundException;
import org.bitbucket.draganbjedov.project.manager.repositories.BacklogRepository;
import org.bitbucket.draganbjedov.project.manager.repositories.ProjectRepository;
import org.bitbucket.draganbjedov.project.manager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class TaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public void addTask(Task task) {
        task.setProjectIdentifier(task.getProjectIdentifier().toUpperCase(Locale.ROOT));

        Backlog backlog = backlogRepository.findByProjectIdentifier(task.getProjectIdentifier());

        if (backlog == null)
            throw new ProjectIdentifierException("Project with identifier '" + task.getProjectIdentifier() + "' doesn't exists");

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
    public List<Task> getTasks(String projectIdentifier) {
        projectIdentifier = projectIdentifier.toUpperCase();

        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        if (backlog == null)
            throw new ProjectIdentifierException("Project with identifier '" + projectIdentifier + "' doesn't exists");

        return backlog.getTasks();
    }

    @Transactional
    public Task getTask(String projectIdentifier, String projectSequence) {
        projectIdentifier = projectIdentifier.toUpperCase();
        if (projectRepository.findByIdentifier(projectIdentifier) == null)
            throw new ProjectIdentifierException("Project with identifier '" + projectIdentifier + "' doesn't exists");

        projectSequence = projectSequence.toUpperCase();
        final Task task = taskRepository.findByProjectSequence(projectSequence);
        if (task == null)
            throw new TaskNotFoundException(projectSequence);
        if (!task.getProjectIdentifier().equals(projectIdentifier))
            throw new TaskNotFoundException(projectIdentifier, projectSequence);
        return task;
    }

    @Transactional
    public void updateTask(Task task, String projectIdentifier, String projectSequence) {
        Task taskFromDB = getTask(projectIdentifier, projectSequence);
        taskFromDB.setSummary(task.getSummary());
        taskFromDB.setAcceptanceCriteria(task.getAcceptanceCriteria());
        taskFromDB.setStatus(task.getStatus());
        taskFromDB.setPriority(task.getPriority());

        taskRepository.save(taskFromDB);
    }

    @Transactional
    public void deleteTask(String projectIdentifier, String projectSequence) {
        Task taskFromDB = getTask(projectIdentifier, projectSequence);
        // Remove relation with backlog
//        taskFromDB.getBacklog().getTasks().remove(taskFromDB);
        taskRepository.delete(taskFromDB);
    }
}
