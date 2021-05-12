package com.tgxyear.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e ,Model model){
        ModelAndView mv  =new ModelAndView();
        mv.addObject("url1",request.getRequestURL());
        mv.addObject("Exception1",e);
       model.addAttribute("url",request.getRequestURL());
       model.addAttribute("Exception",e);
       mv.setViewName("error/error");
        return mv;
    }
}
