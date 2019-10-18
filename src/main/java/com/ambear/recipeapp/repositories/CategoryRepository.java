package com.ambear.recipeapp.repositories;

import com.ambear.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository
    extends CrudRepository<Category, Long>
{
  Optional<Category> findByDescription(String description);
  Iterable<Category> findAll();
}
