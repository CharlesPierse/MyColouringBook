package TwitterBot;

import java.io.File;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

//important, close all instance of this after you are done with it as it causes login in errors if you run multiple instances.
public class BookListener {
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
					 Twitter twitter = new TwitterFactory().getInstance();

		                String username = status.getUser().getScreenName();
		                System.out.println(username);
		                String profileLocation = user.getLocation();
		                System.out.println(profileLocation);
		                long tweetId = status.getId(); 
		                System.out.println(tweetId);
		                String content = status.getText();
		                System.out.println(content +"\n");
		                StatusUpdate st = new StatusUpdate("@"+username +" I hope you are enjoying it, it's a very colourful book indeed #snotgreenSea");
		                st.setMedia(new File("resources"+File.separator+"tweetimages"+File.separator+"tweetFile.png"));
		                try {
							twitter.updateStatus(st);
						} catch (TwitterException e) {
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
	        String bookname = "#Ulysses";
	        String keywords[] = {bookname}; //other thigns can be added 

	        fq.track(keywords);

	        twitterStream.addListener(listener);
	        twitterStream.filter(fq);  

	 }
}
