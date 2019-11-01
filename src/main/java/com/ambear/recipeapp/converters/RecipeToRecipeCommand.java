package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand
    implements Converter<Recipe, RecipeCommand>
{
  private final CategoryToCategoryCommand categoryConverter;
  private final IngredientToIngredientCommand ingredientConverter;
  private final NoteToNoteCommand notesConverter;

  public RecipeToRecipeCommand(
      CategoryToCategoryCommand categoryConverter,
      IngredientToIngredientCommand ingredientConverter,
      NoteToNoteCommand notesConverter)
  {
    this.categoryConverter = categoryConverter;
    this.ingredientConverter = ingredientConverter;
    this.notesConverter = notesConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public RecipeCommand convert(Recipe source) {
    if(source == null) {
      return null;
    }
    final RecipeCommand recipeCommand = new RecipeCommand();

    recipeCommand.setId(source.getId());
    recipeCommand.setDescription(source.getDescription());
    recipeCommand.setPrepTime(source.getPrepTime());
    recipeCommand.setCookTime(source.getCookTime());
    recipeCommand.setServings(source.getServings());
    recipeCommand.setSource(source.getSource());
    recipeCommand.setUrl(source.getUrl());
    recipeCommand.setDirections(source.getDirections());
    recipeCommand.setDifficulty(source.getDifficulty());
    recipeCommand.setNotes(notesConverter.convert(source.getNote()));

    if(source.getCategories() != null && source.getCategories().size() > 0) {
      source.getCategories().forEach(
          category ->
              recipeCommand
                  .getCategories()
                  .add(categoryConverter.convert(category))
      );
    }

    if(source.getIngredients() != null && source.getIngredients().size() > 0) {
      source.getIngredients().forEach(
          ingredient ->
              recipeCommand
                  .getIngredients()
                  .add(ingredientConverter.convert(ingredient))
      );
    }

    return recipeCommand;

  }

}
