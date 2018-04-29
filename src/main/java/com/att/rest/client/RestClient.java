package com.att.rest.client;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {

	public static void main(String[] args) {

		URL url;
		try {
			url = new URL(args[0]);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setRequestMethod("GET");

			httpURLConnection.setRequestProperty("Accept", "application/json");

			int statusCode = httpURLConnection.getResponseCode();

			if (statusCode == 200) {

				ObjectMapper mapper = new ObjectMapper();

				ArrayList<Map<String, ArrayList<Integer>>> jsonResponse = mapper
						.readValue(httpURLConnection.getInputStream(), ArrayList.class);

				int executionTotal = 0;
				for (Map<String, ArrayList<Integer>> jsonDocument : jsonResponse)
					for (Map.Entry<String, ArrayList<Integer>> record : jsonDocument.entrySet()) {
						if (record.getKey().equalsIgnoreCase("numbers")) {
							int total = 0;
							for (Integer arrayValue : record.getValue()) {
								total += arrayValue;
							}
							System.out.println(total);
							executionTotal += total;
						}
					}
				;
				System.out.println("Execution Total : " + executionTotal);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
