package com.ambear.recipeapp.services;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.converters.IngredientCommandToIngredient;
import com.ambear.recipeapp.converters.IngredientToIngredientCommand;
import com.ambear.recipeapp.domain.Ingredient;
import com.ambear.recipeapp.domain.Recipe;
import com.ambear.recipeapp.repositories.RecipeRepository;
import com.ambear.recipeapp.repositories.UomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
  private final RecipeRepository recipeRepository;
  private final UomRepository uomRepository;

  public IngredientServiceImpl(
      IngredientToIngredientCommand ingredientToIngredientCommand,
      IngredientCommandToIngredient ingredientCommandToIngredient,
      RecipeRepository recipeRepository,
      UomRepository uomRepository)
  {
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.recipeRepository = recipeRepository;
    this.uomRepository = uomRepository;
  }

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(
      Long recipeId,
      Long ingredientId)
  {
//    Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
//    if(!optionalRecipe.isPresent()) {
//      log.error("recipe is not found. Id: " + recipeId);
//    }

    //todo impl error handling
    Recipe recipe = recipeRepository
        .findById(recipeId)
        .orElseThrow(
            () -> new RuntimeException("No Recipe Found")
        );

    return recipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientId))
        .map(ingredientToIngredientCommand::convert)
        .findFirst()
        .orElseThrow(
            () -> new RuntimeException("No Ingredient Found")
        );
  }

  @Override
  @Transactional
  public void deleteByRecipeIdAndIngredientId(
      Long recipeId,
      Long ingredientId)
  {
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if(!recipeOptional.isPresent()) {
      //todo toss error if not found!
      log.error("Recipe not found for id: " + recipeId);
    } else {
      Recipe detachedRecipe = recipeOptional.get();
      Optional<Ingredient> ingredientOptional = detachedRecipe.getIngredients()
          .stream()
          .filter(ingredient -> ingredient.getId().equals(ingredientId))
          .findFirst();
      if(ingredientOptional.isPresent()) {
        Ingredient ingredientToDelete = ingredientOptional.get();
        ingredientToDelete.setRecipe(null);
        detachedRecipe.getIngredients()
            .removeIf(ingredient -> ingredient.getId().equals(ingredientId));
      } else {
        log.error("Ingredient id: " + ingredientId + " not found for recipe " +
            "id: " + recipeId);
      }
      recipeRepository.save(detachedRecipe);
    }
  }

  @Override
  @Transactional
  public IngredientCommand saveIngredientCommand(
      IngredientCommand ingredientCommand)
  {
    Optional<Recipe> recipeOptional =
        recipeRepository.findById(ingredientCommand.getRecipeId());

    if(!recipeOptional.isPresent()) {
      //todo toss error if not found!
      log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
      return new IngredientCommand();
    }

    Recipe recipe = recipeOptional.get();

    Optional<Ingredient> ingredientOptional = recipe
        .getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
        .findFirst();

    if(ingredientOptional.isPresent()) {
      Ingredient ingredientFound = ingredientOptional.get();
      ingredientFound.setDescription(ingredientCommand.getDescription());
      ingredientFound.setAmount(ingredientCommand.getAmount());
      ingredientFound.setUom(uomRepository
          .findById(ingredientCommand.getUom().getId())
          //todo address this error handling
          .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));


    } else {
      //add new ingredient
      Ingredient ingredient =
          ingredientCommandToIngredient.convert(ingredientCommand);
      ingredient.setRecipe(recipe);
      recipe.addIngredient(ingredient);
    }

    Recipe savedRecipe = recipeRepository.save(recipe);

    Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
        .stream()
        .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
        .findFirst();

    if(!savedIngredientOptional.isPresent()) {
      savedIngredientOptional = savedRecipe.getIngredients().stream()
          .filter(recipeIngredient ->
              recipeIngredient.getDescription().equals(
                  ingredientCommand.getDescription()))
          .filter(recipeIngredient ->
              recipeIngredient.getAmount().equals(
                  ingredientCommand.getAmount()))
          .filter(recipeIngredient ->
              recipeIngredient.getUom().getId().equals(
                  ingredientCommand.getUom().getId()))
          .findFirst();
    }

    return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

//    return ingredientToIngredientCommand.convert(
//        savedRecipe.getIngredients().stream()
//            .filter(recipeIngredient ->
//                recipeIngredient.getId().equals(ingredientCommand.getId()))
//            .findFirst()
//            .get()
//    );
  }
}
