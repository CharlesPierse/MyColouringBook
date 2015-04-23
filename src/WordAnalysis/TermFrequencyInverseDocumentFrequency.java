package WordAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import BasicBookReader.Book;
import BasicBookReader.Page;


public class TermFrequencyInverseDocumentFrequency {

	private int termFrequency(String term, String[] page){
		int frequency = 0;
		for(String word : page){
			if(word.equals(term)){
				frequency++;
			}
		}
		return frequency;
	}

	private double inverseDocumentFrequency(String term, ArrayList<Page> pages){ //the full directory of texts parameter is not needed, as it is an instance variable.
		double denomonator = 0;
		for(Page page : pages)
		{
			String text = page.getText();
			if(text.contains(term)){
				denomonator++; //+= bookData.get(term);
			}
		}
		return Math.log10(pages.size()/denomonator);
	}

	public double tfIdf(String term, String[] page, ArrayList<Page> pages){
		double tf = termFrequency(term, page);
		double idf = inverseDocumentFrequency(term, pages);
		double result = tf*idf;
		if(Double.isNaN(result)){
			return -1;
		}
		else{
			return result;
		}
	}

	public void writeToFile(ArrayList<Book> bookList){
		File file;
		FileWriter writer;
		ArrayList<Page> pages;
		int currentBookNumber = 0;
		int currentPageNumber = 0;

		for(Book book : bookList){
			currentBookNumber++;
			pages = book.getPages();
			for(Page page : pages){
				currentPageNumber++;
				String text = page.getText().toLowerCase().replaceAll("\\p{P}", "").replaceAll("chapter", "").replaceAll("[0-9]", "");
				String tokens[] = text.split(" ");
				try {
					file = new File("resources" + File.separator + "books" + File.separator + "book_" + currentBookNumber + File.separator + "Page_" + currentPageNumber + ".txt");
					file.getParentFile().mkdirs();
					writer = new FileWriter(file);
					double value;
					for(String token : tokens){
						value = tfIdf(token, tokens, pages);
						if(value > 1 && value < 100){
							writer.write(token + "\t" + value + "\n");
						}
					}
					writer.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			currentPageNumber = 0;
		}
	}

	public HashMap<String, Double> readFile(int bookNumber, int pageNo){
		HashMap<String, Double> pageInfo = new HashMap<String, Double>();
		try{
			FileInputStream fis = new FileInputStream("resources" + File.separator + "books" + File.separator + "book_" + bookNumber + File.separator + "Page_" + pageNo + ".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			String line = "";

			while((line=br.readLine().toLowerCase())!=null){
				String tokens[] = line.split("\t");
				pageInfo.put(tokens[0], Double.parseDouble(tokens[1]));
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return pageInfo;
	}
}
