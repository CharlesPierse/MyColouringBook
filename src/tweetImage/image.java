package tweetImage;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TwitterBot.BookCoverRetrieve;


public class image {
	
	private BufferedImage img = null;
	private String st = "resources"+File.separator+"tweetimages"+File.separator+"bookTemplate.png";
	private BookCoverRetrieve bcr = new BookCoverRetrieve();
	
	public image(String bookname, String author,String c, String colourname){
		new image(bookname, author, c, colourname, false);
	}
	
	public image(String bookname, String author,String c, String colourname, boolean tint){
		try {
		    img = ImageIO.read(new File(st));
		    String color = c.substring(1);
			String red=color.substring(0, 2);
			String green=color.substring(2, 4);
			String blue=color.substring(4, 6);
		    int r = Integer.parseInt(red, 16);
		    int g = Integer.parseInt(green, 16);
		    int b = Integer.parseInt(blue, 16);
		    int a = 255;
		    int col = (a << 24) | (r << 16) | (g << 8) | b;
		    for(int x = 452;x<818;x++){
		    	for(int y = 24;y<518;y++){
		    		img.setRGB(x, y, col);
		    	}
		    }
		    int lines  = colourname.length()%58;
		    ArrayList<String> linesarray = new ArrayList<String>();
		    int place = 58;
		    for(int x = 0; x<lines; x++){
		    	if(place<colourname.length()){
		    		linesarray.add(colourname.substring(place-58, place));
		    		place= place+58;
		    	}
		    }
		   	for(String cur : linesarray){
			    textImage text = new textImage(cur);
			    //BufferedImage txt = getScaledImage(text.getTextImage(), 250, text.getTextImage().getHeight());
			    BufferedImage txt  = text.getTextImage();
			    for(int x = 0; x<txt.getWidth();x++){
			    	for(int y = 0;y<txt.getHeight();y++){
			    		int col1 = txt.getRGB(x, y);
			    		int colalpha = col1>>24;
			    		int colwhite = col1>>16;
			    		if(colalpha!=0||colwhite!=000000000){
			    			img.setRGB(x+455,y+(25+(txt.getHeight()*linesarray.indexOf(cur))),col1);
			    		}
			    	}
			    }
		   	}

			BufferedImage cover = bcr.getBookImage(bookname, author);
			if(cover!=null){
				if(tint)cover = new TintImage(cover, new Color(r,g,b)).getTint();
				cover = getScaledImage(cover, 378, 493);
				for(int x = 58;x<436;x++){
					for(int y = 23;y<516;y++){
						int col2 = cover.getRGB(x-58, y-23);
						img.setRGB(x, y, col2);
					}
				}

		    
		    File f = new File("resources/tweetimages/tweetFile.png");
		    ImageIO.write(img, "PNG", f);
			}else{
				System.out.println("No mage found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getScaledImage(BufferedImage image, int width, int height) {
		try {
		    int imageWidth  = image.getWidth();
		    int imageHeight = image.getHeight();

		    double scaleX = (double)width/imageWidth;
		    double scaleY = (double)height/imageHeight;
		    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		    return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void main(String[] args){
		
		image im = new image("Ulysses", "James Joyce", "#95b87d", "I hope you will be ready to own publicly, whenever you shall be called to it, that by your great and frequent urgency you prevailed on me to publish a very loose and uncorrect account of my travels, with directions to hire some young gentleman of either university to put them in order, and correct the style, as my cousin Dampier did, by my advice, in his book called “A Voyage round the world.”  But I do not remember I gave you power to consent that any thing should be omitted, and much less that any thing should be inserted; therefore, as to the latter, I do here renounce every thing of that kind; particularly a paragraph about her majesty Queen Anne, of most pious and glorious memory; although I did reverence and esteem her more than any of human species.  But you, or your interpolator, ought to have considered, that it was not my inclination, so was it not decent to praise any animal of our composition before my master _Houyhnhnm_: And besides, the fact was altogether false; for to my knowledge, being in England during some part of her majesty’s reign, she did govern by a chief minister; nay even by two successively, the first whereof was the lord of Godolphin, and the second the lord of Oxford; so that you have made me say the thing that was not.  Likewise in the account of the academy of projectors, and several passages of my discourse to my master _Houyhnhnm_, you have either omitted some material circumstances, or minced or changed them in such a manner, that I do hardly know my own work.  When I formerly hinted to you something of this in a letter, you were pleased to answer that you were afraid of giving offence; that people in power were very watchful over the press, and apt not only to interpret, but to punish every thing which looked like an _innuendo_ (as I think you call it).  But, pray how could that which I spoke so many years ago, and at about five thousand leagues distance, in another reign, be applied to any of the _Yahoos_, who now are said to govern the herd; especially at a time when I little thought, or feared, the unhappiness of living under them?  Have not I", true);
	}
}
