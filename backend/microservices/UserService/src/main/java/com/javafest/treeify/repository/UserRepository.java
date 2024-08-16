package com.javafest.treeify.repository;

import com.javafest.treeify.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByVerificationToken(String token);
}

// package com.javafest.treeify.userservice.repository;

// import com.javafest.treeify.userservice.model.User;
// import org.springframework.data.mongodb.repository.MongoRepository;

// public interface UserRepository extends MongoRepository<User, String> {
// }
