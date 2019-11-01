package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.domain.Ingredient;
import com.ambear.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";
  private static final BigDecimal AMOUNT = new BigDecimal(1);

  IngredientToIngredientCommand converter;

  @BeforeEach
  void setUp() {
    converter =
        new IngredientToIngredientCommand(
            new UomToUomCommand());
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Ingredient()));
  }

  @Test
  void convert() {
//    UnitOfMeasure uom = UnitOfMeasure.builder()
//        .id(LONG_VALUE)
//        .description(DESCRIPTION)
//        .build();
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(LONG_VALUE);
    uom.setDescription(DESCRIPTION);

    Ingredient ingredient = new Ingredient();
    ingredient.setId(LONG_VALUE);
    ingredient.setDescription(DESCRIPTION);
    ingredient.setAmount(AMOUNT);
    ingredient.setUom(uom);

    IngredientCommand ingredientCommand = converter.convert(ingredient);

    assertNotNull(ingredientCommand);
    assertNotNull(ingredientCommand.getUom());
    assertEquals(ingredientCommand.getId(), LONG_VALUE);
    assertEquals(ingredientCommand.getDescription(), DESCRIPTION);
    assertEquals(ingredientCommand.getAmount(), AMOUNT);
    assertEquals(ingredientCommand.getUom().getId(), LONG_VALUE);
    assertEquals(ingredientCommand.getUom().getDescription(), DESCRIPTION);
  }
}