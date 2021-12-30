package com.findmyroom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnSortedByLow() throws Exception {
		mockMvc.perform(get("/api/search?filter=&value=&sortBy=low")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(4)))
			.andExpect(jsonPath("$[0].price", is(1700)))
			.andExpect(jsonPath("$[1].price", is(1800)))
			.andExpect(jsonPath("$[2].price", is(1800)))
			.andExpect(jsonPath("$[3].price", is(1950)));
		mockMvc.perform(get("/api/search?sortBy=low")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(4)))
			.andExpect(jsonPath("$[0].price", is(1700)))
			.andExpect(jsonPath("$[1].price", is(1800)))
			.andExpect(jsonPath("$[2].price", is(1800)))
			.andExpect(jsonPath("$[3].price", is(1950)));
	}
	
	@Test
	public void shouldReturnFiltered() throws Exception {
		mockMvc.perform(get("/api/search?filter=numBeds&value=4&sortBy=")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].numBeds", is(4)))
			.andExpect(jsonPath("$[1].numBeds", is(4)));
		mockMvc.perform(get("/api/search?filter=numBeds&value=4")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].numBeds", is(4)))
			.andExpect(jsonPath("$[1].numBeds", is(4)));
	}
	
	@Test
	public void shouldReturnFilteredAndSortedByHigh() throws Exception {
		mockMvc.perform(get("/api/search?filter=numBeds&value=4&sortBy=high")).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].numBeds", is(4)))
			.andExpect(jsonPath("$[0].price", is(1800)))
			.andExpect(jsonPath("$[1].numBeds", is(4)))
			.andExpect(jsonPath("$[1].price", is(1700)));
	}
}
