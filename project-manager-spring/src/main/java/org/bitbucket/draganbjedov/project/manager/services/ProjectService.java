package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Backlog;
import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.exceptions.ProjectIdentifierException;
import org.bitbucket.draganbjedov.project.manager.repositories.BacklogRepository;
import org.bitbucket.draganbjedov.project.manager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public void saveOrUpdate(Project project) {
        try {
            final String identifier = project.getIdentifier().toUpperCase();
            project.setIdentifier(identifier);

            // For new project create backlog
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(identifier);
            } else if (project.getBacklog() == null) {
                // If backlog is not provided set the one from db
                final Backlog backlog = backlogRepository.findByProjectIdentifier(identifier);
                project.setBacklog(backlog);
            }
            projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdentifierException("Project identifier '" + project.getIdentifier() + "' already exists");
        }
    }

    public Project getProjectByIdentifier(String identifier) {
        identifier = identifier.toUpperCase();
        final Project project = projectRepository.findByIdentifier(identifier);
        if (project == null)
            throw new ProjectIdentifierException("Project with identifier '" + identifier + "' doesn't exists");
        return project;
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String identifier) {
        identifier = identifier.toUpperCase();
        final Project project = projectRepository.findByIdentifier(identifier);
        if (project == null) {
            throw new ProjectIdentifierException("Project with identifier '" + identifier + "' doesn't exists");
        }
        projectRepository.delete(project);
    }

}
