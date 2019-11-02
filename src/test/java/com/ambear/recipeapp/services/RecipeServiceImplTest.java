package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.converters.RecipeCommandToRecipe;
import com.ambear.recipeapp.converters.RecipeToRecipeCommand;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;
  RecipeCommandToRecipe recipeCommandToRecipe;
  RecipeToRecipeCommand recipeToRecipeCommand;
  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  void getRecipeByIdTest() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
//    Recipe recipe = Recipe.builder().id(1L).build();
    Optional<Recipe> optionalRecipe = Optional.of(recipe);

    when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

    Recipe recipeReturn = recipeService.findById(1L);

    assertNotNull("Null recipe returned", optionalRecipe);
    verify(recipeRepository,times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  void getRecipesTest() {
//    Recipe recipe = new Recipe();
    HashSet<Recipe> recipesData = new HashSet<>();
    recipesData.add(new Recipe());

    when(recipeService.getRecipes()).thenReturn(recipesData);

    Set<Recipe> recipes = recipeService.getRecipes();

    assertNotNull("Null recipes returned", recipes);
    assertEquals(recipes.size(), 1);
    verify(recipeRepository).findAll();
    verify(recipeRepository, never()).findById(anyLong());
  }

//  @Test
//  void saveRecipeCommandTest() {
//    RecipeCommand command = new RecipeCommand();
//    command.setId(1L);
//    Recipe recipe = recipeCommandToRecipe.convert(command);
//
//    when(recipeRepository.save(any())).thenReturn(recipe);
//
//    RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
//
//    assertNotNull("Null commands returned", savedCommand);
//    assertEquals(command.getId(), savedCommand.getId());
//    verify(recipeRepository,times(1)).save(any());
//
//
//  }
}