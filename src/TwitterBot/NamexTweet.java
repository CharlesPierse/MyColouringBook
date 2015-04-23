package TwitterBot;

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
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class NamexTweet {
 
    private final static String CONSUMER_KEY = "HC3PNXzjbLWtbdqPl0DpHwaDV";
    private final static String CONSUMER_KEY_SECRET = "gSG5gM5cOsbhiXryUyRKEtS3FAAR5CWLkXtQOZzXS6FyYGXZrh";

    public void start(String uname, int hit) throws TwitterException, IOException {
    	String username = uname;
    	Twitter twitter = new TwitterFactory().getInstance();
    	twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

    	String accessToken = getSavedAccessToken();
    	String accessTokenSecret = getSavedAccessTokenSecret();
    	AccessToken oathAccessToken = new AccessToken(accessToken,accessTokenSecret);

    	twitter.setOAuthAccessToken(oathAccessToken);

    	

    	StatusUpdate status = new StatusUpdate("@"+username+" I hope you are enjoying it, it's a very colourful book indeed #snotgreenSea");
    	status.setMedia(new File("resources"+File.separator+"tweetimages"+File.separator+"tweetFile.png"));
    	if(hit==1){
    		System.out.println("-----Author-----");
    	}else if(hit==2){
    		System.out.println("-----Title-----");
    	}
    	//twitter.updateStatus(status);
    }

    private String getSavedAccessTokenSecret() throws IOException {
    	FileInputStream in = null;
		try {
			in = new FileInputStream("resources"+ File.separator + "tokens.txt");
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
			in = new FileInputStream("resources"+ File.separator + "tokens.txt");
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


}
