package TwitterBot;
//Charles Pierse
//11510667
//This code template was taken from the javacodeforgeeks website that was referenced and linked to us in class
// I enetered in my own consumer keys and tokens
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class NamexTweet {
 
    private final static String CONSUMER_KEY = "HC3PNXzjbLWtbdqPl0DpHwaDV";
    private final static String CONSUMER_KEY_SECRET =
    		"gSG5gM5cOsbhiXryUyRKEtS3FAAR5CWLkXtQOZzXS6FyYGXZrh";

    public void start() throws TwitterException, IOException {

 Twitter twitter = new TwitterFactory().getInstance();
 twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

 // here's the difference
 String accessToken = getSavedAccessToken();
 String accessTokenSecret = getSavedAccessTokenSecret();
 AccessToken oathAccessToken = new AccessToken(accessToken,
  accessTokenSecret);

 twitter.setOAuthAccessToken(oathAccessToken);
 urlMapper um = new urlMapper();
 um.loadRMtweets();
 Random gen = new Random();
 Object[] keys = um.getMap().keySet().toArray();
 String randomKey = (String) keys[gen.nextInt(keys.length)];
 String tweetK = randomKey;
 String tweetV = um.getMap().get(randomKey);

 
 twitter.updateStatus("#colorBot , I think that " + tweetK + " would look like: " + tweetV );

 
 System.out.println("\nMy Timeline:");
 
 ResponseList<Status> list = twitter.getHomeTimeline();
 for (Status each : list) {

     System.out.println("Sent by: @" + each.getUser().getScreenName()
      + " - " + each.getUser().getName() + "\n" + each.getText()
      + "\n");
 }
 

    }

    private String getSavedAccessTokenSecret() throws IOException {
    	
  

    	FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator + "Tokens.txt");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String line = null;
		String secretToken = "";
    	
    	List<String> list = new ArrayList<String>();
    	while((line = br.readLine()) != null){
    		
    		list.add(line);
    		
    	}
    	
    	String[] tokenValues = list.toArray(new String[0]);
    	secretToken = tokenValues[0];
    	br.close();
    	
 return secretToken;
    }

    private String getSavedAccessToken() throws IOException {
    	
    	FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator + "Tokens.txt");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String line = null;

    	String AccessToken = "";
  	
    	List<String> list = new ArrayList<String>();
    	while((line = br.readLine()) != null){
    		
    		list.add(line);
    		
    	}
    	
    	String[] tokenValues = list.toArray(new String[0]);
    	AccessToken = tokenValues[1];
    	br.close();
    	
 return  AccessToken;
    }

    public static void main(String[] args) throws Exception {
 new NamexTweet().start();
    }

}
