package com.basic.sample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.basic.sample.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
class AttackControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Get list of attack types")
	public void getAttackTypes() throws Exception {
		mockMvc.perform(get("/api/v1/attack/type")
				.header("X-API-KEY", "sample0405&$2021_"))
			      .andExpect(status().isOk())
			      .andDo(print());
	}
}