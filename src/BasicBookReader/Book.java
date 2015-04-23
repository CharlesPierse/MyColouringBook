package BasicBookReader;

import java.util.ArrayList;
import java.util.HashMap;

public class Book {
	private HashMap<Integer, String> book; //Integer = page number, String[] = words
	ArrayList<Page> pages;
	private String title;
	private String author;
	private int[] tweetedPages;
	
	public Book() {
		this.book = null;
		this.title = null;
		this.author = null;
		this.tweetedPages = null;
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

	public ArrayList<Page> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}

	public void setBook(HashMap<Integer, String> book) {
		this.book = book;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
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