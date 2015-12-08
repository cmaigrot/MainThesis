import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class AppTemp {

	public static void main(String[] argv) {
		new AppTemp();
	}

	public AppTemp() {
		String text = readFile("/Users/cmaigrot/Documents/datasets/d24sample/en-annotations.json");

		int cpt = 0;
		int cptcertain = 0;
		int cptuncertain = 0;
		int cptferguson = 0;
		int cptottawashooting = 0;
		int cptebola = 0;

		for(String line : text.split("\n")) {
			System.out.println("-------------------");
			cpt++;
			for(String attribute : line.split("\",\"")) {
				System.out.println(attribute);
				if(attribute.startsWith("certainty"))
					switch(attribute.split("\":\"")[1]) {
					case "certain" : cptcertain++; break;
					case "uncertain" : cptuncertain++; break;
					}
				else if(attribute.startsWith("event"))
					switch(attribute.split("\"}")[0].split("\":\"")[1]) {
					case "ferguson" : cptferguson++; break;
					case "ottawashooting" : cptottawashooting++; break;
					case "ebola-essien" : cptebola++; break;
					}
			}
		}
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println("cpt "+cpt);
		System.out.println("-------------------");
		System.out.println("cptcertain "+cptcertain);
		System.out.println("cptuncertain "+cptuncertain);
		System.out.println("-------------------");
		System.out.println("cptferguson "+cptferguson);
		System.out.println("cptottawashooting "+cptottawashooting);
		System.out.println("cptebola "+cptebola);
	}





	/* ********************************************
	 * FUNCTIONS CONSTANTS
	/* ********************************************
	 */
	public static String readFile(String filename) {
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
			System.out.println("Size of the file : "+filename);
			System.out.println("\t"+cptLines);
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return respond;
	}


}
