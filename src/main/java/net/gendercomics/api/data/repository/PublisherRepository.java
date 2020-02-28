package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Publisher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublisherRepository extends MongoRepository<Publisher, String> {

}
