package com.ambear.recipeapp.services;

import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;


  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository);
  }

  @Test
  void getRecipes() {
    // because there's no repository, this is setting up fake data.
    Recipe recipe = new Recipe();
    HashSet<Recipe> recipeData = new HashSet<>();
    recipeData.add(recipe);

    // this is an 'override' so that when recipeRepository.findAll() is called, we send the above
    // data
    when(recipeRepository.findAll()).thenReturn(recipeData);

    Set<Recipe> recipes = recipeService.getRecipes();

    assertEquals(recipes.size(), 1);

    // verifies that recipeRepository method findAll was called 1 time
    verify(recipeRepository, times(1)).findAll();
  }
}