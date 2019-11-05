package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.converters.UomToUomCommand;
import com.ambear.recipeapp.domain.UnitOfMeasure;
import com.ambear.recipeapp.repositories.UomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UomServiceImplTest {

  UomToUomCommand uomToUomCommand;
  UomService uomService;

  @Mock
  UomRepository uomRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    uomToUomCommand = new UomToUomCommand();
    uomService = new UomServiceImpl(uomRepository, uomToUomCommand);
  }

  @Test
  void listAllUoms() {
    //given
    Set<UnitOfMeasure> uoms = new HashSet<>();
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setId(1L);
    UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setId(2L);
    uoms.addAll(Arrays.asList(uom1, uom2));
    when(uomRepository.findAll()).thenReturn(uoms);

    //when
    Set<UomCommand> uomCommands = uomService.listAllUoms();

    //then
    assertNotNull(uomCommands);
    assertEquals(2, uomCommands.size());
    verify(uomRepository, times(1)).findAll();
  }
}