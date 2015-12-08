package facebook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import database.Database;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.Page;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;

public class FacebookApp extends Thread {

	private Facebook facebook;
	private Like like;
	
	public FacebookApp() {
		// Generate facebook instance.
		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(ConstantsFacebook.APP_ID, ConstantsFacebook.APP_SECRET);
		// Set access token.
		AccessToken token = new AccessToken(ConstantsFacebook.ACCESS_TOKEN_STRING);
		facebook.setOAuthAccessToken(token);
	}



	public FacebookApp(Like l) throws FacebookException {
		this();
		this.like = l;
	}
	
	
	public void run() {
		//*
		Database db = new Database();
		Page page;
		try {
			page = facebook.getPage(like.getId());
			db.addFacebookPage(page);
			ResponseList<Post> feed = facebook.getFeed(page.getId());

			//*
			do{
				for(Post p : feed) {
					db.addFacebookPost(p, "page", page.getId());
				}

				Paging<Post> paging = feed.getPaging();
				feed = facebook.fetchNext(paging);
			}while(feed.size() > 0);
			//*/
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		
	}

public void queryGroup(String idGroup, long idMin) throws IOException, FacebookException {

	System.out.println("--> Group "+idGroup + " { idMin="+idMin+ " }");
	ResponseList<Post> feed = facebook.getGroupFeed(idGroup);
	// if the repertory of the folder is missing
	//*
	File repertory = new File("data/facebook/");


	long idMaxFeed = -1;
	for(Post p : feed)
		if(idMaxFeed < Long.parseLong(p.getId().split("_")[1])) {
			idMaxFeed = Long.parseLong(p.getId().split("_")[1]);
		}

	if (idMaxFeed <= idMin)
		return;

	FileWriter fw = new FileWriter ("data/facebook/" + idGroup + "/" + idMaxFeed + ".json");

	//*/
	int cpt = 0;

	do{
		//*
		for(Post p : feed) {
			if (
					Long.parseLong(p.getId().split("_")[1]) > idMin && 
					p.getMessage()!=null) {
				fw.write(p+"\n");
				//System.out.println(writeFacebookPost(p));

				cpt++;
			}
		}
		//*/

		Paging<Post> paging = feed.getPaging();
		feed = facebook.fetchNext(paging);
		idMaxFeed = -1;
		for(Post p : feed)
			if(idMaxFeed < Long.parseLong(p.getId().split("_")[1])) {
				idMaxFeed = Long.parseLong(p.getId().split("_")[1]);
			}
		System.out.println("\t\t\t\t\t"+idGroup + " => "+cpt);
	}while(feed.size() > 0 && idMaxFeed > idMin);
	fw.close();
	System.out.println(idGroup + " : "+cpt);
}

public User getMe() throws FacebookException {
	return facebook.getMe();
}



public Page getPage(String string) throws FacebookException {
	return facebook.getPage(string);
}



public ResponseList<Like> getLikes() throws FacebookException {
	return facebook.getUserLikes();
}



public ResponseList<Like> getNextLikes(Paging<Like> paging) throws FacebookException {
	return facebook.fetchNext(paging);
}
}
