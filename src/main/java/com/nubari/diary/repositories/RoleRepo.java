package com.nubari.diary.repositories;

import com.nubari.diary.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findRoleByName(String name);
}
