package ClassTrainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import TwitterBot.BookListener;

public class TrainerBuilder {
	
	
	
	
	public ArrayList<String> bookread(int bookNumber){
		ArrayList<String> bookList = new ArrayList<String>();
		boolean allowedRead = false;
		int wordLimit = 1000;
		int currentPageNumber = 0;
		String currentPage = "";
		String overflow = "";
		FileInputStream fis;
		BufferedReader br;
		String line = "";
		try{
			
			fis = new FileInputStream("resources"+File.separator+"Books"+ File.separator+ "Book_"+ bookNumber + ".txt");
			br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine())!=null){
				if(allowedRead){
					if(line.contains("***")){
						break;
					}
					else{
						String line1 = line.replaceAll("[^a-zA-z|'| ]", "");
						String[] split = line1.split(" ");
						for(String word : split){
							bookList.add(word);
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
		return bookList;
	}	
}

