package com.findmyroom.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.findmyroom.service.FavoriteService;

@RestController
@RequestMapping(path = "/api")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	@GetMapping(path = "/favoriteSublease")
	public @ResponseBody void favoriteSublease(HttpServletResponse response, @RequestParam Integer userID,
			@RequestParam Integer subleaseID) {
		favoriteService.favoriteSublease(userID, subleaseID);
	}

	@GetMapping(path = "/unfavoriteSublease")
	public @ResponseBody void unfavoriteSublease(HttpServletResponse response, @RequestParam Integer userID,
			@RequestParam Integer subleaseID) {
		favoriteService.unfavoriteSublease(userID, subleaseID);
	}
}
