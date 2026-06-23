package com.clinical.manager.health.checks.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.RegisterUserRequest;
import com.clinical.manager.health.checks.DTO.UserResponse;
import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Repository.UserRepository;
import com.clinical.manager.health.checks.enums.Role;

/*
 * Handles user-related business logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Registers new doctor or receptionist.
     */
    public UserResponse registerUser(
            RegisterUserRequest request) {

        /*
         * Prevent duplicate emails.
         */
        boolean emailExists =
                userRepository
                        .findByEmail(request.getEmail())
                        .isPresent();

        if (emailExists) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        /*
         * Prevent creation of admin accounts
         * through this endpoint.
         */
        if (request.getRole() == Role.ADMIN) {

            throw new RuntimeException(
                    "Admin registration not allowed"
            );
        }

        /*
         * Create user entity.
         */
        Users user = new Users();

        user.setFullName(request.getFullName());

        user.setEmail(request.getEmail());

        /*
         * Encrypt password before saving.
         */
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(request.getRole());

        user.setDepartment(request.getDepartment());

        user.setPhone(request.getPhone());

        user.setActive(true);

        /*
         * Save user into database.
         */
        Users savedUser =
                userRepository.save(user);

        /*
         * Return response DTO.
         */
        return new UserResponse(

                savedUser.getId(),

                savedUser.getFullName(),

                savedUser.getEmail(),

                savedUser.getRole(),

                savedUser.getDepartment(),

                savedUser.getPhone(),

                "User registered successfully"
        );
    }
}
