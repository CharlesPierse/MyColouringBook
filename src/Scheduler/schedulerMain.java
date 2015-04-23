package Scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import twitter4j.TwitterException;
import TwitterBot.NamexTweet;

public class schedulerMain {
	
	private ArrayList<String> tweeted = new ArrayList<String>();
	
	public schedulerMain(){
        String fileName = "resources"+File.separator+"retweeted"+File.separator+"retweeted.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                tweeted.add(line);
            }    
            bufferedReader.close();            
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public void run(int secs) {
		long starttime = System.currentTimeMillis();
		TwitterListener tl = new TwitterListener(tweeted, secs);
		while(!tl.hasTweets()&&starttime+(secs*1250)>System.currentTimeMillis()){
			  try {
				  Thread.sleep(secs*250);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ArrayList<ArrayList<String>> tweets = tl.getTweets();
		
		tl.endStream(); //Just in case ;)
		if(!tweets.isEmpty()){
			tweetPicker tp = new tweetPicker(tweets);
			NamexTweet nxt = new NamexTweet();
			try {
				nxt.start(tp.getStatus());
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("No tweets found");
		}
	}
	
	public static void main(String[] args) {
		schedulerMain sm = new schedulerMain();
		//run for however many seconds you like	
		sm.run(60);
	}
}
