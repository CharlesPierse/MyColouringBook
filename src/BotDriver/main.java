package BotDriver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import BasicBookReader.Book;
import BasicBookReader.BookObjectPopulater;
import BasicBookReader.Page;
import WordAnalysis.TermFrequencyInverseDocumentFrequency;

public class main {
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		//		wordBrowser wordBrowser = new wordBrowser();
		//		BasicConfigurator.configure();
		//		wordBrowser.initialise("resources"+File.separator+"WordNet"+File.separator+"props.xml");


		//WILL BE COMMENTED OUT LATER SO THAT WE DONT COMPUTE THE TFIDF EVERYTIME

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

	private void createTrainerFile(){
		BookObjectPopulater populater = new BookObjectPopulater();
		TermFrequencyInverseDocumentFrequency termFrequency = new TermFrequencyInverseDocumentFrequency();

		ArrayList<Book> bookList;
		ArrayList<Page> pages; //pages of each book object.

		populater.populateBookPopulate(); //calls the book reader on all the books.
		bookList = populater.getBookList();// bookList = set of all the book objects

		for(Book book : bookList){
			pages = book.getPages();
			for(Page page : pages){
				String text = replaceUndesirables(page.getText().toLowerCase());
				String tokens[] = text.split(" ");
				double value;
				for(String token : tokens){
					value = termFrequency.tfIdf(token, tokens, pages);
					if(value < 1 && value > 100){ // >100 to remove infinities
						
					}
				}
			}
		}
	}
	
	private String replaceUndesirables(String string){
		string.replaceAll("\\p{P}", ""); //remove punctuation
		string.replaceAll("chapter", ""); //removes the word chapter
		string.replaceAll("[0-9]", ""); //removes numbers
		return string;
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
