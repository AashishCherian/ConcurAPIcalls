package com.concur.apicalls.auth;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Authorize 
{
    public static void main( String[] args )
    {
		try {
			URL url = new URL("https://implementation.concursolutions.com/net2/oauth2/accesstoken.ashx");

			String username = "yourUserName"; //put your username  
			String password = "yourPassword"; //put your password
			String consumerKey = "yourConsumerKey"; // you can get your key from: yourApplication->Administration->Company->Web services->Register Partner Application 

			byte[] header = (username + ":" + password).getBytes(StandardCharsets.UTF_8);

			String auth = Base64.getEncoder().encodeToString(header);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + auth);
			connection.setRequestProperty("X-ConsumerKey", consumerKey);
			InputStream content = (InputStream) connection.getInputStream();

			DocumentBuilderFactory dbfo = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbo = dbfo.newDocumentBuilder();
			Document doc = dbo.parse(content);

			NodeList list = doc.getElementsByTagName("Token");
			System.out.println(list.item(0).getTextContent());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
