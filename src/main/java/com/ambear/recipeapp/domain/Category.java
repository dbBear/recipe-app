package com.ambear.recipeapp.domain;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
//  @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
  @ManyToMany(mappedBy = "categories")
  private Set<Recipe> recipes;

/* ----------------------------G and S---------------------------- */

}
