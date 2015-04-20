package TwitterBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

//important, close all instance of this after you are done with it as it causes login in errors if you run multiple instances.
public class BookListener {
	String[] Author ;
	String[] BookName ;
	String[] Bookvalues;
	
	public BookListener(){
		loadAuthorBookList("NamesAuthors.txt");
	}
	
	
	public void loadAuthorBookList(String filepath){
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator + filepath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String line = null;
		try {
			while((line = br.readLine()) != null)
			{
				int i = 0,j =0;
				Bookvalues = line.split("\\t");
				BookName[i] = Bookvalues[0];
				Author[j] = Bookvalues[1];
				i++;
				j++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	 public static void main(String[] args) {
	        ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setDebugEnabled(true);
	        cb.setOAuthConsumerKey("HC3PNXzjbLWtbdqPl0DpHwaDV");
	        cb.setOAuthConsumerSecret("gSG5gM5cOsbhiXryUyRKEtS3FAAR5CWLkXtQOZzXS6FyYGXZrh");
	        cb.setOAuthAccessToken("3003436679-6idXjsJs74dxLyY4MRP7UCKdMXZgU47LVFWtIJJ");
	        cb.setOAuthAccessTokenSecret("kxmKTCtnb9phscEsP7NyGsmMde2Z6wgMDGHMolmmqvsus");

	        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	        StatusListener listener = new StatusListener() {

				@Override
				public void onException(Exception arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onDeletionNotice(StatusDeletionNotice arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onScrubGeo(long arg0, long arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStallWarning(StallWarning arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStatus(Status status) {
					 User user = status.getUser();
		                String username = status.getUser().getScreenName();
		                System.out.println(username);		
		          
		                String profileLocation = user.getLocation();
		                System.out.println(profileLocation);
		                long tweetId = status.getId(); 
		                System.out.println(tweetId);
		                String content = status.getText();
		                System.out.println(content +"\n");
		                NamexTweet nt = new NamexTweet();
		    	        try {
		    				nt.start(username);
		    			} catch (TwitterException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		                
					
				}

				@Override
				public void onTrackLimitationNotice(int arg0) {
					// TODO Auto-generated method stub
					
				}
	        	
	        };
	        
	        FilterQuery fq = new FilterQuery();
	      
	        String bookinfo[][] = new Array[BookName][2];   //needs to be finished later, not populating correclty yet
	        String keywords[] = {bookinfo}; //other things can be added 

	        fq.track(keywords);

	        twitterStream.addListener(listener);
	        twitterStream.filter(fq);

	 }
}
