package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

  Set<Recipe> getRecipes();
  Recipe findById(Long id);
  void deleteById(Long id);
  RecipeCommand saveRecipeCommand(RecipeCommand command);
  RecipeCommand findCommandById(Long id);
}

