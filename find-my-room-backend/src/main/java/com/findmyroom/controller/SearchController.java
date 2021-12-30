package com.findmyroom.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.findmyroom.model.Sublease;
import com.findmyroom.service.SubleaseService;

@RestController
@RequestMapping(path = "/api")
public class SearchController {

	@Autowired
	private SubleaseService subleaseService;

	/**
	 * Get a particular sublease.
	 * @param response
	 * @param id
	 * @return JSON serialized sublease matching the id, or null if it doesn't exist
	 */
	@GetMapping(path = "/getSublease")
	public @ResponseBody Sublease getSublease(HttpServletResponse response, @RequestParam Integer id) {
		return subleaseService.getSublease(id);
	}

	/**
	 * Get all subleases.
	 * @return JSON serialized list of all subleases
	 */
	@GetMapping(path = "/allSubleases")
	public @ResponseBody Iterable<Sublease> getAllSubleases() {
		return subleaseService.getAllSubleases();
	}
	
	/**
	 * Search for a sublease with optional filtering and sorting parameters.
	 * @param response
	 * @param filter
	 * @param value
	 * @param sortBy
	 * @return JSON serialized list of all subleases matching the provided filter and sorted by price in the provided order
	 */
	@GetMapping(path = "/search")
	public @ResponseBody Iterable<Sublease> browse(HttpServletResponse response,
			@RequestParam(required = false) String filter, @RequestParam(required = false) String value,
			@RequestParam(required = false) String sortBy) {
		if ((filter == null || value == null || filter.equals("") || value.equals(""))
				&& (sortBy == null || sortBy.equals(""))) {
			// missing both
			return subleaseService.getAllSubleases();
		} else if (filter == null || value == null || filter.equals("") || value.equals("")) {
			// no filter, just sort
			return subleaseService.sortSubleases(sortBy);
		} else if (sortBy == null || sortBy.equals("")) {
			// no sort, just filter
			return subleaseService.filter(filter, value);
		}
		return subleaseService.filterAndSort(filter, value, sortBy);
	}
}
