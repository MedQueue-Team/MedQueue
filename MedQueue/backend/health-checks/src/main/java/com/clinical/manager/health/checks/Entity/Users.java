package com.clinical.manager.health.checks.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.clinical.manager.health.checks.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Represents all system users:
 * - Admin
 * - Doctor
 * - Receptionist
 * - Patient
 */
@Entity
@Table(name = "users")


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {

    /*
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Full name
     */
    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    /*
     * Email for login
     */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Encrypted password
     */
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    /*
     * System role
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /*
     * Department
     * Mainly doctors/receptionists
     */
    private String department;

    /*
     * Phone number
     */
    private String phone;

    /*
     * =========================
     * PATIENT PROFILE FIELDS
     * =========================
     */

    

    /*
     * Gender
     */
    private String gender;

    /*
     * Date of birth
     */
    private LocalDate dateOfBirth;

    /*
     * Residential address
     */
    private String address;

    /*
     * Emergency contact
     */
    private String emergencyContact;

    /*
     * National ID / Passport
     */
    @Column(unique = true)
    private String nationalId;

    /*
     * Profile image URL/path
     */
    private String profilePicture;

    /*
     * Account active status
     */
    @Column(nullable = false)
    private boolean active = true;

    /*
     * Account creation time
     */
    @Column(nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    /*
     * Auto timestamp
     */
    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
    }
}
