package com.reven.springmvc.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reven.springmvc.core.BaseController;
import com.reven.springmvc.service.IHelloService;

@Controller
@RequestMapping("/hello")
public class HelloController extends BaseController {
	// 发送短信
	@Resource
	private IHelloService helloService;

	@RequestMapping()
	public String index() {
		return "ajax";
	}

	@RequestMapping("/ajax")
	@ResponseBody
	public String ajax(String name) {
		System.out.println(name);
		return name + " ajax 异步请求demo";
	}

	@RequestMapping("/say")
	public String sayHi(Model model, String name) {
		model.addAttribute("name", name);
		return "helloPageJsp";
	}

	@RequestMapping("/say2")
	public String sayHi2(Model model, String name) {
		model.addAttribute("name", name);
		return "helloPageFtl";
	}

}
