package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.IngredientCommand;

public interface IngredientService {

  IngredientCommand findByRecipeIdAndIngredientId(
      Long recipeId,
      Long ingredientId);

  void deleteByRecipeIdAndIngredientId(
      Long recipeId,
      Long ingredientId);

  IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
