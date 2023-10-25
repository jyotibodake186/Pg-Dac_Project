package com.fashionrental.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fashionrental.website.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
