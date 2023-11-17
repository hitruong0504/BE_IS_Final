package com.springboot.be_is_final.repository;

import com.springboot.be_is_final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
