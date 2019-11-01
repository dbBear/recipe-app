package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.*;
import com.ambear.recipeapp.domain.Difficulty;
import com.ambear.recipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {
  private static final Long LONG_VALUE_1 = 1L;
  private static final Long LONG_VALUE_2 = 2L;
  private static final BigDecimal AMOUNT = new BigDecimal(1);
  private static final String DESCRIPTION_1 = "description 1";
  private static final String DESCRIPTION_2 = "description 2";
  private static final Integer PREP_TIME = 1;
  private static final Integer COOK_TIME = 2;
  private static final Integer SERVINGS = 3;
  private static final String SOURCE = "source";
  private static final String URL = "url";
  private static final String DIRECTIONS = "directions";
  private static final Difficulty DIFFICULTY = Difficulty.EASY;

  RecipeCommandToRecipe converter;

  @BeforeEach
  void setUp() {
    converter = new RecipeCommandToRecipe(
        new CategoryCommandToCategory(),
        new IngredientCommandToIngredient(new UomCommandToUom()),
        new NoteCommandToNote()
    );
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new RecipeCommand()));
  }

  @Test
  void convert() {
    CategoryCommand category1 = new CategoryCommand();
    category1.setId(LONG_VALUE_1);
    category1.setDescription(DESCRIPTION_1);
    CategoryCommand category2 = new CategoryCommand();
    category2.setId(LONG_VALUE_2);
    category2.setDescription(DESCRIPTION_2);

    UomCommand uom = new UomCommand();
    uom.setId(LONG_VALUE_1);
    uom.setDescription(DESCRIPTION_1);

    IngredientCommand ingredient1 = new IngredientCommand();
    ingredient1.setId(LONG_VALUE_1);
    ingredient1.setDescription(DESCRIPTION_1);
    ingredient1.setAmount(AMOUNT);
    ingredient1.setUom(uom);

    IngredientCommand ingredient2 = new IngredientCommand();
    ingredient2.setId(LONG_VALUE_2);
    ingredient2.setDescription(DESCRIPTION_2);
    ingredient2.setAmount(AMOUNT);
    ingredient2.setUom(uom);

    NoteCommand note = new NoteCommand();
    note.setId(LONG_VALUE_1);
    note.setRecipeNote(DESCRIPTION_1);


    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(LONG_VALUE_1);
    recipeCommand.setDescription(DESCRIPTION_1);
    recipeCommand.setPrepTime(PREP_TIME);
    recipeCommand.setCookTime(COOK_TIME);
    recipeCommand.setServings(SERVINGS);
    recipeCommand.setSource(SOURCE);
    recipeCommand.setUrl(URL);
    recipeCommand.setDirections(DIRECTIONS);
    recipeCommand.setDifficulty(DIFFICULTY);
    recipeCommand.setNotes(note);
    recipeCommand.getCategories().add(category1);
    recipeCommand.getCategories().add(category2);
    recipeCommand.getIngredients().add(ingredient1);
    recipeCommand.getIngredients().add(ingredient2);

    Recipe recipe = converter.convert(recipeCommand);

    assertNotNull(recipe);
    assertEquals(recipe.getId(), LONG_VALUE_1);
    assertEquals(recipe.getDescription(), DESCRIPTION_1);
    assertEquals(recipe.getPrepTime(), PREP_TIME);
    assertEquals(recipe.getCookTime(), COOK_TIME);
    assertEquals(recipe.getServings(), SERVINGS);
    assertEquals(recipe.getSource(), SOURCE);
    assertEquals(recipe.getUrl(), URL);
    assertEquals(recipe.getDirections(), DIRECTIONS);
    assertEquals(recipe.getDifficulty(), DIFFICULTY);
    assertEquals(recipe.getNote().getId(), LONG_VALUE_1);
    assertEquals(recipe.getNote().getRecipeNote(), DESCRIPTION_1);
    assertEquals(2, recipe.getIngredients().size());
    assertEquals(2, recipe.getCategories().size());
  }
}