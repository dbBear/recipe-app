package com.ambear.recipeapp.controllers;

import com.ambear.recipeapp.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView handleNotFound(Exception e) {
    log.error("Handling not found exception");
    log.error(e.getMessage());

    ModelAndView modelAndView = new ModelAndView("errorPage");
//    modelAndView.setViewName("errorPage");
    modelAndView.addObject("errorCode", HttpStatus.NOT_FOUND);
    modelAndView.addObject("exception", e);
    return modelAndView;
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleNumberFormat(Exception e) {
    log.error("Handling Number Format Exception");
    log.error(e.getMessage());

    ModelAndView mv = new ModelAndView("errorPage");
//    mv.setViewName("errorPage");
    mv.addObject("errorCode", HttpStatus.BAD_REQUEST);
    mv.addObject("exception", e);
    return mv;
  }

}
