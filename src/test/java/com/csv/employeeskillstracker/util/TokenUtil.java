package com.csv.employeeskillstracker.util;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Configuration
public class TokenUtil {

	@Autowired
	MockMvc mockMvc;
	
	public String obtainAccessToken(String username, String password) throws Exception {

		ResultActions result = mockMvc
				.perform(post("/oauth/token").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
						.param("grant_type", "password").param("username", username).param("password", password)
						.with(httpBasic("csv-client", "csv-secret")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

}
