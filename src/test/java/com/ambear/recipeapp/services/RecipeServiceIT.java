package com.ambear.recipeapp.services;


import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.converters.RecipeCommandToRecipe;
import com.ambear.recipeapp.converters.RecipeToRecipeCommand;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.repositories.RecipeRepository;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeServiceIT {
  public static final String NEW_DESCRIPTION = "New Description";

  @Autowired
  RecipeService recipeService;
  @Autowired
  RecipeRepository recipeRepository;
  @Autowired
  RecipeCommandToRecipe recipeCommandToRecipe;
  @Autowired
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Transactional
  @Test
  void testSaveOfDescription() throws Exception {
    //given
    Iterable<Recipe> recipes = recipeRepository.findAll();
    Recipe testRecipe = recipes.iterator().next();
    RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

    //when
    testRecipeCommand.setDescription(NEW_DESCRIPTION);
    RecipeCommand savedRecipeCommand =
        recipeService.saveRecipeCommand(testRecipeCommand);

    //then
    assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    assertEquals(testRecipe.getCategories().size(),
        savedRecipeCommand.getCategories().size());
    assertEquals(testRecipe.getIngredients().size(),
        savedRecipeCommand.getIngredients().size());
  }

}

