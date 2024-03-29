package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UomToUomCommandTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";

  UomToUomCommand converter;

  @BeforeEach
  void setUp() {
    converter = new UomToUomCommand();
  }

  @Test
  void testNullParameter() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new UnitOfMeasure()));
  }


  @Test
  void convert() {
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(LONG_VALUE);
    uom.setDescription(DESCRIPTION);

    UomCommand uomCommand = converter.convert(uom);

    assertNotNull(uomCommand);
    assertEquals(uomCommand.getId(), LONG_VALUE);
    assertEquals(uomCommand.getDescription(), DESCRIPTION);
  }
}