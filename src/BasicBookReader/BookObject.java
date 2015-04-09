package BasicBookReader;

import java.util.HashMap;

public class BookObject {
	private HashMap<Integer, String[]> book; //Integer = page number, String[] = words
	private String title;
	private String author;
	private int[] tweetedPages;
	
	public BookObject(HashMap<Integer, String[]> book, String title, String author, int[] tweetedPages) {
		this.book = book;
		this.title = title;
		this.author = author;
		this.tweetedPages = tweetedPages;
	}
	
	public int[] getTweetedPages() {
		return tweetedPages;
	}

	public void setTweetedPages(int[] tweetedPages) {
		this.tweetedPages = tweetedPages;
	}

	public HashMap<Integer, String[]> getBook() {
		return book;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
}