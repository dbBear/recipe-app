package com.ambear.recipeapp.bootstrap;

import com.ambear.recipeapp.domain.*;
import com.ambear.recipeapp.repositories.CategoryRepository;
import com.ambear.recipeapp.repositories.RecipeRepository;
import com.ambear.recipeapp.repositories.UomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final RecipeRepository recipeRepository;
  private final CategoryRepository categoryRepository;
  private final UomRepository uomRepository;

  public RecipeBootstrap(
      RecipeRepository recipeRepository,
      CategoryRepository categoryRepository,
      UomRepository uomRepository)
  {
    this.recipeRepository = recipeRepository;
    this.categoryRepository = categoryRepository;
    this.uomRepository = uomRepository;
  }

  @Override
  @Transactional // solves an issues with 'lazy fetching' of data in hashcodes
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    log.debug("I'm in the DataLoader class");

    recipeRepository.saveAll(getRecipes());
    System.out.println("Loaded recipes...");
  }

  private List<Recipe> getRecipes() {

    Category americanCategory;
    Category italianCategory;
    Category mexicanCategory;
    Category fastFoodCategory;
    try {
      americanCategory = categoryRepository.findByDescription("American").get();
      italianCategory = categoryRepository.findByDescription("Italian").get();
      mexicanCategory = categoryRepository.findByDescription("Mexican").get();
      fastFoodCategory = categoryRepository.findByDescription("Fast Food").get();
    } catch (Exception e) {
      throw new RuntimeException("Expected Category not found");
    }
    System.out.println("got all loaded categories...");

    UnitOfMeasure teaspoonUoM;
    UnitOfMeasure tablespoonUoM;
    UnitOfMeasure cupUoM;
    UnitOfMeasure pinchUoM;
    UnitOfMeasure ounceUoM;
    UnitOfMeasure wholeUoM;
    UnitOfMeasure dashUoM;
    UnitOfMeasure pintUoM;
    try {
      teaspoonUoM = uomRepository.findByDescription("teaspoon").get();
      tablespoonUoM = uomRepository.findByDescription("tablespoon").get();
      cupUoM = uomRepository.findByDescription("cup").get();
      pinchUoM = uomRepository.findByDescription("pinch").get();
      ounceUoM = uomRepository.findByDescription("ounce").get();
      wholeUoM = uomRepository.findByDescription("whole").get();
      dashUoM = uomRepository.findByDescription("dash").get();
      pintUoM = uomRepository.findByDescription("pint").get();
    } catch (Exception e) {
      throw new RuntimeException("Expected UnitOfMeasure not found");
    }
    System.out.println("got all loaded unites of measure...");


    Recipe guacRecipe = new Recipe();
    guacRecipe.setDescription("Perfect Guacamole");
    guacRecipe.setPrepTime(10);
    guacRecipe.setCookTime(0);
    guacRecipe.setServings(4);
    guacRecipe.setSource("Simply Recipes");
    guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
    guacRecipe.setDifficulty(Difficulty.EASY);

//    Recipe guacRecipe = Recipe.builder()
//        .description("Perfect Guacamole")
//        .prepTime(10)
//        .cookTime(0)
//        .servings(4)
//        .source("Simply Recipe")
//        .url("https://www.simplyrecipes.com/recipes/perfect_guacamole/")
//        .difficulty(Difficulty.EASY)
//        .build();

    Note guacNote = new Note();
    guacNote.setRecipeNote(
        "Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).\n"
        + "Making Guacamole is easy\n"
        + "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato.\n"
        + "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!\n"
        + "Guacamole Tip: Use Ripe Avocados\n"
        + "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n"
        + "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");
    guacRecipe.setNote(guacNote);
    List<Ingredient> guacIngredientList = Arrays.asList(
        new Ingredient("ripe avocados", new BigDecimal(2), wholeUoM),
        new Ingredient("Kosher salt", new BigDecimal(1/2), teaspoonUoM),
        new Ingredient("fresh lime or lemon juice", new BigDecimal(1), tablespoonUoM),
        new Ingredient("minced red onion or thinly sliced green onion",
            new BigDecimal(1/4),
            cupUoM),
        new Ingredient("Serrano chiles, stems and seeds removed, miced",
            new BigDecimal(1),
            wholeUoM),
        new Ingredient("freshly grated black pepper", new BigDecimal(1), dashUoM),
        new Ingredient("tomato, seeds and pulp removed, chopped", new BigDecimal(1/2),
            wholeUoM)
    );
    guacRecipe.addIngredients(guacIngredientList);
    guacRecipe.getCategories().add(mexicanCategory);
    guacRecipe.setDirections(
        "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
        + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n"
        + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
        + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
        + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
        + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
        + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

    Recipe chickenTacoRecipe = new Recipe();
    chickenTacoRecipe.setDescription("Spicy Grilled Chicken Tacos");
    chickenTacoRecipe.setPrepTime(20);
    chickenTacoRecipe.setCookTime(15);
    chickenTacoRecipe.setServings(6);
    chickenTacoRecipe.setSource("Simply Recipes");
    chickenTacoRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
    chickenTacoRecipe.setDifficulty(Difficulty.MODERATE);

//    Recipe chickenTacoRecipe = Recipe.builder()
//        .description("Spicy Grilled chicken Tacos")
//        .prepTime(20)
//        .cookTime(15)
//        .servings(6)
//        .source("Simply Recipes")
//        .url("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/")
//        .difficulty(Difficulty.MODERATE)
//        .build();

    Note chickenTacoRecipeNote = new Note();
    chickenTacoRecipeNote.setRecipe(chickenTacoRecipe);
    chickenTacoRecipeNote.setRecipeNote(
        "We have a family motto and it is this: Everything goes better in a tortilla.\n"
        + "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n"
        + "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n"
        + "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n"
        + "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n"
        + "Spicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n"
        + "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n"
        + "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n"
        + "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!\n");

    chickenTacoRecipe.setNote(chickenTacoRecipeNote);
    List<Ingredient> chickenTacoIngreddientList = Arrays.asList(
        new Ingredient("ancho chili powder", new BigDecimal(2), teaspoonUoM),
        new Ingredient("dried oregano", new BigDecimal(1), teaspoonUoM),
        new Ingredient("dried cumin", new BigDecimal(1), teaspoonUoM),
        new Ingredient("sugar", new BigDecimal(1), teaspoonUoM),
        new Ingredient("salt", new BigDecimal(1/2), teaspoonUoM),
        new Ingredient("clove garlic, finaly chopped", new BigDecimal(1),
            wholeUoM),
        new Ingredient("finely grated orane zest", new BigDecimal(1),
            tablespoonUoM),
        new Ingredient("fresh-squeezed orange juice", new BigDecimal(3),
            tablespoonUoM),
        new Ingredient("olive oil", new BigDecimal(2), tablespoonUoM),
        new Ingredient("skinless, boneless chicken thighs", new BigDecimal(5),
            wholeUoM),
        new Ingredient("small corn tortillas", new BigDecimal(8), wholeUoM),
        new Ingredient("packed baby arugala", new BigDecimal(3), cupUoM),
        new Ingredient("ripe avocados, sliced", new BigDecimal(2), wholeUoM),
        new Ingredient("radishes, thinly slice", new BigDecimal(4), wholeUoM),
        new Ingredient("cherry tomatoes, halved", new BigDecimal(1/2), pintUoM),
        new Ingredient("red onion, thinly sliced", new BigDecimal(1/2),
            wholeUoM),
        new Ingredient("roughly chopped cilantro", new BigDecimal(1), wholeUoM),
        new Ingredient("sour cream thinned wth same amount of milk",
            new BigDecimal(1/2), cupUoM),
        new Ingredient("lime, cut into wedges", new BigDecimal(1), wholeUoM)
    );
    chickenTacoRecipe.addIngredients(chickenTacoIngreddientList);
    chickenTacoRecipe.getCategories().add(mexicanCategory);
    chickenTacoRecipe.setDirections(
        "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n"
        + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
        + "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n"
        + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
        + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
        + "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
        + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n");

    return Arrays.asList(guacRecipe, chickenTacoRecipe);
  }
}
