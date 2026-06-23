package com.clinical.manager.health.checks.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Repository.UserRepository;
import com.clinical.manager.health.checks.enums.Role;

/*
 * Automatically creates default admin
 * account during application startup.
 */
@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Read admin credentials from
     * environment variables.
     */
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        /*
         * Check if admin already exists.
         */
        if (userRepository.findByEmail(adminEmail).isPresent()) {

            System.out.println(
                    "Admin account already exists."
            );

            return;
        }

        /*
         * Create admin account.
         */
        Users admin = new Users();

        admin.setFullName("System Administrator");

        admin.setEmail(adminEmail);

        /*
         * Encrypt password before saving.
         */
        admin.setPassword(
                passwordEncoder.encode(adminPassword)
        );

        admin.setPhone("0978034856");

        admin.setDepartment("Administration");

        admin.setRole(Role.ADMIN);

        admin.setActive(true);

        userRepository.save(admin);

        System.out.println(
                "Secure admin account created successfully."
        );
    }
}