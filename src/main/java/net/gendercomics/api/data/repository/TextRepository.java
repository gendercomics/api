package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Text;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TextRepository extends MongoRepository<Text, String> {
}
