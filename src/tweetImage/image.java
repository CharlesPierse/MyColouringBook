package tweetImage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class image {
	
	private BufferedImage img = null;
	
	public image(String st){
		try {
		    img = ImageIO.read(new File(st));
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args){
		image im = new image("resource/bookTemplate.png");
	}
}
