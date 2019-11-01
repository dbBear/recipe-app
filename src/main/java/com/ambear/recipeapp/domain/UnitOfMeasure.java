package com.ambear.recipeapp.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
public class UnitOfMeasure {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;

/* ----------------------------G and S---------------------------- */

}
