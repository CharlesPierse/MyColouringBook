package TwitterBot;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import twitter4j.JSONObject;

public class BookCoverRetrieve {
	
	public BufferedImage getBookImage(String bName, String Author_name) {
		String bookName = bName;
    	String author = Author_name ; //I will implement this so that it intially tries with authors name included and if that fails without
    	author = author.replaceAll(" ", "_").toLowerCase();
    	bookName = bookName.replaceAll(" ", "_").toLowerCase();
        try{
        	
            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+bookName+"_book");
            System.out.println(url);
            URLConnection connection = url.openConnection();
        	

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
           
            JSONObject json = new JSONObject(builder.toString());
            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");

            BufferedImage image = ImageIO.read(new URL(imageUrl));
            JOptionPane.showMessageDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
            System.out.println(image.getType());
            return image;
            
        } catch(Exception e){
        	
        	BufferedImage image = null;
			try {
				image = ImageIO.read(new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=page"));
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return image;
        }
        
    }
	
	
    public static void main(String[] args) {
    	
    	BookCoverRetrieve bcr = new BookCoverRetrieve();
    	bcr.getBookImage("The chamber of secrets", "James Joyce");
    }
}