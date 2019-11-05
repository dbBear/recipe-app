package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand
  implements Converter<Ingredient, IngredientCommand>
{
  private final UomToUomCommand uomConvert;

  public IngredientToIngredientCommand(UomToUomCommand uomConvert) {
    this.uomConvert = uomConvert;
  }


  @Synchronized
  @Nullable
  @Override
  public IngredientCommand convert(Ingredient source) {
    if (source == null) {
      return null;
    }

    final IngredientCommand ingredientCommand = new IngredientCommand();

    ingredientCommand.setId(source.getId());
    if(source.getRecipe() != null) {
      ingredientCommand.setRecipeId(source.getRecipe().getId());
    }
    ingredientCommand.setDescription(source.getDescription());
    ingredientCommand.setAmount(source.getAmount());
    ingredientCommand.setUom(uomConvert.convert(source.getUom()));

    return ingredientCommand;
  }
}
