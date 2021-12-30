package com.findmyroom.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.findmyroom.model.SubleaseImage;
import com.findmyroom.service.ImageService;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/api")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@RequestMapping(path = "/addImage")
	public void addImageToSublease(HttpServletRequest request, HttpServletResponse response, @RequestParam (required = false) Integer id, @RequestParam (required = false) String src) throws IOException {
		if (id != null && src != null) {
			imageService.addImageToSublease(id, src);
		} else {
			String requestData = request.getReader().lines().collect(Collectors.joining());
			Gson gson = new Gson();
			SubleaseImage subImage = gson.fromJson(requestData, SubleaseImage.class);
			imageService.addImageToSublease(subImage.getId(), subImage.getSrc());
		}
	}

	@GetMapping(path = "/removeImage")
	public void removeImageFromSublease(HttpServletResponse response, @RequestParam Integer id,
			@RequestParam Integer imageID) {
		imageService.removeImageFromSublease(id, imageID);
	}
}
