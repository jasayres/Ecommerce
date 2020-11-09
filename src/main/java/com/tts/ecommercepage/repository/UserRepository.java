package com.tts.ecommercepage.repository;

import com.tts.ecommercepage.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface UserRepository extends CrudRepository<User, Long> {
        User findByUsername(String username);
    }
