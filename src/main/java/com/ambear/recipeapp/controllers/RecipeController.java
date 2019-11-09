package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.commands.RecipeCommand;
import com.ambear.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping({"/recipe"})
@Controller
public class RecipeController {
   private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping("/{id}/show")
  public String showById(@PathVariable String id, Model model) {
    log.debug("I'm in RecipeController");

      model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
    return "recipe/show";
  }

  @GetMapping("/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    return RECIPE_RECIPEFORM_URL;
  }

  @GetMapping("/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model) {
    model.addAttribute("recipe",
        recipeService.findCommandById(Long.valueOf(id)));
    return RECIPE_RECIPEFORM_URL;
  }

//  @RequestMapping(name = "recipe", method = RequestMethod.POST)
//  @PostMapping({"", "/"})
//  public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
//    RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
//
//    return "redirect:/recipe/" + savedCommand.getId() + "/show";
//  }

  @PostMapping({"","/"})
  public String saveOrUpdate(
      @Valid @ModelAttribute("recipe") RecipeCommand command,
      BindingResult result)
  {
    if(result.hasErrors()) {
      result.getAllErrors().forEach(error -> {
        log.debug(error.toString());
      });
      return RECIPE_RECIPEFORM_URL;
    }
    RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }

  @GetMapping("/{id}/delete")
  public String deleteById(@PathVariable String id) {
    log.debug("Deleting id: " + id);
    recipeService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }
}
