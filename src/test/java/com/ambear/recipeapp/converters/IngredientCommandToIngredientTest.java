package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";
  private static final BigDecimal AMOUNT = new BigDecimal(1);

  IngredientCommandToIngredient converter;

  @BeforeEach
  void setUp() {
    converter = new IngredientCommandToIngredient(new UomCommandToUom());
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new IngredientCommand()));
  }

  @Test
  void convert() {
    UomCommand uomCommand = new UomCommand();
    uomCommand.setId(LONG_VALUE);
    uomCommand.setDescription(DESCRIPTION);

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(LONG_VALUE);
    ingredientCommand.setDescription(DESCRIPTION);
    ingredientCommand.setAmount(AMOUNT);
    ingredientCommand.setUom(uomCommand);

    Ingredient ingredient = converter.convert(ingredientCommand);

    assertNotNull(ingredient);
    assertNotNull(ingredient.getUom());
    assertEquals(ingredient.getId(), LONG_VALUE);
    assertEquals(ingredient.getDescription(), DESCRIPTION);
    assertEquals(ingredient.getAmount(), AMOUNT);
    assertEquals(ingredient.getUom().getId(), LONG_VALUE);
    assertEquals(ingredient.getUom().getDescription(), DESCRIPTION);


  }
}