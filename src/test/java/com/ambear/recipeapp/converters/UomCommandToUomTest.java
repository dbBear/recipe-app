package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UomCommandToUomTest {

  private static final String DESCRIPTION = "description";
  private static final Long LONG_VALUE = 1L;

  UomCommandToUom converter;

  @BeforeEach
  void setUp() throws Exception {
    converter = new UomCommandToUom();
  }

  @Test
  void testNullParameter() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new UomCommand()));
  }

  @Test
  void convert() {
    //given
    UomCommand command = new UomCommand();
    command.setId(LONG_VALUE);
    command.setDescription(DESCRIPTION);

    //when
    UnitOfMeasure uom = converter.convert(command);

    //then
    assertNotNull(uom);
    assertEquals(LONG_VALUE, uom.getId());
    assertEquals(DESCRIPTION, uom.getDescription());

  }
}