package dev.amirgol.biterate.repository;

import dev.amirgol.biterate.domain.entites.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ElasticsearchRepository<Restaurant, String> {
    // TODO: Custom Queries that might happen
}
