package BotDriver;

import java.io.File;
import java.io.PrintWriter;
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

		//WILL BE COMMENTED OUT LATER SO THAT WE DONT COMPUTE THE TFIDF EVERYTIME
		//termFrequency.writeToFile(bookList);

		for(int i = 0; i <= 9; i++){
			for(int j = 0; j <= 9; j++){
				HashMap<String, Double> pageInfo = termFrequency.readFile(i, j);
				for(String word : pageInfo.keySet()){
					if(wordBrowser.wordnet_hypernymTreeRecursive(word, "VB") != null || wordBrowser.wordnet_hypernymTreeRecursive(word, "NN") != null){
						System.out.println("Word is:"+word + ("\t") + wordBrowser.wordnet_hypernymTreeRecursive(word, "NN"));
					}
				}
			}
		}
	}
}
