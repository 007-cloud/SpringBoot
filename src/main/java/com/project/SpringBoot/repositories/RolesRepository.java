package com.project.SpringBoot.repositories;

import com.project.SpringBoot.entites.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findById(Long id);
}
