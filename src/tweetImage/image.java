package tweetImage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class image {
	
	private BufferedImage img = null;
	
	public image(String st){
		try {
		    img = ImageIO.read(new File(st));
		    int r = 255;
		    int g = 215;
		    int b = 0;
		    int a = 255;
		    int col = (a << 24) | (r << 16) | (g << 8) | b;
		    for(int x = 452;x<818;x++){
		    	for(int y = 24;y<518;y++){
		    		img.setRGB(x, y, col);
		    	}
		    }
		    
		    File f = new File("resources/tweetimages/tweetFile.png");
		    ImageIO.write(img, "PNG", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		image im = new image("resources/tweetimages/bookTemplate.png");
	}
}
