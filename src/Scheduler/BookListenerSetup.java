package Scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//important, close all instance of this after you are done with it as it causes login in errors if you run multiple instances.
public class BookListenerSetup {
	private String[] Author ;
	private String[] BookName ;
	private ArrayList<String> Author_full = new ArrayList<String>();
	private ArrayList<String> title_full = new ArrayList<String>();

	
	
	public BookListenerSetup(){
		loadAuthorBookList("NamesAuthors.txt");
	}
	
	public String[] getAuthor(){
		return Author;
	}
	
	public String[] getBookName(){
		return BookName;
	}
	
	public String getAuthFull(int local){
		return Author_full.get(local);
	}
	
	public String getTitleFull(int local){
		return title_full.get(local);
	}
	
	public void loadAuthorBookList(String filepath){
		FileInputStream in = null;
		try {
			in = new FileInputStream("resources"+ File.separator + filepath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String line = null;
		ArrayList<String> Auth = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null)
			{
				String Bookvalues[] = line.split("\t");
				title_full.add(Bookvalues[0]);
				Bookvalues[0] = Bookvalues[0].replaceAll(" ", "");
				Bookvalues[0] = Bookvalues[0].replaceAll("\\p{P}", "");
				title.add("#"+Bookvalues[0]);
				if(Bookvalues.length>1){
					Author_full.add(Bookvalues[1]);
					Bookvalues[1] = Bookvalues[1].replaceAll(" ", "");
					Bookvalues[1] = Bookvalues[1].replaceAll("\\p{P}", "");
					Auth.add("#"+Bookvalues[1].replace(" ", ""));
				}else{
					Author_full.add(null);
					Auth.add(null);
				}

			}
			Author = new String[Auth.size()];
			for(int x = 0; x<Author.length;x++){
				String cur = Auth.get(x);
				Author[x] = cur;
			}
			BookName = new String[title.size()];
			for(int x = 0; x<BookName.length;x++){
				String cur = title.get(x);
				BookName[x] = cur;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
