package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.services.IngredientService;
import com.ambear.recipeapp.services.RecipeService;
import com.ambear.recipeapp.services.UomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.ArgumentMatchers.anyLong;

class IngredientControllerTest {

  @Mock
  RecipeService recipeService;
  @Mock
  IngredientService ingredientService;
  @Mock
  UomService uomService;
  IngredientController controller;
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    controller = new IngredientController(
        recipeService, ingredientService, uomService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void testListIngredient() throws Exception {
    //given
    RecipeCommand command = new RecipeCommand();
    when(recipeService.findCommandById(anyLong())).thenReturn(command);

    //when
    mockMvc.perform(get("/recipe/1/ingredients"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/list"))
        .andExpect(model().attributeExists("recipe"));

    //then
    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  void testShowIngredient() throws Exception {
    //given
    IngredientCommand command = new IngredientCommand();

    //when
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
        .thenReturn(command);

    //then
    mockMvc.perform(get("/recipe/1/ingredient/2/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/show"))
        .andExpect(model().attributeExists("ingredient"));
  }

  @Test
  void testNewIngredientForm() throws Exception {
    //given
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    //when
    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
    when(uomService.listAllUoms()).thenReturn(new HashSet<>());

    //then
    mockMvc.perform(get("/recipe/1/ingredient/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/ingredientform"))
//        .andExpect(model().attributeExists("ingredient"))
//        .andExpect(model().attributeExists("uomList"));
        .andExpect(model().attributeExists("ingredient", "uomList"));

    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  void testUpdateIngredientForm() throws Exception {
    //given
    IngredientCommand ingredientCommand = new IngredientCommand();

    //when
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),
        anyLong())).thenReturn(ingredientCommand);
    when(uomService.listAllUoms()).thenReturn(new HashSet<>());

    //then
    mockMvc.perform(get("/recipe/1/ingredient/2/update"))
        .andExpect(status().isOk())
        .andExpect(view()
            .name("recipe/ingredient/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));
  }

  @Test
  void testSaveOrUpdate() throws Exception {
    //given
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(3L);
    ingredientCommand.setRecipeId(2L);

    //when
    when(ingredientService.saveIngredientCommand(any()))
        .thenReturn(ingredientCommand);

    //then
    mockMvc.perform(post("/recipe/2/ingredient")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("id", "")
          .param("description", "some string"))
        .andExpect(status().is3xxRedirection())
        .andExpect(
//            view().name("redirect:/recipe/2/ingredient/3/show"));
            view().name("redirect:/recipe/2/ingredients"));
  }

  @Test
  void deleteIngredient() throws Exception {
    //given

    //when

    //then
    mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/1/ingredients"));
    verify(ingredientService,times(1)).deleteByRecipeIdAndIngredientId(anyLong(), anyLong());
  }
}