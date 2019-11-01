package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

  @Mock
  RecipeService recipeService;

  RecipeController recipeController;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    recipeController = new RecipeController(recipeService);
  }

  @Test
  public void testGetRecipe() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
//    Recipe recipe = Recipe.builder().id(1L).build();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/show/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/show"));
  }
}
