package WordAnalysis;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import WordNet.wordBrowser;
import BasicBookReader.BookObject;
import BasicBookReader.BookObjectPopulater;

public class findFrequency {

	private HashSet<BookObject> bookList = new HashSet<BookObject>();

	public static void main(String[] args) {
//		BookObjectPopulater pop = new BookObjectPopulater();
//		findFrequency ff = new findFrequency();
//		TermFrequencyInverseDocumentFrequency tf = new TermFrequencyInverseDocumentFrequency();
//		for(int i = 1; i < 38; i++){
//			pop.readBook(i);
//		}
//		ff.bookList = pop.getBookList();
//		for(BookObject bookObject : ff.bookList){
//			HashMap<Integer, String> book = bookObject.getBook();
//			for(int page : book.keySet()){
//				String p = book.get(page).replaceAll("[^a-zA-z]", " ");
//				String[] words = p.split(" ");
//				for(String word : words){
//					System.out.println(word + "\t\t" + tf.tfIdf(word, words, book));
//				}
//			}
//		}
		wordBrowser wb = new wordBrowser();
		wb.initialise("resources"+File.separator+"WordNet"+File.separator+"file_properties.xml");
		//wb.("horse", "n");
	}
}
