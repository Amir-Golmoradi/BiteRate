package dev.amirgol.biterate.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PhotoResponse {
    private String id;
    private String originalFilename;
    private String viewUrl;
    private String downloadUrl;
    private String contentType;
    private long fileSize;
    private LocalDateTime uploadDate;
}
