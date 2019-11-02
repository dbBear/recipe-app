package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

class RecipeControllerTest {

  @Mock
  RecipeService recipeService;
  private RecipeController recipeController;
  private MockMvc mockMvc;



  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    recipeController = new RecipeController(recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }

  @Test
  void testGetRecipe() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
//    Recipe recipe = Recipe.builder().id(1L).build();


    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/show"));
  }

  @Test
  void testGetNewRecipeForm() throws Exception {
    RecipeCommand command = new RecipeCommand();

    mockMvc.perform(get("/recipe/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/recipeform"))
        .andExpect(model().attributeExists("recipe"));
  }

  @Test
  void testPostNewRecipeForm() throws Exception {
    RecipeCommand command = new RecipeCommand();
    command.setId(2L);

    when(recipeService.saveRecipeCommand(any())).thenReturn(command);

    mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/show"));
  }

  @Test
  void testGetUpdateView() throws Exception {
    RecipeCommand command = new RecipeCommand();
    command.setId(2L);

    when(recipeService.findCommandById(anyLong())).thenReturn(command);

    mockMvc.perform(get("/recipe/2/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/recipeform"))
        .andExpect(model().attributeExists("recipe"));
  }
}
