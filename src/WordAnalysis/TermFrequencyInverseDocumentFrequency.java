package WordAnalysis;

import java.util.HashMap;


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
}
