package com.card.task.app.repository;

import com.card.task.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findById(Long aLong);

    Optional<User> findByEmail(String email);

    Optional<User>findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
