package dev.amirgol.biterate.service;

import dev.amirgol.biterate.config.MinioProperties;
import dev.amirgol.biterate.exception.BiteRateException;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioStorageService implements MinioService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @PostConstruct
    public void init() {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .build());
                log.info("Created MinIO bucket: {}", minioProperties.getBucket());
            }
        } catch (Exception e) {
            log.error("Failed to initialize MinIO bucket: {}", e.getMessage());
            throw new BiteRateException("Failed to initialize MinIO bucket", e);
        }
    }

    private String generateObjectName(MultipartFile file) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String contentHash = org.apache.commons.codec.digest.DigestUtils.md5Hex(file.getInputStream());

            return String.format("%s/%s_%s.%s",
                    timestamp.substring(0, 6), // First 6 digits of timestamp for partitioning
                    contentHash,
                    timestamp,
                    fileExtension
            );
        } catch (Exception e) {
            throw new BiteRateException("Failed to generate object name", e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "bin";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BiteRateException("Cannot upload empty or null file");
        }

        String objectName = generateObjectName(file);
        try (InputStream inputStream = file.getInputStream()) {
            log.debug("Attempting to upload file: {} to bucket: {}", objectName, minioProperties.getBucket());

            // Ensure content type is never null
            String contentType = file.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(objectName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(contentType)
                    .build();

            minioClient.putObject(putObjectArgs);
            log.info("Successfully uploaded file: {} to MinIO", objectName);

        } catch (Exception exception) {
            log.error("Failed to upload file to MinIO. File: {}, Error: {}", objectName, exception.getMessage(), exception);
            throw new BiteRateException("MinIO upload failed: " + exception.getMessage(), exception);
        }

        return objectName;
    }

    @Override
    public InputStream downloadFile(String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(objectName)
                            .build()
            );
        } catch (Exception exception) {
            throw new BiteRateException("MinIO download failed", exception);
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(minioProperties.getBucket()) // Request to remove the object from the specified bucket
                            .object(objectName)
                            .build()
            );
        } catch (Exception exception) {
            throw new BiteRateException("MinIO deletion failed", exception);
        }
    }


}
