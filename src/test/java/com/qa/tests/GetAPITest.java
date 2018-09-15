package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
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
	public void getAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		closeableHttpResponse = restClient.get(url, headerMap);

		// Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// Response String
		HttpEntity httpEntity = closeableHttpResponse.getEntity();
		String json_string = EntityUtils.toString(httpEntity, "UTF-8");

		JSONObject jsonResponse = new JSONObject(json_string);
		System.out.println("JSON Response from API-->" + jsonResponse);

		String perPage_Value = TestUtil.getValueByJPath(jsonResponse, "/per_page");
		System.out.println("Value of per page--->" + perPage_Value);
		Assert.assertEquals(Integer.parseInt(perPage_Value), 3, "per_page value is not 3");

		System.out.println(TestUtil.getValueByJPath(jsonResponse, "/data[0]/first_name"));

		// All headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();

		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array-->" + allHeaders);
		System.out.println("Cookie in Header--> " + allHeaders.get("Set-Cookie"));
	}
}
