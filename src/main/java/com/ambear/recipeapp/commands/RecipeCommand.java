package com.ambear.recipeapp.commands;

import com.ambear.recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private Difficulty difficulty;
  private Byte[] image;
  private NoteCommand note;
  private Set<CategoryCommand> categories = new HashSet<>();
}
