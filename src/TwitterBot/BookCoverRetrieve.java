package TwitterBot;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import twitter4j.JSONArray;
import twitter4j.JSONObject;

public class BookCoverRetrieve {
	
	public BufferedImage getBookImage(String bName){
		return getBookImage(bName, "book_cover");
	}
	
	public BufferedImage getBookImage(String bName, String Author_name) {
		String bookName = bName;
    	String author = Author_name ; 
    	author = author.replaceAll(" ", "_").toLowerCase();
    	bookName = bookName.replaceAll(" ", "_").toLowerCase();
        try{
        	
            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+bookName+"_"+author);
            URLConnection connection = url.openConnection();
        	

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
           
            JSONObject json = new JSONObject(builder.toString());
            BufferedImage image = null;
            boolean run = true;
                JSONArray resultsarray = json.getJSONObject("responseData").getJSONArray("results");
                for(int x = 0;x<resultsarray.length()&&run;x++){
                	String imageUrl = resultsarray.getJSONObject(x).getString("url");
                try {
                	 image = ImageIO.read(new URL(imageUrl));
				} catch (Exception e) {
            		System.out.println("Image retrieve failed "+(x)+" times.");
					e.printStackTrace();
				}
                if(image!=null){
                	if(image.getHeight()>250||image.getWidth()>250){
                		run=false;
                	}
                	
                }
            }
           
            if(image==null){
            	image = getBookImage(bName);
            }
            return image;
            
        } catch(Exception e){
        	
        	BufferedImage image = null;
            e.printStackTrace();
            return image;
        }
        
    }
}