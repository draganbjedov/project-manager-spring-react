package org.bitbucket.draganbjedov.project.manager.repositories;

import org.bitbucket.draganbjedov.project.manager.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByIdentifier(String identifier);

    List<Project> findAllByUserId(long userId);

}
