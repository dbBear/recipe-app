package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.commands.IngredientCommand;
import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.commands.UomCommand;
import com.ambear.recipeapp.services.IngredientService;
import com.ambear.recipeapp.services.RecipeService;
import com.ambear.recipeapp.services.UomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UomService uomService;

  public IngredientController(
      RecipeService recipeService,
      IngredientService ingredientService,
      UomService uomService)
  {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.uomService = uomService;
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("Getting ingredient list for recipe id: " + recipeId);

    //use command object to avoid lazy load errors in Thymeleaf
    model.addAttribute("recipe",
        recipeService.findCommandById(Long.valueOf(recipeId)));
    return "recipe/ingredient/list";
  }

  @GetMapping
  @RequestMapping("recipe/{recipeId}/ingredient/new")
  public String newRecipe(@PathVariable String recipeId, Model model) {
    RecipeCommand recipeCommand =
        recipeService.findCommandById(Long.valueOf(recipeId));
    //todo raise exception if null

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(Long.valueOf(recipeId));
    model.addAttribute("ingredient", ingredientCommand);

    //set uom
    ingredientCommand.setUom(new UomCommand());
    model.addAttribute("uomList", uomService.listAllUoms());

    return "recipe/ingredient/ingredientform";
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
  public String updateRecipeIngredient(
      @PathVariable String recipeId,
      @PathVariable String ingredientId,
      Model model)
  {
    model.addAttribute("ingredient",
        ingredientService.findByRecipeIdAndIngredientId(
            Long.valueOf(recipeId),
            Long.valueOf(ingredientId)));
    model.addAttribute("uomList", uomService.listAllUoms());
    return "recipe/ingredient/ingredientform";
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
  public String showRecipeIngredient(
      @PathVariable String recipeId,
      @PathVariable String ingredientId,
      Model model)
  {
    model.addAttribute("ingredient",
        ingredientService.findByRecipeIdAndIngredientId(
            Long.valueOf(recipeId),
            Long.valueOf(ingredientId)));
    return "recipe/ingredient/show";
  }

  @GetMapping
  @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
  public String deleteIngredient(
      @PathVariable String recipeId,
      @PathVariable String ingredientId)
  {
    log.debug("Deleting ingredient id:" + ingredientId +
        " form recipe id:" + recipeId);
    ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId),
        Long.valueOf(ingredientId));

    return "redirect:/recipe/" + recipeId + "/ingredients";
  }

  @PostMapping("/recipe/{recipeId}/ingredient")
  public String saveOrUpdateIngredient(
      @ModelAttribute IngredientCommand ingredientCommand,
      Model model)
  {
    IngredientCommand savedIngredientCommand =
        ingredientService.saveIngredientCommand(ingredientCommand);

    log.debug("saved recipe id:" + savedIngredientCommand.getRecipeId());
    log.debug("savedingredient id:" + savedIngredientCommand.getId());

    return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() +
        "/ingredients";
  }
}
