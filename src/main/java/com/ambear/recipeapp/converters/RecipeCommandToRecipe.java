package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.domain.Category;
import com.ambear.recipeapp.domain.Ingredient;
import com.ambear.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RecipeCommandToRecipe
    implements Converter<RecipeCommand, Recipe>
{
  private final CategoryCommandToCategory categoryConverter;
  private final IngredientCommandToIngredient ingredientConverter;
  private final NoteCommandToNote notesConverter;

  public RecipeCommandToRecipe(
      CategoryCommandToCategory categoryConverter,
      IngredientCommandToIngredient ingredientConverter,
      NoteCommandToNote notesConverter)
  {
    this.categoryConverter = categoryConverter;
    this.ingredientConverter = ingredientConverter;
    this.notesConverter = notesConverter;
  }


  @Synchronized
  @Nullable
  @Override
  public Recipe convert(RecipeCommand source) {
    if(source == null) {
      return null;
    }

    final Recipe recipe = Recipe.builder()
        .id(source.getId())
        .cookTime(source.getCookTime())
        .prepTime(source.getPrepTime())
        .description(source.getDescription())
        .difficulty(source.getDifficulty())
        .directions(source.getDirections())
        .servings(source.getServings())
        .source(source.getSource())
        .url(source.getUrl())
        .note(notesConverter.convert(source.getNotes()))
        .ingredients(new HashSet<Ingredient>())
        .categories(new HashSet<Category>())
        .build();

    if(source.getCategories() != null && source.getCategories().size() > 0) {
      source.getCategories().forEach(
          category ->
              recipe
                  .getCategories()
                  .add(categoryConverter.convert(category))
      );
    }
    if(source.getIngredients() != null && source.getIngredients().size() > 0) {
      source.getIngredients().forEach(
          ingredient ->
              recipe
                  .getIngredients()
                  .add(ingredientConverter.convert(ingredient))
      );
    }
    return recipe;
  }
}
