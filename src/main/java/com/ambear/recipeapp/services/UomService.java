package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.UomCommand;

import java.util.Set;

public interface UomService {
  Set<UomCommand> listAllUoms();
}
