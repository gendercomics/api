package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
