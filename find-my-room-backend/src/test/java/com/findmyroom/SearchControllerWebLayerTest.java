package com.findmyroom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.findmyroom.controller.SearchController;
import com.findmyroom.model.Image;
import com.findmyroom.model.Sublease;
import com.findmyroom.service.SubleaseService;

@WebMvcTest(SearchController.class)
public class SearchControllerWebLayerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SubleaseService service;
	
	private static Sublease sublease1;
	private static Sublease sublease2;
	private static Sublease sublease3;
	
	@BeforeAll
	public static void setupMock() {
		sublease1 = new Sublease();
		sublease1.setSubleaseID(1);
		sublease1.setAuthorID(1);
		sublease1.setAddress("Testing Center #1, CA");
		sublease1.setDirection("south");
		sublease1.setSqFootage(1250);
		sublease1.setPrice(2000);
		sublease1.setNumBeds(4);
		sublease1.setDateAvailability(new Timestamp(1619066756));
		List<Image> images = new ArrayList<Image>();
		images.add(new Image(1, "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII="));
		images.add(new Image(1, "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z/C/HgAGgwJ/lK3Q6wAAAABJRU5ErkJggg=="));
		sublease1.setImages(images);
		
		sublease2 = new Sublease();
		sublease2.setSubleaseID(2);
		sublease2.setAuthorID(1);
		sublease2.setAddress("Testing Center #2, CA");
		sublease2.setDirection("north");
		sublease2.setSqFootage(900);
		sublease2.setPrice(2500);
		sublease2.setNumBeds(3);
		
		sublease3 = new Sublease();
		sublease3.setSubleaseID(3);
		sublease3.setAuthorID(2);
	}
	
	@BeforeEach
	public void setupMockBehavior() {
		List<Sublease> subleases = new ArrayList<>();
		subleases.add(sublease1);
		subleases.add(sublease2);
		subleases.add(sublease3);
		when(service.getAllSubleases()).thenReturn(subleases);
		when(service.filter(anyString(), anyString())).thenReturn(Collections.singletonList(sublease2));
		when(service.filter(isNull(), isNull())).thenReturn(Collections.singletonList(sublease2));
	}
	
	@Test
	public void searchShouldReturnAllSubleasesFromServiceIfNoParameters() throws Exception {
		mockMvc.perform(get("/api/search?filter=&value=&sortBy=")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].price", is(2000)))
				.andExpect(jsonPath("$[0].images[1].src", containsString("HgAGgwJ")))
				.andExpect(jsonPath("$[1].authorID", is(1)))
				.andExpect(jsonPath("$[2].address", is(nullValue())));
		mockMvc.perform(get("/api/search")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$[0].price", is(2000)))
		.andExpect(jsonPath("$[0].images[1].src", containsString("HgAGgwJ")))
		.andExpect(jsonPath("$[1].authorID", is(1)))
		.andExpect(jsonPath("$[2].address", is(nullValue())));
	}
	
	@Test
	public void searchShouldFilterIfFilterParameters() throws Exception {
		mockMvc.perform(get("/api/search?filter=numBeds&value=3&sortBy=")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].numBeds", is(3)));
	}
}
