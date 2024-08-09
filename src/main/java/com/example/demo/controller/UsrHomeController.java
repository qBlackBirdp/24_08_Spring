package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {

	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<String> getList() {
		List<String> list = new ArrayList<>();
		list.add("철수 나이");
		list.add("영수 나이");
		return list;
	}

	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("철수나이", 11);
		map.put("영수나이", 12);

		return map;
	}

	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public double getDouble() {
		return 3.14;
	}

	@RequestMapping("/usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {
		return true;
	}

	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() {
		return "abc";
	}

	@RequestMapping("/usr/home/getInt")
	@ResponseBody
	public int getInt() {
		return 1;
	}

}

