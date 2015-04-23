package WordAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import BasicBookReader.Book;


public class TermFrequencyInverseDocumentFrequency {

	private int termFrequency(String term, String[] page){
		int frequency = 0;
		for(String word : page){
			if(word.equals(term)){
				frequency++;
			}
		}
		return frequency;
	}

	private double inverseDocumentFrequency(String term, HashMap<Integer, String> book){ //the full directory of texts parameter is not needed, as it is an instance variable.
		double denomonator = 0;
		for(String page : book.values())
		{
			if(page.contains(term)){
				denomonator++; //+= bookData.get(term);
			}
		}
		return Math.log10(book.size()/denomonator);
	}

	public double tfIdf(String term, String[] page, HashMap<Integer, String> book){
		double tf = termFrequency(term, page);
		double idf = inverseDocumentFrequency(term, book);
		double result = tf*idf;
		if(Double.isNaN(result)){
			return -1;
		}
		else{
			return result;
		}
	}
	
	public void writeToFile(HashSet<Book> bookList){
		PrintWriter writer;
		int bookLimit = 10;
		int pageLimit = 10;
		HashMap<Integer, String> pages;
		
		for(Book book : bookList){
			if(bookLimit > 0){//-------------------------------------------
				bookLimit--;//-------------------------------------------
				pages = book.getBook();
				for(int key : pages.keySet()){
					if(key <= pageLimit){//-------------------------------------------
						try {
							writer = new PrintWriter("resources" + File.separator + "books" + File.separator + "book_" + bookLimit + File.separator + "Page_" + key + ".txt", "UTF-8");
							String[] splitCurrentPage = pages.get(key).replaceAll("\\p{P}", "").split(" "); //remove the punctuation from page and then splits it at whitespacs.
							for(String word : splitCurrentPage){
								double value = tfIdf(word, splitCurrentPage, pages);
								if(value > 1 && value < 100){
									writer.println(word + "\t" + value);
								}
							}
							writer.close();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public HashMap<String, Double> readFile(int bookNumber, int pageNo){
		HashMap<String, Double> pageInfo = new HashMap<String, Double>();
		try{
			FileInputStream fis = new FileInputStream("resources" + File.separator + "books" + File.separator + "book_" + bookNumber + File.separator + "Page_" + pageNo + ".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			String line = "";
			
			while((line=br.readLine().toLowerCase())!=null){
				String tokens[] = line.split("\t");
				pageInfo.put(tokens[0], Double.parseDouble(tokens[1]));
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return pageInfo;
	}
}
