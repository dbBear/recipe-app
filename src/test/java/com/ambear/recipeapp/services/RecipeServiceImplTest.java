package com.ambear.recipeapp.services;

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

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository);
  }

  @Test
  public void getRecipeByIdTest() throws Exception {
//    Recipe recipe = new Recipe();
//    recipe.setId(1L);
    Recipe recipe = Recipe.builder().id(1L).build();
    Optional<Recipe> optionalRecipe = Optional.of(recipe);

    when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

    Recipe recipeReturn = recipeService.findById(1L);

    assertNotNull("Null recipe returned", optionalRecipe);
    verify(recipeRepository,times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void getRecipesTest() throws Exception {
//    Recipe recipe = new Recipe();
    HashSet<Recipe> recipesData = new HashSet<>();
    recipesData.add(Recipe.builder().build());

    when(recipeService.getRecipes()).thenReturn(recipesData);

    Set<Recipe> recipes = recipeService.getRecipes();

    assertNotNull("Null recipes returned", recipes);
    assertEquals(recipes.size(), 1);
    verify(recipeRepository).findAll();
    verify(recipeRepository, never()).findById(anyLong());

  }
}