package com.clinical.manager.health.checks.Config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Repository.UserRepository;

/*
 * Loads user details from database
 * for Spring Security authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        /*
         * Find user by email.
         */
        Users user = userRepository.findByEmail(email)

                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        /*
         * Convert application role into
         * Spring Security authority.
         *
         * Spring expects:
         * ROLE_ADMIN
         * ROLE_DOCTOR
         * ROLE_RECEPTIONIST
         */
        return new User(

                user.getEmail(),

                user.getPassword(),

                Collections.singletonList(

                        new SimpleGrantedAuthority(

                                "ROLE_" + user.getRole().name()
                        )
                )
        );
    }
}