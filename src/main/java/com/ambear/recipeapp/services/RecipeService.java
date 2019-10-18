package com.ambear.recipeapp.services;

import com.ambear.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

  Set<Recipe> getRecipes();
}
