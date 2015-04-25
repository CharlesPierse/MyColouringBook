package BotDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import BasicBookReader.Book;
import BasicBookReader.BookObjectPopulater;
import BasicBookReader.Page;
import WordAnalysis.TermFrequencyInverseDocumentFrequency;
import WordNet.wordBrowser;

public class main {
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
		long end = System.currentTimeMillis();
		System.out.println("Time taken : " + (end-start));
		main m = new  main();
		m.createTrainerFile();
	}

	private void createTrainerFile(){
		BookObjectPopulater populater = new BookObjectPopulater();
		TermFrequencyInverseDocumentFrequency termFrequency = new TermFrequencyInverseDocumentFrequency();
		PageCategoryFinder categoryFinder = new PageCategoryFinder();
		
		wordBrowser wordBrowser = new wordBrowser();
		BasicConfigurator.configure();
		wordBrowser.initialise("resources" + File.separator + "WordNet" + File.separator + "props.xml");

		ArrayList<Book> bookList;
		ArrayList<Page> pages; //pages of each book object.
		ArrayList<String> importantWords;

		populater.populateBookPopulate(); //calls the book reader on all the books.
		bookList = populater.getBookList();// bookList = set of all the book objects

		for(Book book : bookList){
			pages = book.getPages();
			for(Page page : pages){
				importantWords = new ArrayList<String>();
				String text =page.getText().toLowerCase().replaceAll("[^a-z ]", "");
				String tokens[] = text.split(" ");
				double value;
				for(String token : tokens){
					value = termFrequency.tfIdf(token, tokens, pages);
					if(value > 1 && value < 100){ // >100 to remove infinities
						importantWords.add(token);
					}
				}
				categoryFinder.getPairings(importantWords, 0.8, wordBrowser);
				break;
			}
		}
	}
//	
//	private String replaceUndesirables(String string){
//		String output = string.replaceAll("\\p{P}", ""); //remove punctuation
//		output = string.replaceAll("chapter", ""); //removes the word chapter
//		output = string.replaceAll("[0-9]", ""); //removes numbers
//		return output;
//	}

}

class ValueComparator implements Comparator<String> {

	Map<String, Integer> base;
	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.    
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}