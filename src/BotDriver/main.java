package BotDriver;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.BasicConfigurator;

import WordAnalysis.TermFrequencyInverseDocumentFrequency;
import BasicBookReader.BookObject;
import BasicBookReader.BookObjectPopulater;
import WordNet.wordBrowser;

public class main {
	public static void main(String[] args) {

		BookObjectPopulater populater = new BookObjectPopulater();
		wordBrowser wordBrowser = new wordBrowser();
		TermFrequencyInverseDocumentFrequency termFrequency = new TermFrequencyInverseDocumentFrequency();

		BasicConfigurator.configure();
		wordBrowser.initialise("resources"+File.separator+"WordNet"+File.separator+"props.xml");

		HashSet<BookObject> bookList;
		HashMap<Integer, String> pages; //pages of each book object.

		populater.populateBookPopulate(); //calls the book reader on all the books.
		bookList = populater.getBookList();// bookList = set of all the book objects
		for(BookObject book : bookList){
			pages = book.getBook();
			for(int key : pages.keySet()){
				String[] splitCurrentPage = pages.get(key).replaceAll("\\p{P}", "").split(" "); //remove the punctuation from page and then splits it at whitespacs.
				for(String word : splitCurrentPage){
					System.out.println(word + "\t" + termFrequency.tfIdf(word, splitCurrentPage, pages));
				}
			}
		}
		//		for (int i = 1; i <= 1; i++){
		//			tb.bookread(i);
		//			ArrayList<String> wordList = tb.bookread(i);
		//			
		//			for(String word: wordList){
		//				
		//				if(wb.wordnet_hypernymTreeRecursive(word, "VB")==null ||wb.wordnet_hypernymTreeRecursive(word, "NN")== null){
		//					//wordList.remove(word);
		//				}
		//				else{
		//					
		//					System.out.println("Word is:"+word + ("\t") + wb.wordnet_hypernymTreeRecursive(word, "NN"));
		//				}
		//			}
		//
		//		}
	}
}
