package tweetImage;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

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
		    textImage text = new textImage(colourname);
		    BufferedImage txt = getScaledImage(text.getTextImage(), 250, text.getTextImage().getHeight());
		    for(int x = 0; x<txt.getWidth();x++){
		    	for(int y = 0;y<txt.getHeight();y++){
		    		int col1 = txt.getRGB(x, y);
		    		if(col1!=0){
		    			img.setRGB(x+500,y+250,col1);
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
		image im = new image("The Merchant of venice", "ALOC", "#95b87d", "Brown trout", true);
	}
}
