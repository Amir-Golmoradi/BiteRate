package dev.amirgol.biterate.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * Represents a structured postal address used within the system.
 * <p>
 * This class is designed to be indexed in Elasticsearch and supports both keyword and text field types
 * to enable optimized searching and filtering behavior.
 * </p>
 *
 * <p><b>Field Annotations:</b></p>
 * <ul>
 *   <li>{@code Keyword} fields are used for exact match and aggregations (e.g., city, state).</li>
 *   <li>{@code Text} field is used for analyzed full-text search (e.g., streetName).</li>
 * </ul>
 *
 * <p><b>Usage:</b> Typically embedded within domain objects that require address-level information.
 * Not intended to be a standalone entity in persistent storage.</p>
 *
 * <p><b>Design Notes:</b></p>
 * <ul>
 *   <li>Fields like {@code streetNumber}, {@code unit}, {@code postalCode} use {@code Keyword} because partial
 *   search is not required.</li>
 *   <li>{@code streetName} uses {@code Text} to support flexible full-text search queries.</li>
 * </ul>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * Address address = Address.builder()
 *     .streetNumber("123")
 *     .streetName("Main Street")
 *     .unit("Apt 4B")
 *     .city("San Francisco")
 *     .state("CA")
 *     .postalCode("94105")
 *     .country("USA")
 *     .build();
 * }</pre>
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Field(type = FieldType.Keyword)
    private String streetNumber;
    @Field(type = FieldType.Text)
    private String streetName;
    @Field(type = FieldType.Keyword)
    private String unit;
    @Field(type = FieldType.Keyword)
    private String city;
    @Field(type = FieldType.Keyword)
    private String state;
    @Field(type = FieldType.Keyword)
    private String postalCode;
    @Field(type = FieldType.Keyword)
    private String country;
}
