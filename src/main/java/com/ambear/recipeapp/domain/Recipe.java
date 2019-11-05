package com.ambear.recipeapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"categories", "ingredients"})
@Entity
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;

  @Lob
  private String directions;

  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;

  @Lob
  private Byte[] image;

  @OneToOne(cascade = CascadeType.ALL)
  private Note note;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private Set<Ingredient> ingredients = new HashSet<>();

//  @ManyToMany(fetch = FetchType.EAGER)
  @ManyToMany
  @JoinTable(name = "recipe_category",
      joinColumns = @JoinColumn(name = "recipe_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();


/* ----------------------------G and S---------------------------- */


  public void setNote(Note note) {
    this.note = note;
    note.setRecipe(this);
  }

  public void addIngredient(Ingredient ingredient) {
    ingredient.setRecipe(this);
    this.ingredients.add(ingredient);
  }

  public void addIngredients(List<Ingredient> ingredients) {
    ingredients.forEach(ingredient -> {
      ingredient.setRecipe(this);
      this.ingredients.add(ingredient);
    });
  }

}
