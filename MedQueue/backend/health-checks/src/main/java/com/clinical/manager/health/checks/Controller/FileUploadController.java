package com.clinical.manager.health.checks.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.clinical.manager.health.checks.Service.FileStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "File Upload APIs")
@RequestMapping("/api/v1/files")
@CrossOrigin("*")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    /*
     * Upload file
     */
    @PreAuthorize(
        "hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST','PATIENT')"
    )
    @Operation(
            summary = "Upload file",
            description = "Allows authenticated users to upload a multipart file to storage"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "File uploaded successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Missing, empty, or invalid file upload request"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            )
    })
    @PostMapping( value = "/upload",
        consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(

            @RequestParam("file")
            MultipartFile file
    ) {

        /*
         * Validate empty file
         */
        if (file.isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("No file selected");
        }

        /*
         * Save file
         */
        String fileName =
                fileStorageService.saveFile(file);

        return ResponseEntity.ok(
                "File uploaded successfully: "
                        + fileName
        );
    }
}
