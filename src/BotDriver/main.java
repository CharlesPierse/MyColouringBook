package BotDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.BasicConfigurator;

import BasicBookReader.Book;
import BasicBookReader.BookObjectPopulater;
import WordAnalysis.TermFrequencyInverseDocumentFrequency;
import WordNet.wordBrowser;

public class main {
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
//		wordBrowser wordBrowser = new wordBrowser();
//		BasicConfigurator.configure();
//		wordBrowser.initialise("resources"+File.separator+"WordNet"+File.separator+"props.xml");

		BookObjectPopulater populater = new BookObjectPopulater();
		TermFrequencyInverseDocumentFrequency termFrequency = new TermFrequencyInverseDocumentFrequency();



				ArrayList<Book> bookList;
				HashMap<Integer, String> pages; //pages of each book object.
		
				populater.populateBookPopulate(); //calls the book reader on all the books.
				bookList = populater.getBookList();// bookList = set of all the book objects
		
				//WILL BE COMMENTED OUT LATER SO THAT WE DONT COMPUTE THE TFIDF EVERYTIME
				termFrequency.writeToFile(bookList);

//		StringBuffer sb = new StringBuffer();
//		double depthLevel = 0.7;
//
//		for(int bookNumber = 0; bookNumber <= 9; bookNumber++){
//			for(int pageNumber = 0; pageNumber <= 9; pageNumber++){
//				PageCategoryFinder p = new PageCategoryFinder();
//				p.setWordBrowser(wordBrowser);
//				HashMap<String, Integer> map = p.getTopCategories(termFrequency, bookNumber, pageNumber, depthLevel);
//				
//				for(String key : map.keySet()){
//					System.out.println(key + "\t" + map.get(key));
//				}
//			}
//		}
				long end = System.currentTimeMillis();
				System.out.println("Time taken : " + (end-start));
	}

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
