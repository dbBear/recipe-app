package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UomToUomCommand
    implements Converter<UnitOfMeasure, UomCommand>
{
  @Synchronized
  @Nullable
  @Override
  public UomCommand convert(UnitOfMeasure source) {
    if(source == null) {
      return null;
    }

    final UomCommand uomCommand = new UomCommand();

    uomCommand.setId(source.getId());
    uomCommand.setDescription(source.getDescription());

    return uomCommand;
  }


}
