package com.springboot.springmongooauthjwt.repository;

import com.springboot.springmongooauthjwt.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
