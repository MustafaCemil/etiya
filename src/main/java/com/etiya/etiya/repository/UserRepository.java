package com.etiya.etiya.repository;

import com.etiya.etiya.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User emailBul(String username);
}
