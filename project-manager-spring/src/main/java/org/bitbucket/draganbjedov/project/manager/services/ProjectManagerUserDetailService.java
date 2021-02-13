package org.bitbucket.draganbjedov.project.manager.services;

import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.bitbucket.draganbjedov.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectManagerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username '" + username + "' doesn't exists"));
    }

    @Transactional
    public User loadUserById(long id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User with id '" + id + "' doesn't exists"));
    }
}
