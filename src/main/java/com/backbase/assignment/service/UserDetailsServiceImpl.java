/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backbase.assignment.service;

import com.backbase.assignment.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.backbase.assignment.model.User user = repository.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return new User(username, user.getPassword(), user.isActive(), true, true, true, Arrays.asList(user));
    }
}
