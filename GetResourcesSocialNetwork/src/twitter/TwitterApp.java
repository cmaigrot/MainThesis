package twitter;

import java.io.IOException;
import java.util.ArrayList;

import database.Database;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.FriendsFollowersResources;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApp {

	private Twitter twitter;

	public TwitterApp() {
		twitter = connectToTwitterAPI();
	}

	/**
	 * Creates the connection to Twitter API.
	 * @return Twitter object for the current connection
	 */
	public static Twitter connectToTwitterAPI() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		/*please fill in your credentials*/
		cb.setDebugEnabled(true).setOAuthConsumerKey(ConstantsTwitter.CONSUMER_KEY)
		.setOAuthConsumerSecret(ConstantsTwitter.CONSUMER_SECRET)
		.setOAuthAccessToken(ConstantsTwitter.ACCESS_TOKEN)
		.setOAuthAccessTokenSecret(ConstantsTwitter.ACCESS_TOKEN_SECRET);

		cb.setJSONStoreEnabled(true);

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		return twitter;
	}

	public void query() throws IOException {
		FriendsFollowersResources list = twitter.friendsFollowers();
	}



	public void query(String keyword) throws IOException {
		query(keyword,-1);
	}

	public void query(String keyword, long idMin) throws IOException {
		Query query = new Query(""+keyword);
		query.count(100);
		query.lang("fr");

		Database db = new Database();
		//System.out.println("idMin = "+idMin);


		ArrayList<Status> listOfMessage = new ArrayList<Status>();
		int cpt=0;
		try {
			boolean firstPage = true;
			QueryResult listOfStatus = twitter.search(query);
			//if (listOfStatus.getMaxId() <= idMin)
			//	return;
			//FileWriter fw = new FileWriter ("data/twitter/"+keyword+"/"+listOfStatus.getMaxId()+".json");
			do {
				if(firstPage)
					firstPage = false;
				else
					listOfStatus = twitter.search(listOfStatus.nextQuery());

				for(Status s : listOfStatus.getTweets()) {
					if (s.getId() <= idMin) {
						//fw.close();
						System.out.println(cpt+" new(s) message(s) from Twitter about \""+keyword+"\"");
						return;
					}

					if(!s.getText().startsWith("RT ")) {
						//fw.write(""+s+"\n");
						db.addTwitterStatus(s);
						cpt++;
					}
				}

			} while(listOfStatus.hasNext());
			System.out.println(cpt+" new(s) message(s) from Twitter about \""+keyword+"\"");
			//fw.close();
		} catch (TwitterException e) {e.printStackTrace();}
	}
}
