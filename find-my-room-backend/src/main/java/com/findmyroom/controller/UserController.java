package com.findmyroom.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.findmyroom.model.Authentication;
import com.findmyroom.model.User;
import com.findmyroom.service.UserService;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/api") // all URLs here start with "/api"
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/login")
	public @ResponseBody int login(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) String email,
			@RequestParam(required=false) String password) throws IOException {
		if (email != null && password != null) {
			return userService.checkUser(email, password);
		}
		String requestData = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		Authentication auth = gson.fromJson(requestData, Authentication.class);
		return userService.checkUser(auth.getEmail(), auth.getPassword());
	}

	@RequestMapping(path = "/signup")
	public @ResponseBody int signup(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) String email,
			@RequestParam(required=false) String password) throws IOException {
		if (email != null && password != null) {
			return userService.addUser(email, password);
		}
		String requestData = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		Authentication auth = gson.fromJson(requestData, Authentication.class);
		return userService.addUser(auth.getEmail(), auth.getPassword());
	}

	@GetMapping(path = "/getProfile")
	public @ResponseBody User getProfile(HttpServletResponse response, @RequestParam Integer userID) {
		return userService.getUser(userID);
	}

	@GetMapping(path = "/getUser")
	public @ResponseBody User getUser(HttpServletResponse response, @RequestParam Integer userID) {
		return userService.getUserBasicInformation(userID);
	}

	@GetMapping(path = "/allUsers")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping(path = "/updateUser")
	public @ResponseBody boolean updateUser(HttpServletResponse response, @RequestParam User user) {
		return userService.updateUser(user);
	}
}