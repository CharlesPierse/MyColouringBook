package WordAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.aliasi.io.FileLineReader;

public class TermFrequencyInverseDocumentFrequency {

	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	private String fileExtension = ".txt";
	private HashMap<String, Integer> bookMap;
	private int lastBookNumber = 38;
	private HashMap<Integer, HashMap<String, Integer>> bookCollectionMap = new HashMap<Integer, HashMap<String, Integer>>();

	public void readBook(int bookNumber){
		File file = new File(filePath + bookNumber + fileExtension);
		boolean allowRead = false;
		bookMap = new HashMap<String, Integer>();
		try{
			FileLineReader book = new FileLineReader(file,"UTF-8");
			for (String line : book){
				if(allowRead){
					if(line.contains("***")){
						break;
					}
					else{
						line = line.replaceAll("[^a-zA-z]", " ");
						String[] splitLine = line.split(" ");
						for(String word : splitLine){
							if(!word.equals("")){
								frequencyAcumulator(word);
							}
						}
					}
				}
				else{
					if(line.contains("***")){
						allowRead = true;
					}
				}
			}
			book.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		bookCollectionMap.put(bookNumber, bookMap);
	}

	private void frequencyAcumulator(String word) {
		if(bookMap.containsKey(word)){
			int tempFrequency = bookMap.get(word)+1;
			bookMap.put(word, tempFrequency);
		}
		else{
			bookMap.put(word, 1);
		}
	}

	private int termFrequency(String term, int bookNumber){
		HashMap<String, Integer> bookData = bookCollectionMap.get(bookNumber);
		if(bookData.containsKey(term)){
			return bookData.get(term);
		}
		else{
			return 0;
		}
	}

	private double inverseDocumentFrequency(String term){ //the full directory of texts parameter is not needed, as it is an instance variable.
		double denomonator = 0;
		HashMap<String, Integer> bookData;
		for(int currentBook = 1; currentBook <= lastBookNumber; currentBook++){
			bookData = bookCollectionMap.get(currentBook);
			if(bookData.containsKey(term)){
				denomonator++; //+= bookData.get(term);
			}
		}
		return Math.log10(lastBookNumber/denomonator);
	}

	private double tfIdf(String term, int bookNumber){
		double x = termFrequency(term, bookNumber);
		double y = inverseDocumentFrequency(term);
		return x*y;
	}

	public static void main(String args[]){
		TermFrequencyInverseDocumentFrequency tfIDF = new TermFrequencyInverseDocumentFrequency();
		for(int currentBook = 1; currentBook <= tfIDF.lastBookNumber; currentBook++){
			tfIDF.readBook(currentBook);
		}
		//		for(int bookNumber : tfIDF.bookCollectionMap.keySet()){
		//			HashMap<String, Integer> map = tfIDF.bookCollectionMap.get(bookNumber);
		//			for(String key : map.keySet()){
		//				System.out.println(bookNumber + "\t" + key + "\t" + map.get(key));
		//			}
		//		}
		for(int currentBook = 1; currentBook <= tfIDF.lastBookNumber; currentBook++){
			System.out.println(tfIDF.tfIdf("leopold", currentBook));
		}
	}
}
