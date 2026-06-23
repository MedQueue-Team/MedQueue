package com.clinical.manager.health.checks.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clinical.manager.health.checks.Exception.BadRequestException;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /*
     * Stores uploaded file
     */
    public String saveFile(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {

            throw new BadRequestException(
                    "File is required"
            );
        }

        try {

            /*
             * Create upload folder if missing
             */
            Path uploadPath =
                    Paths.get(uploadDir)
                            .toAbsolutePath()
                            .normalize();

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            /*
             * Generate unique filename
             */
            String originalFileName =
                    file.getOriginalFilename();

            if (originalFileName == null ||
                    originalFileName.trim().isEmpty()) {

                throw new BadRequestException(
                        "File name is required"
                );
            }

            String cleanFileName =
                    Paths.get(originalFileName)
                            .getFileName()
                            .toString();

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + cleanFileName;

            /*
             * Save file
             */
            Path filePath =
                    uploadPath.resolve(fileName)
                            .normalize();

            if (!filePath.startsWith(uploadPath)) {

                throw new BadRequestException(
                        "Invalid file name"
                );
            }

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload file",
                    e
            );
        }
    }
}
