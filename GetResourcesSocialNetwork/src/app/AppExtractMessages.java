package app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class AppExtractMessages {

	public static void main(String[] argv) {
		System.out.println(" -- Extraction BEGIN --");

		Database db = new Database();
		try {
			ArrayList<String> pagesIds = db.getPagesIds();
			ArrayList<String> postsIds;
			String URL;
			for(String idPage : pagesIds) {
				//*
				if(idPage.equals("383436601687889")) {
					postsIds = db.getPagesPostsIds(idPage);
					for(String idPost : postsIds) {
						URL = db.getPostURL(idPost);
						if(URL.contains("legorafi"))
							try {
								String[] array = readURL(URL);
								System.out.println("\t\t"+array[0]);
							} catch (IOException e) {
								e.printStackTrace();
							}
					}
				}
				//*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(" -- Extraction END --");
	}








	private static String readFile(String filename) {
		String respond = "";

		try{
			InputStream ips=new FileInputStream(filename); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			int cptLines=0;
			while ((line=br.readLine())!=null){
				cptLines++;
				respond+=line+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return respond;
	}


	private static String[] readURL(String url) throws IOException {

		URL oracle = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)oracle.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Language", "fr-FR"); 
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		//send Request 
		DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
		dataout.flush();
		dataout.close();

		//get response
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer response = new StringBuffer();

		String inputLine,text="";
		while ((inputLine = br.readLine()) != null) {
			text += inputLine+" ";
		}
		is.close();
		
		String[] array = new String[3];
		array[0] = text.split("<h1>")[1].split("</h1>")[0];
		array[1] = text.split("<h1>")[1].split("</h1>")[0];
		array[2] = text.split("<h1>")[1].split("</h1>")[0];
		return array;
	}
}
