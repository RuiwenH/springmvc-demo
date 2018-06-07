package com.reven.springmvc.service.impl;

import org.springframework.stereotype.Service;

import com.reven.springmvc.service.IHelloService;

@Service
public class HelloServiceImpl implements IHelloService {

	public String sayWord(String name) {
		if (name != null) {
			return "hello world!";
		}
		return name + "hello world!";
	}
}
