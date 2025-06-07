package dev.amirgol.biterate.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "photos")
public class Photo {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String storageKey; // The hashed filename in MinIO

    @Field(type = FieldType.Keyword)
    private String originalFilename; // Original filename uploaded by user

    @Field(type = FieldType.Keyword)
    private String contentType;

    @Field(type = FieldType.Long)
    private long fileSize;

    @Field(type = FieldType.Date, format = date_hour_minute_second)
    private LocalDateTime uploadDate;
}
