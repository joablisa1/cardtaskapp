package com.card.task.app.repository;

import com.card.task.app.model.Role;
import com.card.task.app.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleEnum name);

    Boolean existsByName(RoleEnum name);

}
