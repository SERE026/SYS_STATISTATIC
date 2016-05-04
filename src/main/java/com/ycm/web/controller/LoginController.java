package com.ycm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login")
	public String index(HttpServletRequest request,HttpServletResponse response,ModelAndView model) {
		model.addObject("test", "test-ok");
		return "index";
	}
}
