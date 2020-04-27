package com.project.SpringBoot.repositories;

import com.project.SpringBoot.entites.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByDeletedAtNullAndEmail(String email);
    Users findByEmail(String email);
}
