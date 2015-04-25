package BotDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.BasicConfigurator;

import WordAnalysis.TermFrequencyInverseDocumentFrequency;
import WordNet.wordBrowser;


public class PageCategoryFinder {

	public HashMap<String, ArrayList<String>> getTopCategories(ArrayList<String> wordsInPage, double depthLevel, wordBrowser wordBrowser){
		HashMap<String, ArrayList<String>> catWords = new HashMap<String, ArrayList<String>>();
		StringBuffer sb;


		for(String word : wordsInPage){
			sb=new StringBuffer();
			StringBuffer s1 = wordBrowser.wordnet_hypernymTreeRecursive(word, "VB");
			if(s1!=null){
				String output1 = s1.toString();
				String lines[]=output1.split("\n");
				for (int k = 0; k < lines.length; k++) {
					String token[]=lines[k].split("\\|");
					int index=(int) (token.length*depthLevel);
					sb.append(token[index].trim()).append("\t");
				}
				String token[] = sb.toString().split("\t");
				for (int i = 0; i < token.length; i++) {
					
					if (token[i].matches(".*[A-Z].*")) {

					}
					else{
						if(!catWords.containsKey(token[i])){
							ArrayList<String> cWords = new ArrayList<String>();
							cWords.add(word);
							catWords.put(token[i], cWords);
						}
						ArrayList<String> tempCwords =catWords.get(token[i]);
						tempCwords.add(word);
						catWords.put(token[i], tempCwords);
					}
				}

			}
			sb = new StringBuffer();

			StringBuffer s2 = wordBrowser.wordnet_hypernymTreeRecursive(word, "NN");
			if(s2 != null){
				String output2 = s2.toString();
				String lines[]=output2.split("\n");
				for (int k = 0; k < lines.length; k++) {
					String token[]=lines[k].split("\\|");
					int index=(int) (token.length*depthLevel);
					sb.append(token[index].trim()).append("\t");
				}
				String token[] = sb.toString().split("\t");

				for (int i = 0; i < token.length; i++) {
					if (token[i].matches(".*[A-Z].*")) {


					}
					else{
						if(!catWords.containsKey(token[i])){
							ArrayList<String> cWords = new ArrayList<String>();
							cWords.add(word);
							catWords.put(token[i], cWords);
						}
						ArrayList<String> tempCwords =catWords.get(token[i]);
						tempCwords.add(word);
						catWords.put(token[i], tempCwords);
					}
				}
			}
		}

		return catWords;
	}

	public HashMap<String,String> getPairings(ArrayList<String> WordsIn, double depthLevel, wordBrowser wordBrowser){
		HashMap<String,String> pairMap = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> val = getTopCategories(WordsIn ,depthLevel, wordBrowser);

		//		String cat1 = "color";
		//		if(val.get(cat1) != null){
		//			System.out.print("\n" + cat1 + "\t");
		//			for(String word : val.get(cat1)){
		//				System.out.print(word + " ");
		//			}
		//		}
		//		
		//		String cat2 = "colour";
		//		if(val.get(cat2) != null){
		//			System.out.print("\n" + cat2 + "\t");
		//			for(String word : val.get(cat2)){
		//				System.out.print(word + " ");
		//			}
		//		}
		//
		//		String cat3 = "nature";
		//		if(val.get(cat3) != null){
		//			System.out.print("\n" + cat3 + "\t");
		//			for(String word : val.get(cat3)){
		//				System.out.print(word + " ");
		//			}
		//		}
		//
		//		String cat4 = "art";
		//		if(val.get(cat4) != null){
		//			System.out.print("\n" + cat4 + "\t");
		//			for(String word : val.get(cat4)){
		//				System.out.print(word + " ");
		//			}
		//		}
		//		
		//		System.out.println();
		
		
//		
//		for(String key : val.keySet()){
//			System.out.print(key + "||||||||");
//			ArrayList<String> wordList = val.get(key);
//			for(String word : wordList){
//				System.out.print(word + " ");
//			}
//			System.out.print("\n");
//		}
//
//		//	pairMap.put(WordsIn[], val);
//
//		System.out.println("\n \n \n");


		return pairMap;
	}
	
	private String catagoryDecider(HashMap<String, ArrayList<String>> pageData){
		
		return null;
	}
	

}
