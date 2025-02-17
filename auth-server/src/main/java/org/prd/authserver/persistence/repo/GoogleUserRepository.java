package org.prd.authserver.persistence.repo;


import org.prd.authserver.persistence.entity.GoogleUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface GoogleUserRepository extends MongoRepository<GoogleUser, String> {
    Optional<GoogleUser> findByEmail(String s);

}