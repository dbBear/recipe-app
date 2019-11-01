package com.ambear.recipeapp.controllers;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {


//  @Mock
//  RecipeRepository recipeRepository;

  @Mock
  RecipeService recipeService;
  @Mock
  Model model;
  @Mock
  IndexController indexController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    indexController = new IndexController(recipeService);
  }

  @Test
  void testMockMVC() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }

  @Test
  void getIndexPage() {

    //given
    Set<Recipe> recipes = new HashSet<>();

    Recipe recipe = new Recipe();
    recipe.setId(2L);
//    Recipe recipe = Recipe.builder().id(2L).build();
//    recipes.add(Recipe.builder().build());
    recipes.add(recipe);

    when(recipeService.getRecipes()).thenReturn(recipes);
    ArgumentCaptor<Set<Recipe>> argumentCaptor =
        ArgumentCaptor.forClass(Set.class);

    //when
    String viewName = indexController.getIndexPage(model);

    //then
    assertEquals(viewName, "index");
    verify(recipeService, times(1)).getRecipes();
    verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());

    Set<Recipe> setInController = argumentCaptor.getValue();
    assertEquals(1, setInController.size());
  }
}