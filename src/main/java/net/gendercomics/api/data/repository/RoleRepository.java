package net.gendercomics.api.data.repository;

import java.util.Optional;

import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RoleRepository extends MongoRepository<Role, String> {
}
