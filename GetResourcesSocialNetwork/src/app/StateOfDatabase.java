package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import facebook.ConstantsFacebook;
import facebook4j.FacebookException;

public class StateOfDatabase {

	public static void main(String[] args) throws IOException {
		File data = new File("data");

		for(File website : data.listFiles()) {
			System.out.println("+++++++++++++++++++++++++++");
			System.out.println(website);
			System.out.println("+++++++++++++++++++++++++++");

			int cptTotal = 0;
			for(File page : website.listFiles()) {
				int cpt = 0;
				for(File part : page.listFiles()) {
					cpt += partSize(part);
				}
				cptTotal += cpt;
				try {
					System.out.println(page.getName().split("/")[page.getName().split("/").length-1] + "\t"+ConstantsFacebook.getPageName(page.getName().split("/")[0])+"\t" + cpt);
				} catch (FacebookException e) {
					System.out.println(page + "\t?\t" + cpt);
				}
			}
			System.out.println("TOTAL website "+website+" : "+cptTotal);
		}
	}

	private static int partSize(File part) throws IOException {
		int cpt = 0;
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(part); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				if(ligne.startsWith("PostJSONImpl") || ligne.startsWith("StatusJSONImpl"))
					cpt++;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return cpt;	
	}

}
