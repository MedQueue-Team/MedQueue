package com.clinical.manager.health.checks.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.enums.Role;

/*
 * Repository responsible for database operations
 - related to the User entity.

 -JpaRepository provides:
   save()
   findAll()
   deleteById()
   findById()
  and many more automatically.
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    /*
     -Finds a user using email.
     - Optional is used to safely handle cases where the user is not found.
     */
    Optional<Users> findByEmail(String email);

    

    /*
 * Counts users by role.
 */
long countByRole(Role role);
}