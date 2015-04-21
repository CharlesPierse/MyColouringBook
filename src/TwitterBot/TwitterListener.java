package TwitterBot;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

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

public class TwitterListener {
	public TwitterListener(){
	 	BookListenerSetup book = new BookListenerSetup();
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
				if(!status.isRetweet()){
					int hashhit = 0;
					User user = status.getUser();
					String tweet = status.getText().toLowerCase();
					String hash = "";
					boolean found = false;
					String[] title = book.getBookName();
					for(int x = 0; x<title.length&&!found; x++){
						if(tweet.contains(title[x].toLowerCase())){
							String author[] = book.getAuthFull(x).split(" ");
							for(int y = 0; y<author.length&&!found;y++){
								if(!tweet.contains(author[y])&&author[y].length()>1){
									hash = title[x];
									found = true;
									hashhit = 2;
								}
							}
							}
					}
					if(!found) {
						String[] auth = book.getAuthor();
						for(int x = 0; x<auth.length&&!found; x++){
							if(tweet.contains(auth[x].toLowerCase())){
								String titleswords[] = book.getTitleFull(x).split(" ");
								for(int y = 0; y<titleswords.length&&!found;y++){
									if(!tweet.contains(titleswords[y])&&titleswords[y].length()>1){
										hash = auth[x];
										found = true;
										hashhit = 1;
									}
								}
							}
						}
					}
					//Check if the found tweet has a Author or Title hashtag. This info can be passed to the Namextweet
					if(hashhit==1||hashhit==2){
						System.out.print("\n---------\nThis is the "); 
						if(hashhit==1)System.out.print("author ");
						if(hashhit==2)System.out.print("title ");
						System.out.println("hashtag  " +hash+"\n---------");
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
			    		nt.start(username, hashhit);
			    	 		} catch (TwitterException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			} catch (IOException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    		}
					}	
				}
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        
        FilterQuery fq = new FilterQuery();
        
        String[] author = book.getAuthor();
        String[] title = book.getBookName();
        int keywordlen = author.length + title.length;
        String keywords[] = new String[keywordlen];
        for(int x = 0; x<author.length; x++){
        	keywords[x] = author[x];
        }
        for(int x = 0; x<title.length; x++){
        	keywords[x+author.length] = title[x];
        }
        fq.track(keywords);
        
        
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

	}
	
	public static void main(String[] args){
		TwitterListener tl = new TwitterListener();
	}
}
