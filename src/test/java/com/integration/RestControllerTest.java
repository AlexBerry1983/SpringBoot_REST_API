package com.integration;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


class RestControllerTest extends SpringbootrestapiApplicationTests {
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testGetJournals() throws Exception {
		mockMvc.perform(get("/journals"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"));
	}
	
	@Test
	void testGetJournalsById() throws Exception {
		mockMvc.perform(get("/journals/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("Journal of history"))
			.andExpect(jsonPath("$.status").value("processed successfully"))
			.andExpect(jsonPath("$.createdOn").value("13/09/22"))
			.andExpect(jsonPath("$.lastModified").value("14/09/22"));
	}
	
	@Test
	void testPostNewJournal() throws Exception {
        String journalAsJson = "{\"message\": \"journal of cake\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/journals")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(journalAsJson);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isAccepted())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("journal of cake"))
			.andExpect(jsonPath("$.status").value("To Process"));
	}
	
	@Test
	void testUpdateJournalMessage() throws Exception {
        String journalAsJson = "{\"message\": \"journal of music\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/journals/2")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .queryParam("paramType", "message")
            .content(journalAsJson);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("journal of music"))
			.andExpect(jsonPath("$.status").value("failed"))
			.andExpect(jsonPath("$.createdOn").value("10/09/22"))
			.andExpect(jsonPath("$.lastModified").value("11/09/22"));
	}
	
	@Test
	void testDeleteJournalsById() throws Exception {
		mockMvc.perform(delete("/journals/3"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("Journal of science"))
			.andExpect(jsonPath("$.status").value("processing"))
			.andExpect(jsonPath("$.createdOn").value("12/09/22"))
			.andExpect(jsonPath("$.lastModified").value("12/09/22"));
	}
}
