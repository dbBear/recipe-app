package com.ambear.recipeapp.repositories;

import com.ambear.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UomRepositoryIT {

  @Autowired
  UomRepository uomRepository;

  @BeforeEach
  void setUp() {}

  @Test
//  @DirtiesContext
  void findByDescription() {
    Optional<UnitOfMeasure> uomOptional =
        uomRepository.findByDescription("teaspoon");

    assertEquals("teaspoon", uomOptional.get().getDescription());
  }

  @Test
  void findByDescriptionCup() {
    Optional<UnitOfMeasure> uomOptional =
        uomRepository.findByDescription("cup");

    assertEquals("cup", uomOptional.get().getDescription());
  }
}