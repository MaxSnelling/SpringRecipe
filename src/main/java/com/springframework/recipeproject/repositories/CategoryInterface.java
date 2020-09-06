package com.springframework.recipeproject.repositories;

import com.springframework.recipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryInterface extends CrudRepository<Category, Long> {
}
