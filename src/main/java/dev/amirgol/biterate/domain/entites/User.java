package dev.amirgol.biterate.domain.entites;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents a user within the system.
 * <p>
 * This class is intended for indexing in Elasticsearch and uses {@code Text} field types to enable
 * full-text search capabilities on all user-identifiable fields.
 * </p>
 *
 * <p><b>Field Annotations:</b></p>
 * <ul>
 *   <li>{@code id}: A unique user identifier. Treated as {@code Text} to support flexible querying across IDs if needed.</li>
 *   <li>{@code username}: The handle or login name for the user. Indexed as {@code Text} to support search by partial matches.</li>
 *   <li>{@code firstName} / {@code familyName}: Personal identity fields, indexed as {@code Text} to support user-friendly lookup.</li>
 * </ul>
 *
 * <p><b>Design Notes:</b></p>
 * <ul>
 *   <li>All fields are indexed as {@code Text} to allow for partial, case-insensitive, and language-analyzed search.</li>
 *   <li>This entity is likely used as part of document ownership, audit trails, or user-related metadata in other domain models.</li>
 * </ul>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * User user = User.builder()
 *     .id("u12345")
 *     .username("amirgol")
 *     .firstName("Amir")
 *     .familyName("Gol")
 *     .build();
 * }</pre>
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Text)
    private String firstName;

    @Field(type = FieldType.Text)
    private String familyName;
}


