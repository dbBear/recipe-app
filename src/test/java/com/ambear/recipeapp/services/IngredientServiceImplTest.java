package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.converters.IngredientCommandToIngredient;
import com.ambear.recipeapp.converters.IngredientToIngredientCommand;
import com.ambear.recipeapp.converters.UomCommandToUom;
import com.ambear.recipeapp.converters.UomToUomCommand;
import com.ambear.recipeapp.domain.Ingredient;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.repositories.RecipeRepository;
import com.ambear.recipeapp.repositories.UomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
  @Mock
  RecipeRepository recipeRepository;
  @Mock
  UomRepository uomRepository;
  IngredientService ingredientService;

  IngredientServiceImplTest() {
    this.ingredientToIngredientCommand =
        new IngredientToIngredientCommand(new UomToUomCommand());
    this.ingredientCommandToIngredient =
        new IngredientCommandToIngredient(new UomCommandToUom());
  }


  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    ingredientService =
        new IngredientServiceImpl(
            ingredientToIngredientCommand,
            ingredientCommandToIngredient,
            recipeRepository,
            uomRepository);
  }


  @Test
  void findByRecipeIdAndIngredientId() {}

  @Test
  void findByRecipeIdAndRecipeIdHappyPath() {
    //given
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(1L);
    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(2L);
    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId(3L);

    recipe.addIngredients(Arrays.asList(ingredient1, ingredient2, ingredient3));
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    //then
    IngredientCommand ingredientCommand =
        ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

    //when
    assertEquals(3L, ingredientCommand.getId());
    assertEquals(1L, ingredientCommand.getRecipeId());
    verify(recipeRepository, times(1)).findById(anyLong());
  }

  @Test
  void testSaveRecipeCommand() {
    //given
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(3L);
    ingredientCommand.setRecipeId(2L);
    Optional<Recipe> recipeOptional = Optional.of(new Recipe());

    Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId(3L);

    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
    when(recipeRepository.save(any())).thenReturn(savedRecipe);

    //when
    IngredientCommand savedIngredientCommand =
        ingredientService.saveIngredientCommand(ingredientCommand);

    //then
    assertEquals(Long.valueOf(3L), savedIngredientCommand.getId());
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }

  @Test
  void deleteByRecipeIdAndIngredientId() {
    //given
    Ingredient ingredient = new Ingredient();
    ingredient.setId(1L);
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    ingredient.setRecipe(recipe);
    recipe.getIngredients().add(ingredient);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    //when
    ingredientService.deleteByRecipeIdAndIngredientId(recipe.getId(),
        ingredient.getId());

    //then
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any());
  }
}