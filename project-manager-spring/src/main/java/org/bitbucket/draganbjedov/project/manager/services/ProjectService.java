package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.exceptions.ProjectIdentifierException;
import org.bitbucket.draganbjedov.project.manager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void saveOrUpdate(Project project) {
        try {
            project.setIdentifier(project.getIdentifier().toUpperCase());
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
