package com.ambear.recipeapp.repositories;

import com.ambear.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository
    extends CrudRepository<Recipe, Long> {}
