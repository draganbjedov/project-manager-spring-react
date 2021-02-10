package org.bitbucket.draganbjedov.project.manager.repositories;

import org.bitbucket.draganbjedov.project.manager.domain.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String projectIdentifier);
}
