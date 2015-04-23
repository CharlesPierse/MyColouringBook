package Scheduler;

import java.util.ArrayList;

public class tweetOrganizer {
	
	ArrayList<String> tweet = new ArrayList<String>();
	
	public tweetOrganizer(String user, String hashtag, int type, long time, ArrayList<String> tweeted, String title){
		new tweetOrganizer(user, hashtag, type, time, tweeted, title, null);
	}
	
	public tweetOrganizer(String user, String hashtag, int type, long time, ArrayList<String> tweeted, String title, String author){
		if(!tweeted.contains(user)){
			if(type==1){
				tweet.add("Type 1");
				tweet.add(user);
				tweet.add(author);
				//Could be null
				tweet.add(title);
				/*
				 * 
				 * Will add details on 
				 * coour and subject of book??/
				 * 
				 */
			}else if(type==2){
				tweet.add("Type 2");
				tweet.add(user);
				tweet.add(author);
				tweet.add(title);
				/*
				 * 
				 * Will add details on 
				 * coour and subject of book??/
				 * 
				 */
			}
		}
	}
	
	
	public ArrayList<String> getTweetInfo(){
		return tweet;
	}
	

}
