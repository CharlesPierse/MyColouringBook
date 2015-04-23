package Scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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

	
	public void run(int secs) throws InterruptedException{
			TwitterListener tl = new TwitterListener(tweeted, secs);
			while(!tl.hasTweets()){

				  Thread.sleep(secs/2);
			}
			ArrayList<ArrayList<String>> tweets = tl.getTweets();
			for(ArrayList<String> al : tweets){
				for(String st : al){
					System.out.print(st + " ");
				}
				System.out.print("\n");
			}
	}
	
	public static void main(String[] args) throws InterruptedException{
		schedulerMain sm = new schedulerMain();
		sm.run(60);
	}
}
