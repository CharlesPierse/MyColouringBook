package WordAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aliasi.io.FileLineReader;

public class BuilderReader {
	Set<String> colours = new HashSet<String>();{
		colours.add("red");
		colours.add("orange");
		colours.add("yellow");
		colours.add("green");
		colours.add("blue");
		colours.add("indigo");
		colours.add("beige");
		colours.add("silver");
		colours.add("gray");
		colours.add("black");
		colours.add("white");
		colours.add("navy");
	}

	
	//read this from a file.
	Set<String> ignoredWords = new HashSet<String>();
	{
		ignoredWords.add("a");
		ignoredWords.add("be");
		ignoredWords.add("had");
		ignoredWords.add("it");
		ignoredWords.add("only");
		ignoredWords.add("she");
		ignoredWords.add("was");
		ignoredWords.add("about");
		ignoredWords.add("because");
		ignoredWords.add("has");
		ignoredWords.add("its");
		ignoredWords.add("of");
		ignoredWords.add("some");
		ignoredWords.add("we");
		ignoredWords.add("after");
		ignoredWords.add("been");
		ignoredWords.add("have");
		ignoredWords.add("last");
		ignoredWords.add("on");
		ignoredWords.add("such");
		ignoredWords.add("were");
		ignoredWords.add("all");
		ignoredWords.add("but");
		ignoredWords.add("he");
		ignoredWords.add("more");
		ignoredWords.add("one");
		ignoredWords.add("than");
		ignoredWords.add("when");
		ignoredWords.add("also");
		ignoredWords.add("by");
		ignoredWords.add("her");
		ignoredWords.add("most");
		ignoredWords.add("or");
		ignoredWords.add("that");
		ignoredWords.add("which");
		ignoredWords.add("an");
		ignoredWords.add("can");
		ignoredWords.add("his");
		ignoredWords.add("mr");
		ignoredWords.add("other");
		ignoredWords.add("the");
		ignoredWords.add("who");
		ignoredWords.add("any");
		ignoredWords.add("co");
		ignoredWords.add("if");
		ignoredWords.add("mrs");
		ignoredWords.add("out");
		ignoredWords.add("their");
		ignoredWords.add("will");
		ignoredWords.add("and");
		ignoredWords.add("corp");
		ignoredWords.add("in");
		ignoredWords.add("ms");
		ignoredWords.add("over");
		ignoredWords.add("there");
		ignoredWords.add("with");
		ignoredWords.add("are");
		ignoredWords.add("could");
		ignoredWords.add("inc");
		ignoredWords.add("mz");
		ignoredWords.add("s");
		ignoredWords.add("they");
		ignoredWords.add("would");
		ignoredWords.add("as");
		ignoredWords.add("for");
		ignoredWords.add("into");
		ignoredWords.add("no");
		ignoredWords.add("so");
		ignoredWords.add("this");
		ignoredWords.add("up");
		ignoredWords.add("at");
		ignoredWords.add("from");
		ignoredWords.add("is");
		ignoredWords.add("not");
		ignoredWords.add("says");
		ignoredWords.add("to");
		ignoredWords.add("my");
		ignoredWords.add("I");
		ignoredWords.add("you");
		ignoredWords.add("yours");
		ignoredWords.add("thou");
		ignoredWords.add("his");
		ignoredWords.add("hers");
		ignoredWords.add("me");
		ignoredWords.add("he");
		ignoredWords.add("she");
		ignoredWords.add("He");
		ignoredWords.add("She");
		ignoredWords.add("His");
		ignoredWords.add("Hers");
		ignoredWords.add("Ours");
		ignoredWords.add("ours");
		ignoredWords.add("a");
		ignoredWords.add("A");
		ignoredWords.add("am");
		ignoredWords.add("no");
		ignoredWords.add("No");
		ignoredWords.add("Where");
		ignoredWords.add("When");
		ignoredWords.add("But");
		ignoredWords.add("but");

	}

	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	private String fileExtension = ".txt";
	private int lastBookNumber = 38;
	private HashMap<String, HashMap<String, Integer>> frequencyMap = new HashMap<String, HashMap<String, Integer>>();
	private HashMap<String, String> basicColourMap = new HashMap<String, String>();
	private Set<String> vocab = new HashSet<String>();

