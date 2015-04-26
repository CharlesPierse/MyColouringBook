package Scheduler;

import java.util.ArrayList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterListener{
	private long starttime;
	private BookListenerSetup book = new BookListenerSetup();
	private ArrayList<ArrayList<String>> organizedRetweets = new ArrayList<ArrayList<String>>();
	private volatile boolean anyfound = false;
	private TwitterStream twitterStream;
	private int running;
	private ArrayList<String> tweeted;
	
	public TwitterListener(ArrayList<String> tw, int run){
		running = run;
		tweeted = tw;
		starttime = System.currentTimeMillis();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("HC3PNXzjbLWtbdqPl0DpHwaDV");
        cb.setOAuthConsumerSecret("gSG5gM5cOsbhiXryUyRKEtS3FAAR5CWLkXtQOZzXS6FyYGXZrh");
        cb.setOAuthAccessToken("3003436679-6idXjsJs74dxLyY4MRP7UCKdMXZgU47LVFWtIJJ");
        cb.setOAuthAccessTokenSecret("kxmKTCtnb9phscEsP7NyGsmMde2Z6wgMDGHMolmmqvsus");

        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
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
					int booknum = 0;
					int hashhit = 0;
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
									hashhit = 1;
									booknum = x;
								}else if(tweet.contains(author[y])&&author[y].length()>1){
									hash = title[x]+" "+author[x];
									found = true;
									hashhit = 3;
									booknum = x;
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
										hashhit = 2;
										booknum = x;
									}
								}
							}
						}
					}
					//Check if the found tweet has a Author or Title hashtag. This info can be passed to the Namextweet
					if(hashhit==1||hashhit==2||hashhit==3){
						long time = (status.getCreatedAt().getTime() - starttime)/1000;
						tweetOrganizer to = null;
						if(book.getAuthFull(booknum)!=null){
							to = new tweetOrganizer(status.getUser().getScreenName(), hash, hashhit, time, tweeted, book.getTitleFull(booknum),book.getAuthFull(booknum));
						} else{
							to = new tweetOrganizer(status.getUser().getScreenName(), hash, hashhit, time, tweeted, book.getTitleFull(booknum));
						}
						if(to!=null){
							organizedRetweets.add(to.getTweetInfo());
							//Make sure we dont gather too many tweets
							if(organizedRetweets.size()>49){
								endStream();
								anyfound = true;
							}
						}
						
						//Shutdown listener once it finds a status and has gone passed running secs
				        if(starttime+(running*1000)<System.currentTimeMillis()){
				        	endStream();
				        	if(!organizedRetweets.isEmpty()){
				        		anyfound = true;
				        	}
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
	
	public void endStream(){
    	twitterStream.shutdown();
    	twitterStream.cleanUp();
	}
	
	public long getStart(){
		return starttime;
	}
	public ArrayList<ArrayList<String>> getTweets(){
		return organizedRetweets;
	}
	public boolean hasTweets(){
		return anyfound;
	}
}
