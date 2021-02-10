package org.bitbucket.draganbjedov.project.manager.repositories;

import org.bitbucket.draganbjedov.project.manager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByProjectSequence(String projectSequence);

}
