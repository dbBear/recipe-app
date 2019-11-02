package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping({"/recipe"})
@Controller
public class RecipeController {

  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping("/{id}/show")
  public String showById(@PathVariable String id, Model model) {
    log.debug("I'm in RecipeController");

    model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
    return "recipe/show";
  }

  @RequestMapping("/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }

  @RequestMapping("/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model) {
    model.addAttribute("recipe",
        recipeService.findCommandById(Long.valueOf(id)));
    return "recipe/recipeform";
  }

//  @RequestMapping(name = "recipe", method = RequestMethod.POST)
  @PostMapping
  @RequestMapping("")
  public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
    RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }
}
