package com.findmyroom;

import static org.hamcrest.CoreMatchers.containsString;
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
public class FavoriteControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldAddFavorite() throws Exception {
		mockMvc.perform(get("/api/favoriteSublease?userID=5&subleaseID=2")).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(get("/api/getProfile?userID=5")).andExpect(status().isOk())
			.andExpect(jsonPath("$.favoritedSubleases", hasSize(1)))
			.andExpect(jsonPath("$.favoritedSubleases[0].address", containsString("4321 1st St")));
	}
	
	@Test
	public void shouldRemoveFavorite() throws Exception {
		mockMvc.perform(get("/api/unfavoriteSublease?userID=3&subleaseID=1")).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(get("/api/getProfile?userID=3")).andExpect(status().isOk())
			.andExpect(jsonPath("$.favoritedSubleases", hasSize(0)));
	}
}
