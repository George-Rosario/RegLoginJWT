package com.ey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ey.model.ApplicationUser;
import com.ey.repository.UserRepository;

import static java.util.Collections.emptyList;


/**
 * @author George.Rosario
 *
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userReposiory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userReposiory.findByUserName(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUserName(), applicationUser.getPassword(), emptyList());
    }
}