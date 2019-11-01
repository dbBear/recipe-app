package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.CategoryCommand;
import com.ambear.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";

  CategoryToCategoryCommand converter;

  @BeforeEach
  void setUp() {
    converter = new CategoryToCategoryCommand();
  }

  @Test
  void testNullParameter() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Category()));
  }

  @Test
  void convert() {
    Category category = new Category();
    category.setId(LONG_VALUE);
    category.setDescription(DESCRIPTION);

    CategoryCommand categoryCommand = converter.convert(category);

    assertNotNull(categoryCommand);
    assertEquals(categoryCommand.getId(), LONG_VALUE);
    assertEquals(categoryCommand.getDescription(), DESCRIPTION);
  }
}