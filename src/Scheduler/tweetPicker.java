package Scheduler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

import tweetImage.image;
import twitter4j.StatusUpdate;

public class tweetPicker {
	
	private StatusUpdate status;
	private final String filepath = "resources"+File.separator+"retweeted"+File.separator+"retweeted.txt";
	
	public tweetPicker(ArrayList<ArrayList<String>> tweets){
		int size = tweets.size();
		Random rand = new Random();
		ArrayList<String> cur = tweets.get(rand.nextInt(size));
		System.out.println(cur);
		String user = cur.get(1);
		String title = cur.get(3);
		String author;
		if(cur.get(2)!=null){
			author = cur.get(2);
		}else{
			author = "";
		}
		String hashtag = cur.get(4);
		String colour = "#95b87d"; //From colour
		//Will get page from selected book
		String text = "I hope you will be ready to own publicly, whenever you shall be called to it, that by your great and frequent urgency you prevailed on me to publish a very loose and uncorrect account of my travels, with directions to hire some young gentleman of either university to put them in order, and correct the style, as my cousin Dampier did, by my advice, in his book called �A Voyage round the world. But I do not remember I gave you power to consent that any thing should be omitted, and much less that any thing should be inserted; therefore, as to the latter, I do here renounce every thing of that kind; particularly a paragraph about her majesty Queen Anne, of most pious and glorious memory; although I did reverence and esteem her more than any of human species.  But you, or your interpolator, ought to have considered, that it was not my inclination, so was it not decent to praise any animal of our composition before my master _Houyhnhnm_: And besides, the fact was altogether false; for to my knowledge, being in England during some part of her majesty�s reign, she did govern by a chief minister; nay even by two successively, the first whereof was the lord of Godolphin, and the second the lord of Oxford; so that you have made me say the thing that was not.  Likewise in the account of the academy of projectors, and several passages of my discourse to my master _Houyhnhnm_, you have either omitted some material circumstances, or minced or changed them in such a manner, that I do hardly know my own work.  When I formerly hinted to you something of this in a letter, you were pleased to answer that you were afraid of giving offence; that people in power were very watchful over the press, and apt not only to interpret, but to punish every thing which looked like an _innuendo_ (as I think you call it).  But, pray how could that which I spoke so many years ago, and at about five thousand leagues distance, in another reign, be applied to any of the _Yahoos_, who now are said to govern the herd; especially at a time when I little thought, or feared, the unhappiness of living under them?  Have not I";
		status = new StatusUpdate("@"+user+" "+hashtag);
		createImage(title, author, colour, text);
		addUser(user);
	}
	
	public StatusUpdate getStatus(){
		return status;
	}
	
	private void createImage(String title, String author, String colour, String text){
		image im = new image(title, author, colour, text);
		status.setMedia(new File("resources"+File.separator+"tweetimages"+File.separator+"tweetFile.png"));
	}
	
	private void addUser(String user){
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter(filepath, true));
			output.append("\n"+user);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
