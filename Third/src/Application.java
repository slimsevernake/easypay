import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.portable.OutputStream;

public class Application {
	
	public static void main(String[] args) throws IOException, JSONException {
		
		URL baseUrl = new URL("https://code-api-staging.easypayfinance.com/api/");
		
		System.out.println("Logging in...");
		try {
	        URL url = new URL(baseUrl + "Authentication/login");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization","Bearer {token}");
	        conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("Accept", "*/*");
			conn.setDoOutput(true);
			String data = "{\n \"username\":\"user\",\"password\":\"pass\"}\n";
			try(java.io.OutputStream os = conn.getOutputStream()) {
				byte[] input = data.getBytes("utf-8");
				os.write(input, 0, input.length);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println(response.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Verifying end point response = 200...");
		try {
			URL url2 = new URL(baseUrl + "Application/all");
			HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");
			assertTrue(conn.getResponseCode() == 200, "Error! Invalid response code.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
}
