package dev.amirgol.biterate.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperatingHours {
    @Field(type = FieldType.Nested)
    private TimeRange monday;
    @Field(type = FieldType.Nested)
    private TimeRange tuesday;
    @Field(type = FieldType.Nested)
    private TimeRange wednesday;
    @Field(type = FieldType.Nested)
    private TimeRange thursday;
    @Field(type = FieldType.Nested)
    private TimeRange friday;
    @Field(type = FieldType.Nested)
    private TimeRange saturday;
    @Field(type = FieldType.Nested)
    private TimeRange sunday;
}


