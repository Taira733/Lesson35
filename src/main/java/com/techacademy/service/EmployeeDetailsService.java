package com.techacademy.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.techacademy.entity.Authentication;
import com.techacademy.repository.AuthenticationRepository;

public class EmployeeDetailsService implements UserDetailsService {
    private final AuthenticationRepository authenticationRepository;

    public EmployeeDetailsService(AuthenticationRepository repository) {
        this.authenticationRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Authentication> authentication = authenticationRepository.findById(username);

        if (!authentication.isPresent()) {
            throw new UsernameNotFoundException("Exception:Username Not Found");
        }
        return new EmployeeDetails(authentication.get().getEmployee());
    }
}


