package BasicBookReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import tweetImage.image;

public class BookObjectPopulater {

	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	private String fileExtension = ".txt";
	public int lastBookNumber = 38; //needed public
	private ArrayList<Book> bookList = new ArrayList<Book>();
	HashMap<String, String> pluralToSingular = new HashMap<String, String>();


	public ArrayList<Book> getBookList() {
		return bookList;
	}

	public void populateBookPopulate(){
		for(int i = 1; i <= lastBookNumber; i++){
			readBook(i);
		}
	}

	private void readBook(int bookNumber){
		Book bookObject = null;
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
			bookObject = new Book();
			fis = new FileInputStream(filePath + bookNumber + fileExtension);
			br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine().toLowerCase())!=null){
				if(allowedRead){
					if(line.contains("***")){
						break;
					}
					else{
						if(overflow.equals("")){//This should only be entered after finishing a page and not all the words could be added.
							if(currentCharCount + overflow.length() <= pageCharLimit){
								currentPage += (" " + overflow);
								currentCharCount +=  overflow.length();
							}
							else{
								System.out.println("This should never happen. Basicly a line was read in that is over 4200 characters.....");
								System.out.println(currentCharCount);
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

							bookObject.addPage(currentPage, currentPageNumber);
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
						bookObject.setTitle(line.substring(7, line.length()));
					}
					else if(line.contains("Author:")){
						bookObject.setAuthor(line.substring(8, line.length()));
					}
				}
			}
			bookList.add(bookObject);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void readInPlurals(){
		try{
			FileInputStream fis = new FileInputStream("resources" + File.separator + "Word databases" + File.separator + "plurals.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			String line = "";
			while((line=br.readLine())!=null){
				String tokens[] = line.split("\t");
				if(tokens.length == 2){
					if(!pluralToSingular.containsKey(tokens[0])){
						pluralToSingular.put(tokens[0], tokens[1]);
					}
				}
			}
		}catch(Exception e){
			System.out.println("\n\nCould not read on plurals.");
			e.printStackTrace();
		}
	}

		public String pluralToSingular(String inWord){
			if(pluralToSingular.containsKey(inWord)){
				return pluralToSingular.get(inWord);
			}
			else{
				return inWord;
			}
		}

	private void saveNameAuthor(){
		try {
			PrintWriter writer = new PrintWriter("resources"+File.separator+"NamesAuthors.txt", "UTF-8");
			for(Book Object : bookList){
				writer.println(Object.getTitle() + "\t" + Object.getAuthor());
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}