package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.converters.UomToUomCommand;
import com.ambear.recipeapp.repositories.UomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UomServiceImpl implements UomService {

  private final UomRepository uomRepository;
  private final UomToUomCommand uomToUomCommand;

  public UomServiceImpl(
      UomRepository uomRepository,
      UomToUomCommand uomToUomCommand)
  {
    this.uomRepository = uomRepository;
    this.uomToUomCommand = uomToUomCommand;
  }

  @Override
  public Set<UomCommand> listAllUoms() {
    return StreamSupport.stream(uomRepository.findAll().spliterator(), false)
        .map(uomToUomCommand::convert)
        .collect(Collectors.toSet());
  }
}
