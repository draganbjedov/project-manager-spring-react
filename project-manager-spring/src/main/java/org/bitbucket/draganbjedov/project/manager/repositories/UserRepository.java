package org.bitbucket.draganbjedov.project.manager.repositories;

import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
