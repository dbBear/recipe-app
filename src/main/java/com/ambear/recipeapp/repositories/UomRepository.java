package com.ambear.recipeapp.repositories;

import com.ambear.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UomRepository
    extends CrudRepository<UnitOfMeasure, Long>
{
  Optional<UnitOfMeasure> findByDescription(String description);
}
