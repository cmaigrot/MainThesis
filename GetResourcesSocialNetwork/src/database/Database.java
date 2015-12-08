package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import facebook4j.IdNameEntity;
import facebook4j.Post;
import twitter4j.Status;
import twitter4j.User;

public class Database {

	private Connection connection; 
	private Statement stmt;


	public Database(){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:8889/thesis"; 
		String login = "root";
		String password = "root"; 




		try{ 
			Class.forName(driver); 
			connection = DriverManager.getConnection(url,login,password); 
		} 
		catch(ClassNotFoundException cnfe){ 
			System.out.println("Driver introuvable : "); 
			cnfe.printStackTrace(); 
		} 
		catch(SQLException sqle){ 
			System.out.println("Erreur SQL : "); 
			System.out.println(sqle.getMessage());
		}
	}

	public int addFacebookUser(facebook4j.User user) {
		try {
			stmt = connection.createStatement();

			if(existFacebookUser(user))
				return -1;

			String sqlInsertUser = "INSERT INTO users ("
					+ "`id`, `name`, `surname`, `gender`, `date_of_birth`,"
					+ " `lang`, `mobile_number`, `address`, `email`, `created`, `modified`)"
					+ " VALUES ("
					+ " null, '"+user.getLastName()+"', '"+user.getFirstName()+"', '"+user.getGender()+"', '"+transformBirdthday(user.getBirthday())+"',"
					+ " '"+user_languages(user.getLanguages())+"', NULL , '"+user.getHometown().getName()+"', '"+user.getEmail()+"', '0000-00-00 00:00:00.000000', '"
					+ createdDateFacebook(user.getUpdatedTime().toString()) +"')";
			stmt.executeUpdate(sqlInsertUser);

			System.out.println("INSERT INTO user : "+user.getName());

			String sqlSelectId = "SELECT id FROM users WHERE id IN ("
					+ "SELECT MAX(id) FROM users"
					+ ");";
			ResultSet rs = stmt.executeQuery(sqlSelectId);
			String idU = "";
			while ( rs.next() ) {
				idU = ""+rs.getObject(1);
			}

			String sqlInsertFacebookUser = "INSERT INTO facebook_users (id, user_id)"
					+ " VALUES ("
					+ " '"+user.getId()+"', "+idU +")";
			stmt.executeUpdate(sqlInsertFacebookUser);

			System.out.println("INSERT INTO user : "+user.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return 0;
	}

	public int addFacebookPage(facebook4j.Page page) {
		try {
			if(existFacebookPage(page)){
				return -1;
			}

			String sqlInsertPage = "INSERT INTO facebook_pages ";
			sqlInsertPage += " VALUES (";
			sqlInsertPage += "'"+page.getId()+"',"; // id
			if(page.getAbout() != null) sqlInsertPage += "'"+page.getAbout().replace("'"," ")+"',"; else sqlInsertPage += "NULL,"; // about
			sqlInsertPage += "'"+page.getCategory()+"',"; // category
			if(page.getCover() != null) sqlInsertPage += "'"+page.getCover().getId()+"',"; else sqlInsertPage += "NULL,"; // cover
			sqlInsertPage += ""+page.getLikes()+","; // likes
			sqlInsertPage += "'"+page.getLink()+"',"; // link
			if(page.getLocation() != null) sqlInsertPage += "'"+page.getLocation().getText()+"',"; else sqlInsertPage += "NULL,"; // location
			sqlInsertPage += "'"+page.getName().replace("'", " ")+"',"; // name
			sqlInsertPage += "'"+page.getPhone()+"',"; // phone
			sqlInsertPage += "'"+page.getUsername()+"',"; // username
			sqlInsertPage += "'"+page.getWebsite()+"',"; // website
			sqlInsertPage += ""+page.getWereHereCount()+" ,"; // were_here_count
			sqlInsertPage += "NULL ,"; // lang
			sqlInsertPage += "'"+createdDateFacebook(""+page.getCreatedTime())+"'"; // created
			sqlInsertPage += ");";
			sqlInsertPage = removeAccent(sqlInsertPage);
			for(String key : sqlInsertPage.split(","))
				System.out.println(key);
			stmt.executeUpdate(sqlInsertPage);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int addFacebookPost(facebook4j.Post post, String type, String id){
		try {
			if(existFacebookPost(post)) return -1;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sqlInsertPost = "";
		try {
			sqlInsertPost = "INSERT INTO facebook_posts ";
			sqlInsertPost += " VALUES (";
			sqlInsertPost += "'" + post.getId() + "',"; // id
			if(post.getCaption() != null) sqlInsertPost += "'"+post.getCaption().replace("'","")  +"',"; else sqlInsertPost += "NULL,"; // caption
			if(post.getDescription() != null) sqlInsertPost += "'"+post.getDescription().replace("'", " ") +"',"; else sqlInsertPost += "NULL,"; // description
			sqlInsertPost += "'" + post.getFrom().getId() + "',"; // author ID
			if(type.equals("page")) {
				sqlInsertPost += "'"+id+"',NULL,NULL,NULL,"; // page
			} else if(type.equals("group")) {
				sqlInsertPost += "NULL,'"+id+"',NULL,NULL,"; // page
			} else {
				sqlInsertPost += "NULL,NULL,'"+id+"',NULL,"; // page
			}
			sqlInsertPost += "'" + post.getIcon() + "',"; // icon
			sqlInsertPost += "'" + post.getLink() + "',"; // link
			if(post.getMessage() != null) sqlInsertPost += "'"+post.getMessage().replace("'", " ") +"',"; else sqlInsertPost += "NULL,"; // content of the message
			if(post.getName() != null) sqlInsertPost += "'"+post.getName().replace("'", " ") +"',"; else sqlInsertPost += "NULL,"; // name
			sqlInsertPost += "'" + post.getPicture() + "',"; // picture
			if(post.getPlace() != null && post.getPlace().getLocation() != null) sqlInsertPost += "'"+post.getPlace().getLocation().getState()  +"',"; else sqlInsertPost += "NULL,"; // name
			sqlInsertPost += "" + post.getSharesCount() + ","; // shares
			sqlInsertPost += "'" + post.getSource() + "',"; // source
			sqlInsertPost += "'" + post.getStatusType() + "',"; // status_type
			if(post.getStory() != null) sqlInsertPost += "'"+post.getStory().replace("'", " ") +"',"; else sqlInsertPost += "NULL,"; // story
			sqlInsertPost += "'" + post.getType() + "',"; // type
			sqlInsertPost += "'" + createdDateFacebook(""+post.getUpdatedTime()) + "',"; // update timestamp
			sqlInsertPost += "'" + createdDateFacebook(""+post.getCreatedTime()) + "'"; //  created timestamp
			sqlInsertPost += ");";
			sqlInsertPost = removeAccent(sqlInsertPost);
			System.out.println("add new Facebook Post");
			stmt.executeUpdate(sqlInsertPost);

		} catch (SQLException e) {
			e.printStackTrace();
			for(String part : sqlInsertPost.split("'"))
				System.out.println(part);
		}

		return 0;
	}


	public int addTwitterStatus(Status s) {
		try {
			stmt = connection.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			if(existTwitterStatus(s))
				return -1;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		addTwitterUser(s.getUser());
		String sqlInsertStatus = "";
		try {
			sqlInsertStatus = "INSERT INTO twitter_statuses ";
			sqlInsertStatus += " VALUES (";
			sqlInsertStatus += ""+s.getId()+","; // id
			sqlInsertStatus += "'"+s.getUser().getId()+"',"; // user_id
			sqlInsertStatus += "'"+createdDateTwitter(""+s.getCreatedAt())+"',";
			sqlInsertStatus += "'"+s.getInReplyToUserId()+"',";
			sqlInsertStatus += ""+s.getRetweetCount()+",";
			sqlInsertStatus += "'"+s.getInReplyToStatusId()+"',";
			if(s.getText() != null) sqlInsertStatus += "'"+s.getText().replace("'", " ") +"',"; else sqlInsertStatus += "NULL,"; // story
			sqlInsertStatus += "'"+s.getInReplyToScreenName()+"',";
			if(s.getPlace() != null) sqlInsertStatus += "'"+s.getPlace().getName() +"',"; else sqlInsertStatus += "NULL,"; // story
			sqlInsertStatus += "'"+s.getSource()+"'";
			sqlInsertStatus += ");";
			sqlInsertStatus = removeAccent(sqlInsertStatus);

			System.out.println("Add new twitter statues");

			stmt.executeUpdate(sqlInsertStatus);

		} catch (Exception e) {
			e.printStackTrace();
			for(String key: sqlInsertStatus.split(","))
				System.err.println(key);
		}

		return 0;

	}


	private int addTwitterUser(User user) {
		try {
			if(existTwitterUser(user.getId()))
				return -1;
		} catch (SQLException e1) { e1.printStackTrace(); }
		String sqlInsertUser = "";
		try {
			sqlInsertUser = "INSERT INTO twitter_users ";
			sqlInsertUser += " VALUES (";
			sqlInsertUser += "NULL,"; // id
			sqlInsertUser += ""+user.getId()+","; // user_id
			sqlInsertUser += "'"+user.getName()+"',";
			sqlInsertUser += "'"+user.getProfileImageURL()+"',";
			sqlInsertUser += "'"+createdDateTwitter(""+user.getCreatedAt())+"',";
			sqlInsertUser += "'"+user.getLocation()+"',";
			sqlInsertUser += ""+user.getFavouritesCount()+",";
			sqlInsertUser += ""+user.getListedCount()+",";
			sqlInsertUser += ""+user.getFollowersCount()+",";
			sqlInsertUser += ""+(user.isVerified()?"1":"0")+",";
			sqlInsertUser += ""+(user.isGeoEnabled()?"1":"0")+",";
			sqlInsertUser += "'"+user.getTimeZone()+"',";
			if(user.getDescription() != null) sqlInsertUser += "'"+user.getDescription().replace("'", " ") +"',"; else sqlInsertUser += "NULL,";
			sqlInsertUser += ""+user.getStatusesCount()+",";
			sqlInsertUser += ""+user.getFriendsCount()+",";
			sqlInsertUser += "'"+user.getScreenName()+"'";
			sqlInsertUser += ");";
			sqlInsertUser = removeAccent(sqlInsertUser);

			stmt.executeUpdate(sqlInsertUser);

		} catch (Exception e) {
			e.printStackTrace();

			for(String key: sqlInsertUser.split(","))
				System.err.println(key);		}

		return 0;
	}

	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

	public ArrayList<String> getPagesIds() throws SQLException {
		String sql = "SELECT id FROM facebook_pages;";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<String> pagesIds = new ArrayList<String>();
		while ( rs.next() ) {
			pagesIds.add(""+rs.getObject(1));
		}
		return pagesIds;
	}

	public ArrayList<String> getPagesPostsIds(String idPage) throws SQLException {
		String sql = "SELECT id FROM facebook_posts WHERE page_id = "+idPage+";";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<String> postsIds = new ArrayList<String>();
		while ( rs.next() ) {
			postsIds.add(""+rs.getObject(1));
		}
		return postsIds;
	}

	public String getPostURL(String idPost) throws SQLException {
		String sql = "SELECT link FROM facebook_posts WHERE id = '"+idPost+"';";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		String url = "";
		while ( rs.next() ) {
			url = ""+rs.getObject(1);
		}
		return url;
	}

	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

	private boolean existTwitterUser(long id) throws SQLException {
		String sql = "SELECT * FROM twitter_users WHERE id IN ('" + id + "');";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() ) {
			return true;
		}
		return false;
	}

	private boolean existTwitterStatus(Status s) throws SQLException {
		String sql = "SELECT * FROM twitter_statuses WHERE id IN ('" + s.getId() + "');";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() ) {
			return true;
		}
		return false;
	}

	private boolean existFacebookUser(facebook4j.User user) throws SQLException {
		String sql = "SELECT * FROM facebook_users WHERE id IN ('" + user.getId() + "');";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() ) {
			return true;
		}
		return false;
	}

	private boolean existFacebookPage(facebook4j.Page page) throws SQLException {
		String sql = "SELECT * FROM facebook_pages WHERE id IN ('" + page.getId() + "');";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() ) {
			return true;
		}
		return false;
	}

	private boolean existFacebookPost(Post post) throws SQLException {
		String sql = "SELECT * FROM facebook_posts WHERE id IN ('" + post.getId() + "');";
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() ) {
			return true;
		}
		return false;
	}

	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */


	private String user_languages(List<IdNameEntity> languages) {
		String response = "";
		for(IdNameEntity ie : languages)
		{
			if(response.length()>0)
				response += "/";
			response += ie.getName();
		}
		return response;
	}

	private String transformBirdthday(String birthday) {
		String[] tab = birthday.split("/");
		if(tab.length == 3) {
			return ""+tab[2]+"-"+tab[1]+"-"+tab[0]+" 00:00:00.000000";
		}
		return "0000-00-00 00:00:00.000000";
	}

	private String createdDateFacebook(String date) {
		String[] tab = date.split(" ");
		if(tab.length == 6) {
			String month = "";
			switch(tab[1]) {
			case "Jan" : month = "01"; break;
			case "Feb" : month = "02"; break;
			case "Mar" : month = "03"; break;
			case "Apr" : month = "04"; break;
			case "May" : month = "05"; break;
			case "Jun" : month = "06"; break;
			case "Jul" : month = "07"; break;
			case "Aug" : month = "08"; break;
			case "Sep" : month = "09"; break;
			case "Oct" : month = "10"; break;
			case "Nov" : month = "11"; break;
			case "Dec" : month = "12"; break;
			}

			return ""+tab[5]+"-"+month+"-"+tab[2]+" "+tab[3]+".000000";
		}
		return "0000-00-00 00:00:00.000000";
	}

	private String createdDateTwitter(String date) {
		String[] tab = date.split(" ");
		if(tab.length == 6) {
			String month = "";
			switch(tab[1]) {
			case "Jan" : month = "01"; break;
			case "Feb" : month = "02"; break;
			case "Mar" : month = "03"; break;
			case "Apr" : month = "04"; break;
			case "May" : month = "05"; break;
			case "Jun" : month = "06"; break;
			case "Jul" : month = "07"; break;
			case "Aug" : month = "08"; break;
			case "Sep" : month = "09"; break;
			case "Oct" : month = "10"; break;
			case "Nov" : month = "11"; break;
			case "Dec" : month = "12"; break;
			}

			return ""+tab[5]+"-"+month+"-"+tab[2]+" "+tab[3]+".000000";
		}
		return "0000-00-00 00:00:00.000000";
	}

	public String removeAccent(String text) {
		text = text.replaceAll("à|á|â|ä", "a");
		text = text.replaceAll("ò|ó|ô|ö", "o");
		text = text.replaceAll("è|é|ê|ë", "e");
		text = text.replaceAll("ù|ú|û|ü", "u");
		text = text.replaceAll("ì|í|î|ï", "i");

		return text;
	}




}
