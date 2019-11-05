package com.ambear.recipeapp.domain;

import javax.persistence.Entity;

public enum Difficulty {
  EASY {
    @Override
    public String toString() {
      return "Easy";
    }
  },
  MODERATE {
    @Override
    public String toString() {
      return "Moderate";
    }
  },
  HARD {
    @Override
    public String toString() {
      return "Hard";
    }
  },
  SUPER_HARD {
    @Override
    public String toString() {
      return "Super Hard";
    }
  }
}
