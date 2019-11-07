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

  @GetMapping("/{id}/show")
  public String showById(@PathVariable String id, Model model) {
    log.debug("I'm in RecipeController");

      model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
    return "recipe/show";
  }

  @GetMapping("/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }

  @GetMapping("/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model) {
    model.addAttribute("recipe",
        recipeService.findCommandById(Long.valueOf(id)));
    return "recipe/recipeform";
  }

//  @RequestMapping(name = "recipe", method = RequestMethod.POST)
  @PostMapping({"", "/"})
  public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
    RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }

  @GetMapping("/{id}/delete")
  public String deleteById(@PathVariable String id) {
    log.debug("Deleting id: " + id);
    recipeService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }

//  @ExceptionHandler(NotFoundException.class)
//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  public ModelAndView handleNotFound(Exception e) {
//    log.error("Handling not found exception");
//    log.error(e.getMessage());
//
//    ModelAndView modelAndView = new ModelAndView("errorPage");
////    modelAndView.setViewName("errorPage");
//    modelAndView.addObject("errorCode", HttpStatus.NOT_FOUND);
//    modelAndView.addObject("exception", e);
//    return modelAndView;
//  }

//  @ExceptionHandler(NumberFormatException.class)
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  public ModelAndView handleNumberFormat(Exception e) {
//    log.error("Handling Number Format Exception");
//    log.error(e.getMessage());
//
//    ModelAndView mv = new ModelAndView("errorPage");
////    mv.setViewName("errorPage");
//    mv.addObject("errorCode", HttpStatus.BAD_REQUEST);
//    mv.addObject("exception", e);
//    return mv;
//  }
}
