package BasicBookReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BookObjectPopulater {

	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	private String fileExtension = ".txt";
	private int lastBookNumber = 38;

	private void readBook(int bookNumber){
		boolean allowedRead = false;
		FileInputStream fis;
		BufferedReader br;
		String line = "";
		try{
			fis = new FileInputStream(filePath + bookNumber + fileExtension);
			br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine())!=null){
				if(allowedRead){
					if(line.contains("***")){
						break;
					}
					else{
						line = line.replaceAll("[a-zA-Z]", " ");
						String[] splitLine = line.split(" ");
						for(String word : splitLine){
							
						}
					}
				}
				else{
					if(line.contains("***")){
						allowedRead = true;
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		BookObjectPopulater populater = new BookObjectPopulater(); //lpt = ling pipe test

	}
}