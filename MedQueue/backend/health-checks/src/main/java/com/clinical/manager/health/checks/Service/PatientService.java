package com.clinical.manager.health.checks.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clinical.manager.health.checks.DTO.PatientProfileResponse;
import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Exception.ResourceNotFoundException;
import com.clinical.manager.health.checks.Repository.UserRepository;

@Service
public class PatientService {

    @Autowired
    private UserRepository userRepository;

    /*
     * Fetch logged-in patient profile
     */
    public PatientProfileResponse getMyProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        Optional<Users> optionalUser =
                userRepository.findByEmail(email);

        Users user = optionalUser.orElseThrow(() ->
                new ResourceNotFoundException(
                        "Patient not found"
                ));

        return new PatientProfileResponse(

                user.getId(),

                user.getFullName(),

                user.getEmail(),

                user.getPhone(),

                user.getRole(),

        

                user.getGender(),

                user.getDateOfBirth(),

                user.getAddress(),

                user.getEmergencyContact(),

                user.getNationalId(),

                user.getProfilePicture()
        );
    }

    /*
     * Upload patient profile picture
     */
    public String uploadProfilePicture(
            MultipartFile file,
            Authentication authentication
    ) throws IOException {

        String email = authentication.getName();

        Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"
                        ));

        /*
         * Create uploads folder
         */
        Path uploadPath =
                Paths.get("uploads");

        if (!Files.exists(uploadPath)) {

            Files.createDirectories(uploadPath);
        }

        /*
         * Generate unique file name
         */
        String fileName =
                System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        /*
         * Save file
         */
        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        /*
         * Save image path into database
         */
        user.setProfilePicture(
                "/uploads/" + fileName
        );

        userRepository.save(user);

        return "Profile picture uploaded successfully";
    }
}