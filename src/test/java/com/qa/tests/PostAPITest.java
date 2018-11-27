package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl + apiUrl;
	}

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		// Jackson API :
		ObjectMapper mapper = new ObjectMapper();
		Users user = new Users("Deepak", "Automation Tester");

		// Object to json file conversion
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\Users.json"),
				user);

		// Object to json in String
		String userJsonString = mapper.writeValueAsString(user);
		System.out.println(userJsonString);

		closeableHttpResponse = restClient.post(url, userJsonString, headerMap);

		// Validate response from API
		// StatusCode
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, TestBase.RESPONSE_STATUS_CODE_201, " Status Code is not 201");

		// JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("The response from API is -->" + responseJSON);

		// Json to Java Object
		Users userResObj = mapper.readValue(responseString, Users.class);
		System.out.println(userResObj);

		Assert.assertEquals(userResObj.getName(), user.getName(), "Name does not match");

		Assert.assertEquals(userResObj.getJob(), user.getJob(), "Job does not match");

		System.out.println("User ID-->" + userResObj.getId());
		System.out.println("Created At-->" + userResObj.getCreatedAt());
	}
}
