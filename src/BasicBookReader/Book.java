package BasicBookReader;

import java.util.ArrayList;
import java.util.HashMap;

public class Book {
	private HashMap<Integer, String> book; //Integer = page number, String[] = words
	ArrayList<Page> pages;
	private String title;
	private String author;
	private int[] tweetedPages;
	
	public Book(HashMap<Integer, String> book, String title, String author, int[] tweetedPages) {
		this.book = book;
		this.title = title;
		this.author = author;
		this.tweetedPages = tweetedPages;
		this.pages=new ArrayList<Page>();
	}
	
	public int[] getTweetedPages() {
		return tweetedPages;
	}
	
	public void addPage(String text,int number){
		Page p=new Page();
		p.setNumber(number);
		p.setText(text);
		this.pages.add(p);
	}

	public void setTweetedPages(int[] tweetedPages) {
		this.tweetedPages = tweetedPages;
	}

	public HashMap<Integer, String> getBook() {
		return book;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
}