package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient
    implements Converter<IngredientCommand, Ingredient>
{

  private final UomCommandToUom uomConverter;

  public IngredientCommandToIngredient(
      UomCommandToUom uomConverter)
  {
    this.uomConverter = uomConverter;
  }

//  @Synchronized
  @Nullable
  @Override
  public Ingredient convert(IngredientCommand source) {
    if(source == null) {
      return null;
    }

    Ingredient ingredient = new Ingredient();
    ingredient.setId(source.getId());
    ingredient.setDescription(source.getDescription());
    ingredient.setAmount(source.getAmount());
    ingredient.setUom(uomConverter.convert(source.getUom()));

    return ingredient;
  }
}
