package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.converters.RecipeCommandToRecipe;
import com.ambear.recipeapp.converters.RecipeToRecipeCommand;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  public RecipeServiceImpl(
      RecipeRepository recipeRepository,
      RecipeCommandToRecipe recipeCommandToRecipe,
      RecipeToRecipeCommand recipeToRecipeCommand)
  {
    this.recipeRepository = recipeRepository;
    this.recipeCommandToRecipe = recipeCommandToRecipe;
    this.recipeToRecipeCommand = recipeToRecipeCommand;
  }

  @Override
  public Set<Recipe> getRecipes() {
    log.debug("I'm in the service");

    Set<Recipe> recipeSet = new HashSet<>();
    recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
    return recipeSet;
  }

  @Override
  public Recipe findById(Long id) {
    log.debug("I'm in the service");
    return recipeRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Recipe Not Found!"));
  }

  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(RecipeCommand command) {
    Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

    Recipe savedRecipe = recipeRepository.save(detachedRecipe);
    log.debug("Saved RecipeId:" + savedRecipe.getId());
    return recipeToRecipeCommand.convert(savedRecipe);
  }
}
