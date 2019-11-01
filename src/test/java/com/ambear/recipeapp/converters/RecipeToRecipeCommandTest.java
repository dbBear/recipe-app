package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {
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

  RecipeToRecipeCommand converter;


  @BeforeEach
  void setUp() {
    converter = new RecipeToRecipeCommand(
        new CategoryToCategoryCommand(),
        new IngredientToIngredientCommand(new UomToUomCommand()),
        new NoteToNoteCommand()
    );
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Recipe()));
  }

  @Test
  void convert() {
    Category category1 = new Category();
    category1.setId(LONG_VALUE_1);
    category1.setDescription(DESCRIPTION_1);
    Category category2 = new Category();
    category2.setId(LONG_VALUE_2);
    category2.setDescription(DESCRIPTION_2);

//    UnitOfMeasure uom = UnitOfMeasure.builder()
//        .id(LONG_VALUE_1)
//        .description(DESCRIPTION_1)
//        .build();
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(LONG_VALUE_1);
    uom.setDescription(DESCRIPTION_1);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(LONG_VALUE_1);
    ingredient1.setDescription(DESCRIPTION_1);
    ingredient1.setAmount(AMOUNT);
    ingredient1.setUom(uom);
    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(LONG_VALUE_2);
    ingredient2.setDescription(DESCRIPTION_2);
    ingredient2.setAmount(AMOUNT);
    ingredient2.setUom(uom);

    Note note = new Note();
    note.setId(LONG_VALUE_1);
    note.setRecipeNote(DESCRIPTION_1);

//    Recipe recipe = Recipe.builder()
//        .id(LONG_VALUE_1)
//        .description(DESCRIPTION_1)
//        .prepTime(PREP_TIME)
//        .cookTime(COOK_TIME)
//        .servings(SERVINGS)
//        .source(SOURCE)
//        .url(URL)
//        .directions(DIRECTIONS)
//        .difficulty(DIFFICULTY)
//        .note(note)
//        .categories(new HashSet<Category>())
//        .ingredients(new HashSet<Ingredient>())
//        .build();
    Recipe recipe = new Recipe();
    recipe.setId(LONG_VALUE_1);
    recipe.setDescription(DESCRIPTION_1);
    recipe.setPrepTime(PREP_TIME);
    recipe.setCookTime(COOK_TIME);
    recipe.setServings(SERVINGS);
    recipe.setSource(SOURCE);
    recipe.setUrl(URL);
    recipe.setDirections(DIRECTIONS);
    recipe.setDifficulty(DIFFICULTY);
    recipe.setNote(note);
    recipe.getCategories().addAll(Arrays.asList(category1, category2));
    recipe.getIngredients().addAll(Arrays.asList(ingredient1, ingredient2));

    RecipeCommand recipeCommand = converter.convert(recipe);

    assertNotNull(recipeCommand);
    assertEquals(recipeCommand.getId(), LONG_VALUE_1);
    assertEquals(recipeCommand.getDescription(), DESCRIPTION_1);
    assertEquals(recipeCommand.getPrepTime(), PREP_TIME);
    assertEquals(recipeCommand.getCookTime(), COOK_TIME);
    assertEquals(recipeCommand.getServings(), SERVINGS);
    assertEquals(recipeCommand.getSource(), SOURCE);
    assertEquals(recipeCommand.getUrl(), URL);
    assertEquals(recipeCommand.getDirections(), DIRECTIONS);
    assertEquals(recipeCommand.getDifficulty(), DIFFICULTY);
    assertEquals(recipe.getNote().getId(), LONG_VALUE_1);
    assertEquals(recipe.getNote().getRecipeNote(), DESCRIPTION_1);
    assertEquals(2, recipe.getIngredients().size());
    assertEquals(2, recipe.getCategories().size());
  }
}