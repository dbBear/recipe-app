package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.CategoryCommand;
import com.ambear.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";

  CategoryCommandToCategory converter;

  @BeforeEach
  void setUp() {
    converter = new CategoryCommandToCategory();
  }

  @Test
  void testNullParameter() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new CategoryCommand()));
  }

  @Test
  void convert() {
    //given
    CategoryCommand command = new CategoryCommand();
    command.setId(LONG_VALUE);
    command.setDescription(DESCRIPTION);

    //when
    Category category = converter.convert(command);

    //then
    assertNotNull(category);
    assertEquals(category.getId(), LONG_VALUE);
    assertEquals(category.getDescription(), DESCRIPTION);

  }
}