package com.fashionrental.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fashionrental.website.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
