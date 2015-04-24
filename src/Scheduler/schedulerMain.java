package Scheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import twitter4j.TwitterException;
import TwitterBot.NamexTweet;

public class schedulerMain {
	
	private ArrayList<String> tweeted = new ArrayList<String>();
	private final int MAXTWEETEDSIZE = 200;
	private final String BOTNAME = "my_colour_book";
	
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
        //Removing from the tweeted after a certain amount
        if(tweeted.size()>=MAXTWEETEDSIZE){
        	List<String> temp =tweeted.subList(MAXTWEETEDSIZE-50, tweeted.size());
        	temp.add(0, BOTNAME);
        	tweeted = new ArrayList<String>(temp);
        	
			try {
	        	PrintWriter writer = new PrintWriter(fileName);
	        	writer.close();
				try {
					Writer output = new BufferedWriter(new FileWriter(fileName));
					for(int x = 0; x<tweeted.size();x++){
						String user = tweeted.get(x);
						if(x!=tweeted.size()-1){
							output.append(user+"\n");
						}else{
							output.append(user);
						}
					}
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
		sm.run(60);
	}
}
