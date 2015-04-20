package TwitterBot;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import cc.mallet.util.ArrayUtils;
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

	
	
	public BookListener(){
		loadAuthorBookList("NamesAuthors.txt");
	}
	
	public String[] getAuthor(){
		return Author;
	}
	
	public String[] getBookName(){
		return BookName;
	}
	
	public void loadAuthorBookList(String filepath){
		FileInputStream in = null;
		try {
			in = new FileInputStream("resources"+ File.separator + filepath);
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
		ArrayList<String> Auth = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null)
			{

				String Bookvalues[] = line.split("\t");
				Bookvalues[0] = Bookvalues[0].replaceAll(" ", "");
				Bookvalues[0] = Bookvalues[0].replaceAll("\\p{P}", "");
				title.add("#"+Bookvalues[0]);
				if(Bookvalues.length>1){
					Bookvalues[0] = Bookvalues[1].replaceAll(" ", "");
					Bookvalues[0] = Bookvalues[1].replaceAll("\\p{P}", "");
					Auth.add("#"+Bookvalues[1].replace(" ", ""));
				}

			}
			Author = new String[Auth.size()];
			for(int x = 0; x<Author.length;x++){
				String cur = Auth.get(x);
				Author[x] = cur;
			}
			BookName = new String[title.size()];
			for(int x = 0; x<BookName.length;x++){
				String cur = title.get(x);
				BookName[x] = cur;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	 public static void main(String[] args) {
		 	BookListener book = new BookListener();
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
						String tweet = status.getText();
						String hash = "";
						boolean found = false;
						String[] title = book.getBookName();
						for(int x = 0; x<title.length&&!found; x++){
							if(tweet.contains(title[x])||tweet.contains(title[x].toLowerCase())){
								hash = title[x];
								found = true;
								hashhit = 2;
								}
						}
						if(!found) {
							String[] auth = book.getAuthor();
							for(int x = 0; x<auth.length&&!found; x++){
								if(tweet.contains(auth[x])||tweet.contains(auth[x].toLowerCase())){
									hash = auth[x];
									found = true;
									hashhit = 1;
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
							/*NamexTweet nt = new NamexTweet();
				    	    try {
				    		nt.start(username);
				    	 		} catch (TwitterException e) {
				    				// TODO Auto-generated catch block
				    				e.printStackTrace();
				    			} catch (IOException e) {
				    				// TODO Auto-generated catch block
				    				e.printStackTrace();
				    		}*/
						}	
					}
				}

				@Override
				public void onTrackLimitationNotice(int arg0) {
					// TODO Auto-generated method stub
					
				}
	        	
	        };
	        
	        FilterQuery fq = new FilterQuery();
	      
	        String keywords[] = Stream.concat(Arrays.stream(book.getAuthor()), Arrays.stream(book.getBookName())).toArray(String[]::new);
	        fq.track(keywords);

	        twitterStream.addListener(listener);
	        twitterStream.filter(fq);

	 }
}
