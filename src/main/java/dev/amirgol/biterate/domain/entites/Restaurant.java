package dev.amirgol.biterate.domain.entites;

import dev.amirgol.biterate.domain.enums.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a restaurant listed in the RestaurantReview application.
 * <p>
 * This entity is indexed in Elasticsearch under the "restaurant" index. It includes core restaurant metadata
 * such as name, cuisine type, contact information, location, ratings, operating hours, and related entities
 * like photos and reviews.
 * </p>
 *
 * <p><b>Indexing Notes:</b></p>
 * <ul>
 *   <li>{@code @Document(indexName = "restaurant")} – Defines the Elasticsearch index name.</li>
 *   <li>{@code @Id} – Uniquely identifies each restaurant document.</li>
 *   <li>{@code @Field(type = FieldType.Keyword)} – Used for fields requiring exact match or aggregations (e.g., cuisine type).</li>
 *   <li>{@code @Field(type = FieldType.Text)} – Used for full-text search fields (e.g., name, contact info).</li>
 *   <li>{@code @GeoPointField} – Used for location-based geospatial queries.</li>
 *   <li>{@code @Field(type = FieldType.Nested)} – Used for structured sub-documents (e.g., address, photos, reviews).</li>
 * </ul>
 *
 * <p><b>Key Fields:</b></p>
 * <ul>
 *   <li><b>id</b> – Unique restaurant identifier.</li>
 *   <li><b>name</b> – Human-readable name of the restaurant. Indexed for full-text search.</li>
 *   <li><b>cuisineType</b> – Enum representing the restaurant’s cuisine. Indexed as keyword for filtering.</li>
 *   <li><b>contactInformation</b> – Optional string for phone, email, or website.</li>
 *   <li><b>averageRating</b> – Precomputed average from associated reviews, indexed as float for sorting/filtering.</li>
 *   <li><b>geoLocation</b> – Latitude and longitude for geospatial queries.</li>
 *   <li><b>address</b> – Structured location object, nested to preserve hierarchy.</li>
 *   <li><b>operatingHours</b> – Object representing opening and closing hours per day.</li>
 *   <li><b>photos</b> – List of image metadata or URLs related to the restaurant.</li>
 *   <li><b>reviews</b> – Embedded list of user-submitted reviews.</li>
 *   <li><b>createdBy</b> – Reference to the user who submitted or created the restaurant entry.</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * Restaurant restaurant = Restaurant.builder()
 *     .id("Tony Soprano")
 *     .name("La Bella Italia")
 *     .cuisineType(CuisineType.ITALIAN)
 *     .contactInformation("+1-800-123-4567")
 *     .averageRating(4.6f)
 *     .geoLocation(new GeoPoint(37.7749, -122.4194))
 *     .address(new Address(...))
 *     .operatingHours(new OperatingHours(...))
 *     .createdBy(new User(...))
 *     .build();
 * }</pre>
 */



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "restaurant")
public class Restaurant {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private CuisineType cuisineType;

    @Field(type = FieldType.Text)
    private String contactInformation;

    @Field(type = FieldType.Float)
    private Float averageRating;

    @GeoPointField
    private GeoPoint geoLocation;

    @Field(type = FieldType.Nested)
    private Address address;

    @Field(type = FieldType.Nested)
    private OperatingHours operatingHours;

    @Field(type = FieldType.Nested)
    private List<Photo> photos = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<Review> reviews = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private User createdBy;
}
