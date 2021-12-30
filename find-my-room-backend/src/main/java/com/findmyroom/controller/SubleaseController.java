package com.findmyroom.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.findmyroom.service.SubleaseService;

@RestController
@RequestMapping(path = "/api")
public class SubleaseController {

	@Autowired
	private SubleaseService subleaseService;

	@GetMapping(path = "/addSublease")
	public @ResponseBody int addSublease(HttpServletResponse response, @RequestParam Integer userID,
			@RequestParam(required = false) String address, @RequestParam(required = false) String direction,
			@RequestParam(required = false) String sqFootage, @RequestParam(required = false) String price,
			@RequestParam(required = false) String numBeds, @RequestParam(required = false) String dateAvailability) {
		return subleaseService.addSublease(userID, address, direction, sqFootage, price, numBeds, dateAvailability);
	}

	@GetMapping(path = "/removeSublease")
	public @ResponseBody void removeSublease(HttpServletResponse response, @RequestParam Integer userID,
			@RequestParam Integer subleaseID) {
		subleaseService.removeSublease(userID, subleaseID);
	}
	
	@GetMapping(path = "/updateSublease")
	public void updateSublease(HttpServletResponse response, @RequestParam Integer id, @RequestParam String field,
			@RequestParam String value) {
		subleaseService.updateSublease(id, field, value);
	}
}
