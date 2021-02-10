package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Backlog;
import org.bitbucket.draganbjedov.project.manager.domain.Task;
import org.bitbucket.draganbjedov.project.manager.exceptions.ProjectIdentifierException;
import org.bitbucket.draganbjedov.project.manager.repositories.BacklogRepository;
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

    @Transactional
    public void addTask(Task task) {
        task.setProjectIdentifier(task.getProjectIdentifier().toUpperCase(Locale.ROOT));

        Backlog backlog = backlogRepository.findByProjectIdentifier(task.getProjectIdentifier());

        if (backlog == null)
            throw new ProjectIdentifierException("Project with identifier '" + task.getProjectIdentifier() + "' doesn't exists");

        int taskSequence = backlog.getTaskSequence();
        task.setProjectSequence(task.getProjectIdentifier() + "-" + taskSequence++);
        backlog.setTaskSequence(taskSequence);

        if(task.getPriority() == null || task.getPriority() == 0)
            task.setPriority(3);
        if(task.getStatus() == null || task.getStatus().isBlank())
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
}
