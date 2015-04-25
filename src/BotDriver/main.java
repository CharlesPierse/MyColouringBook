package BotDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
		main m = new  main();
		m.createTrainerFile();
		long end = System.currentTimeMillis();
		System.out.println("Time taken : " + (end-start));
	}

	private void createTrainerFile(){
		BookObjectPopulater populater = new BookObjectPopulater();
		populater.readInPlurals();
		TermFrequencyInverseDocumentFrequency termFrequency = new TermFrequencyInverseDocumentFrequency();
		PageCategoryFinder categoryFinder = new PageCategoryFinder();

		wordBrowser wordBrowser = new wordBrowser();
		BasicConfigurator.configure();
		wordBrowser.initialise("resources" + File.separator + "WordNet" + File.separator + "props.xml");

		ArrayList<Book> bookList;
		ArrayList<Page> pages; //pages of each book object.
		ArrayList<String> importantWords;
		int limit = 28;

		populater.populateBookPopulate(); //calls the book reader on all the books.
		bookList = populater.getBookList();// bookList = set of all the book objects
		for(Book book : bookList){
			if(limit > 0){
				pages = book.getPages();
				for(Page page : pages){
					importantWords = new ArrayList<String>();
					String text =page.getText().toLowerCase().replaceAll("[^a-z ]", "");
					String tokens[] = text.split(" ");
					double value;
					for(String token : tokens){
						String singularToken = populater.pluralToSingular(token);
						value = termFrequency.tfIdf(singularToken, tokens, pages);
						if(value > 0.2 && value < 100){ // >100 to remove infinities
							importantWords.add(singularToken);
						}
					}
					HashMap<String, ArrayList<String>> result = categoryFinder.getPairings(importantWords, 0.6, wordBrowser);
					if(result == null){
						//System.out.println("No reli");
					}
					else{
						wordBrowser.writeTrainer(result, text);
					}
				}
			}
			else{
				break;
			}
		}
	}
}