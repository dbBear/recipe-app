package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UomCommandToUom
    implements Converter<UomCommand, UnitOfMeasure>
{
  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasure convert(UomCommand source) {
    if(source == null) {
      return null;
    }

    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(source.getId());
    uom.setDescription(source.getDescription());

    return uom;
//    return UnitOfMeasure.builder()
//        .id(source.getId())
//        .description(source.getDescription())
//        .build();
  }
}
