package BotDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import WordNet.wordBrowser;


public class PageCategoryFinder {


	ArrayList<String> catSet = new ArrayList<String>();

	public HashMap<String, ArrayList<String>> getTopCategories(ArrayList<String> wordsInPage, double depthLevel, wordBrowser wordBrowser){
		HashMap<String, ArrayList<String>> catWords = new HashMap<String, ArrayList<String>>();
		StringBuffer sb;


		for(String word : wordsInPage){
			if(word.toLowerCase().equals("chapter")){}
			else{
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
		}

		return catWords;
	}

	private void loadCatSet(){
		FileInputStream in = null;
		BufferedReader br = null;
		try {
			in = new FileInputStream("resources"+ File.separator + "CatFile.txt");

			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String line = null;
		try {
			while((line = br.readLine()) != null)
			{
				catSet.add(line.replaceAll("[^a-zA-z]", ""));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public HashMap<String,ArrayList<String>> getPairings(ArrayList<String> WordsIn, double depthLevel, wordBrowser wordBrowser){
		HashMap<String,String> pairMap = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> pageCatigories = getTopCategories(WordsIn ,depthLevel, wordBrowser);
		HashMap<String, ArrayList<String>> bestCategoryMap = new HashMap<String, ArrayList<String>>();
		int highestWordHit = 0;
		String bestCatagory = "";

		loadCatSet();
		for(String catagory : catSet){
			if(pageCatigories.containsKey(catagory) && pageCatigories.get(catagory).size() > highestWordHit){
				highestWordHit = pageCatigories.get(catagory).size();
				bestCatagory = catagory;
			}
		}

		if(bestCatagory != ""){
			bestCategoryMap.put(bestCatagory, pageCatigories.get(bestCatagory));
			return bestCategoryMap;
		}
		else{
			return null;
		}
	}
}
