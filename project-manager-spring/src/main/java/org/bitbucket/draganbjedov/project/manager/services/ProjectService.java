package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Backlog;
import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.bitbucket.draganbjedov.project.manager.exceptions.ProjectIdentifierException;
import org.bitbucket.draganbjedov.project.manager.repositories.BacklogRepository;
import org.bitbucket.draganbjedov.project.manager.repositories.ProjectRepository;
import org.bitbucket.draganbjedov.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveOrUpdate(Project project, String username) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty())
                throw new UsernameNotFoundException("User with username '" + username + "' doesn't exists");
            project.setUser(optionalUser.get());

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

    @Transactional
    public Project getProjectByIdentifier(String identifier) {
        identifier = identifier.toUpperCase();
        final Project project = projectRepository.findByIdentifier(identifier);
        if (project == null)
            throw new ProjectIdentifierException("Project with identifier '" + identifier + "' doesn't exists");
        return project;
    }

    @Transactional
    public List<Project> getProjects(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException("User with username '" + username + "' doesn't exists");
        return projectRepository.findAllByUserId(optionalUser.get().getId());
    }

    @Transactional
    public void deleteProjectByIdentifier(String identifier) {
        identifier = identifier.toUpperCase();
        final Project project = projectRepository.findByIdentifier(identifier);
        if (project == null) {
            throw new ProjectIdentifierException("Project with identifier '" + identifier + "' doesn't exists");
        }
        projectRepository.delete(project);
    }

}