	private void readBasicColours(){
		String line = "";
		try{
			FileInputStream fis=new FileInputStream("resources" + File.separator + "base colours");
			BufferedReader br=new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine())!=null){
				String token[] = line.toLowerCase().split("\t");
				basicColourMap.put(token[0], token[1]);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	//	private void bigramCheck(String previousWord, String currentWord, String nextWord){
	//		HashMap<String, Integer> tempMap;
	//		int tempFrequency = 0;
	//		if(frequencyMap.containsKey(previousWord)){
	//			tempMap = frequencyMap.get(previousWord);
	//			if(tempMap.containsKey(currentWord)){
	//				tempFrequency = tempMap.get(currentWord)+1;
	//				tempMap.put(currentWord, tempFrequency);
	//				frequencyMap.put(previousWord, tempMap);
	//			}
	//			else{
	//				if(!ignoredWords.contains(previousWord)){
	//					tempMap.put(currentWord, 1);
	//					frequencyMap.put(previousWord, tempMap);
	//				}
	//			}
	//		}
	//		else if(frequencyMap.containsKey(currentWord)){
	//			tempMap = frequencyMap.get(currentWord);
	//			if(tempMap.containsKey(nextWord)){
	//				tempFrequency = tempMap.get(nextWord)+1;
	//				tempMap.put(nextWord, tempFrequency);
	//				frequencyMap.put(currentWord, tempMap);
	//			}
	//			else{
	//				if(!ignoredWords.contains(nextWord)){
	//					tempMap.put(nextWord, 1);
	//					frequencyMap.put(currentWord, tempMap);
	//				}
	//			}
	//		}
	//		else{
	//			if(!ignoredWords.contains(previousWord) && !ignoredWords.contains(nextWord)){
	//				tempMap = new HashMap<String, Integer>();
	//				tempMap.put(currentWord, 1);
	//				frequencyMap.put(previousWord, tempMap);
	//				tempMap = new HashMap<String, Integer>();
	//				tempMap.put(nextWord, 1);
	//				frequencyMap.put(currentWord, tempMap);
	//			}
	//		}
	//	}

	public void readBook(int bookNumber){
		File file = new File(filePath + bookNumber + fileExtension);
		boolean allowRead = false;

		try{
			FileLineReader book = new FileLineReader(file,"UTF-8");
			for (String line : book) {
				if(allowRead){
					if(line.contains("***")){
						allowRead = false;
					}
					else{
						String[] splitLine = line.toLowerCase().split(" ");
						for(int i = 0; i < splitLine.length; i++){
							splitLine[i] = splitLine[i].replaceAll("[^a-zA-Z]", " ");//should remove all shitespaces and punctuation.
							if(splitLine[i].split(" ").length > 1){
								String tokens[] = splitLine[i].split(" ");
								tokens[0].replaceAll("[^a-zA-Z]", "");
								tokens[1].replaceAll("[^a-zA-Z]", "");
								if(tokens[0] != ""){
									vocab.add(tokens[0]);
								}
								else if(tokens[1] != ""){
									vocab.add(tokens[1]);
								}
							}
							else{
								vocab.add(splitLine[i].replaceAll("[^a-zA-Z]", ""));
							}

						}
						//						for(int i = 1; i < splitLine.length-1; i++){
						//							//							if(colours.contains(splitLine[i])){
						//							vocab.add(splitLine[i]);
						//							//								bigramCheck(splitLine[i-1], splitLine[i], splitLine[i+1]);
						//							//							}
						//						}
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
		vocab.remove("");
	}

	public void saveMap(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("resources" + File.separator + "word_colour_map.txt", "UTF-8");
			//			for(String outterKey : frequencyMap.keySet()){
			//				for(String innerKey : frequencyMap.get(outterKey).keySet()){
			//					writer.println(outterKey + "\t" + innerKey + "\t" + frequencyMap.get(outterKey).get(innerKey));
			//				}
			//			}
			for(String s : vocab){
				writer.println(s);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		BuilderReader reader = new BuilderReader();
		reader.readBasicColours();
		int highest = 0;
		String i = "";
		String o = "";
		for(int currentBook = 1; currentBook <= reader.lastBookNumber; currentBook++){
			reader.readBook(currentBook);
		}
		//		for(String outterKey : reader.frequencyMap.keySet()){
		//			HashMap<String, Integer> currentMap = reader.frequencyMap.get(outterKey);
		//			for(String innerKey : currentMap.keySet()){
		//				System.out.println(outterKey + "\t" + innerKey + "\t" + currentMap.get(innerKey));
		//			}
		//		}
		reader.saveMap();
	}
}