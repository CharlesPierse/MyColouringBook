package BasicBookReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

import tweetImage.image;

public class BookObjectPopulater {

	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	private String fileExtension = ".txt";
	private int lastBookNumber = 38;
	private HashSet<BookObject> bookList = new HashSet<BookObject>();

	private void readBook(int bookNumber){
		BookObject bookObject;
		HashMap<Integer, String> book = new HashMap<Integer, String>();
		image testImage;
		boolean allowedRead = false;
		int currentCharCount = 0;
		int pageCharLimit = 2000;
		int currentPageNumber = 0;
		String currentPage = "";
		String overflow = "";
		FileInputStream fis;
		BufferedReader br;
		String line = "";
		String title = "";
		String author = "";

		try{
			fis = new FileInputStream(filePath + bookNumber + fileExtension);
			br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine())!=null){
				if(allowedRead){
					if(line.contains("***")){
						break;
					}
					else{
						if(overflow.equals("")){//This should only be entered after finishing a page and not all the words could be added.
							if(currentCharCount + overflow.length() < pageCharLimit){
								currentPage += (" " + overflow);
								currentCharCount +=  overflow.length();
							}
							else{
								System.out.println("This should never happen. Basically a line was read in that is over 4200 characters.....");
							}
							overflow = "";
						}

						//if there is no overflow
						if(currentCharCount + line.length() <= pageCharLimit){
							currentPage += (" " + line);
							currentCharCount += line.length();
						}
						else{
							currentPage += (" " + line.substring(0, pageCharLimit-currentCharCount));
							book.put(currentPageNumber, currentPage);
							overflow += (" " + line.substring(pageCharLimit-currentCharCount+1, line.length()));
							currentPage = "";
							currentCharCount = 0;
							currentPageNumber++;
						}
					}
				}
				else{
					if(line.contains("***")){
						allowedRead = true;
					}
					else if(line.contains("Title:")){
						title = line.substring(7, line.length());
					}
					else if(line.contains("Author:")){
						author = line.substring(8, line.length());
					}
				}
			}
			bookObject = new BookObject(book, title, author, new int[0]);
			bookList.add(bookObject);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		BookObjectPopulater populater = new BookObjectPopulater(); //lpt = ling pipe test
		for(int i = 1; i < populater.lastBookNumber; i++){
			populater.readBook(1);
		}
		System.out.println(populater.bookList.size());
	}
}
