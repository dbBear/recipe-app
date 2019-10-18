package com.ambear.recipeapp.domain;

import javax.persistence.*;

@Entity
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private Recipe recipe;

  @Lob
  private String recipeNote;

/* ----------------------------G and S---------------------------- */

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public String getRecipeNote() {
    return recipeNote;
  }

  public void setRecipeNote(String recipeNote) {
    this.recipeNote = recipeNote;
  }
}
