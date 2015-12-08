package app;

import java.io.File;
import java.io.IOException;

import facebook.FacebookApp;
import facebook4j.FacebookException;
import facebook4j.Like;
import facebook4j.Paging;
import facebook4j.ResponseList;
import twitter.TwitterApp;

public class AppGetResourcesSocialNetwork {

	public static void main(String[] argv) {
		System.out.println("-- START --");

		String[] listOfRepertoryToVerify = {"data","data/facebook","data/twitter"};

		for(String s : listOfRepertoryToVerify) {
			File file = new File(s);
			if(!file.exists())
				file.mkdir();
		}

		System.out.println("--------------------");
		System.out.println("--    KEYWORDS    --");
		System.out.println("--------------------");
		String[] listOfKeywords = {"hoax","fake"};
		System.out.println("Keywords : ");
		for(String keyword : listOfKeywords)
			System.out.print(keyword+" ; ");
		System.out.println();



		System.out.println("--------------------");
		System.out.println("--    TWITTER     --");
		System.out.println("--------------------");
		//*
		TwitterApp twitter = new TwitterApp();
		try {
			for(String keyword : listOfKeywords) {
				twitter.query(keyword,0);
			}
		} catch (IOException e) { e.printStackTrace(); }
		//*/

		System.out.println("--------------------");
		System.out.println("--    FACEBOOK    --");
		System.out.println("--------------------");
		 /*
		int cpt=0;
		try {
			FacebookApp facebook = new FacebookApp();
			ResponseList<Like> likes  = facebook.getLikes();
			do{
				for(Like l : likes) {
					new FacebookApp(l).start();
				}
				Paging<Like> paging = likes.getPaging();
				likes =  facebook.getNextLikes(paging);
			}while(likes.size() >0);
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		//*/
		System.out.println("-- END --");
	}

	private static long maxId(String website, String keyword) {
		File repertory = new File("data/"+website+"/"+keyword);
		long idMax = -1;
		if(repertory.exists()) {
			for(File f : repertory.listFiles()) {
				if(idMax < Long.parseLong(f.getName().split("\\.")[0])) {
					idMax = Long.parseLong(f.getName().split("\\.")[0]);
				}
			}
		}
		else{
			repertory.mkdir();
		}
		return idMax;
	}

}
