package org.bitbucket.draganbjedov.project.manager.repositories;

import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
