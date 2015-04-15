package WordAnalysis;

import java.io.File;
import java.util.HashMap;

import com.aliasi.io.FileLineReader;

public class TermFrequencyInverseDocumentFrequency {

	//	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	//	private String fileExtension = ".txt";
	//	private HashMap<String, Integer> bookMap;
	private String[] splitPage;
	//	private int lastBookNumber = 38;
	//	private HashMap<Integer, HashMap<String, Integer>> bookCollectionMap = new HashMap<Integer, HashMap<String, Integer>>();

	//	public void readBook(int bookNumber){
	//		File file = new File(filePath + bookNumber + fileExtension);
	//		boolean allowRead = false;
	//		bookMap = new HashMap<String, Integer>();
	//		try{
	//			FileLineReader book = new FileLineReader(file,"UTF-8");
	//			for (String line : book){
	//				if(allowRead){
	//					if(line.contains("***")){
	//						break;
	//					}
	//					else{
	//						line = line.replaceAll("[^a-zA-z]", " ");
	//						String[] splitLine = line.split(" ");
	//						for(String word : splitLine){
	//							if(!word.equals("")){
	//								frequencyAcumulator(word);
	//							}
	//						}
	//					}
	//				}
	//				else{
	//					if(line.contains("***")){
	//						allowRead = true;
	//					}
	//				}
	//			}
	//			book.close();
	//		} catch(Exception e) {
	//			e.printStackTrace();
	//		}
	//		bookCollectionMap.put(bookNumber, bookMap);
	//	}

	private int termFrequency(String term, String[] page){
		int frequency = 0;
		for(String word : page){
			if(word.equals(term)){
				frequency++;
			}
		}
		return frequency;
	}

	private double inverseDocumentFrequency(String term, HashMap<Integer, String> book){ //the full directory of texts parameter is not needed, as it is an instance variable.
		double denomonator = 0;
		for(String page : book.values())
		{
			if(page.contains(term)){
				denomonator++; //+= bookData.get(term);
			}
		}
		return Math.log10(book.size()/denomonator);
	}

	public double tfIdf(String term, String[] page, HashMap<Integer, String> book){
		double tf = termFrequency(term, page);
		double idf = inverseDocumentFrequency(term, book);
		double result = tf*idf;
		if(Double.isNaN(result)){
			return -1;
		}
		else{
			return result;
		}
	}

	public static void main(String args[]){
		TermFrequencyInverseDocumentFrequency tfIDF = new TermFrequencyInverseDocumentFrequency();
		//		for(int currentBook = 1; currentBook <= tfIDF.lastBookNumber; currentBook++){
		//			tfIDF.readBook(currentBook);
		//		}
		//		for(int currentBook = 1; currentBook <= tfIDF.lastBookNumber; currentBook++){
		//			System.out.println(tfIDF.tfIdf("twitte", currentBook));
		//		}
	}
}
