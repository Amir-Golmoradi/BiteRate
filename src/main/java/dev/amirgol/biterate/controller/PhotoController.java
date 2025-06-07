package dev.amirgol.biterate.controller;

import dev.amirgol.biterate.domain.dto.PhotoResponse;
import dev.amirgol.biterate.domain.entites.Photo;
import dev.amirgol.biterate.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Tag(name = "Photo Management", description = "Upload, retrieve and delete photos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private final MinioService minIOService;
    private final ElasticsearchOperations elasticsearchOperations;

    @Operation(
            summary = "Upload a photo",
            description = "Uploads a photo and returns the photo metadata",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Photo> upload(
            @Parameter(description = "Photo file to upload", required = true)
            @RequestPart("file") MultipartFile file
    ) {
        String storageKey = minIOService.uploadFile(file);

        Photo photo = Photo.builder()
                .id(UUID.randomUUID().toString())
                .storageKey(storageKey)
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .uploadDate(LocalDateTime.now())
                .build();

        Photo savedPhoto = elasticsearchOperations.save(photo);
        return ResponseEntity.ok(savedPhoto);
    }

    @Operation(
            summary = "View a photo in browser",
            description = "Displays the photo directly in the browser",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Photo displayed successfully",
                            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
                    ),
                    @ApiResponse(responseCode = "404", description = "Photo not found")
            }
    )
    @GetMapping(value = "/view/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> viewImage(@PathVariable String filename) {
        Photo photo = elasticsearchOperations.get(filename, Photo.class);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(minIOService.downloadFile(photo.getStorageKey()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getContentType()))
                .body(resource);
    }

    @Operation(
            summary = "Download a photo",
            description = "Downloads a photo by filename",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Photo downloaded successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    ),
                    @ApiResponse(responseCode = "404", description = "Photo not found")
            }
    )
    @GetMapping("/download/{filename}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String filename) {
        Photo photo = elasticsearchOperations.get(filename, Photo.class);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(minIOService.downloadFile(photo.getStorageKey()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getOriginalFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(photo.getFileSize())
                .body(resource);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<PhotoResponse> getPhotoInfo(@PathVariable String filename) {
        Photo photo = elasticsearchOperations.get(filename, Photo.class);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        PhotoResponse response = PhotoResponse.builder()
                .id(photo.getId())
                .originalFilename(photo.getOriginalFilename())
                .viewUrl("/api/v1/photos/view/" + photo.getId())
                .downloadUrl("/api/v1/photos/download/" + photo.getId())
                .contentType(photo.getContentType())
                .fileSize(photo.getFileSize())
                .uploadDate(photo.getUploadDate())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a photo",
            description = "Deletes a photo from MinIO by object name",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Photo deleted"),
                    @ApiResponse(responseCode = "404", description = "Photo not found")
            }
    )
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<Void> delete(@PathVariable String fileName) {
        minIOService.deleteFile(fileName);
        return ResponseEntity.noContent().build();
    }
}
