package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.bitbucket.draganbjedov.project.manager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void saveOrUpdate(Project project) {
        projectRepository.save(project);
    }
}
