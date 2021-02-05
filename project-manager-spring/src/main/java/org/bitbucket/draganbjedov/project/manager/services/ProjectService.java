package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.exceptions.ProjectIdentifierException;
import org.bitbucket.draganbjedov.project.manager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Project> getProjectByIdentifier(String identifier) {
        return Optional.ofNullable(projectRepository.findByIdentifier(identifier.toUpperCase()));
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
}
