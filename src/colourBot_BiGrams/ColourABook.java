package colourBot_BiGrams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import BasicBookReader.*;

public class ColourABook {

	private HashMap<String, HashMap<String,String>> fullWordMap;
	HashMap<Integer, List<String>> bookMap;

	public void fillMap(){
		String line="";
		fullWordMap = new HashMap<String, HashMap<String,String>>();
		try {
			FileInputStream fis=new FileInputStream("resources"+File.separator+"fullWordMap.txt");
			BufferedReader br=new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine())!=null){
				if(!line.contains("?")){
					String token[]=line.toLowerCase().split("\t");
					if(token.length==3){
						if(!fullWordMap.containsKey(token[0])){
							fullWordMap.put(token[0], new HashMap<String, String>());
							fullWordMap.get(token[0]).put(token[1], token[2]);
						}
						else if(!fullWordMap.get(token[0]).containsKey(token[1])){
							fullWordMap.put(token[0], new HashMap<String, String>());
							fullWordMap.get(token[0]).put(token[1], token[2]);
						}
					} 
				}
			}
			br.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String colourBook(){
		ColourBlender blender = new ColourBlender();
		String currentMix = "";
		HashMap<String, String> tempMap;
		List<String> line;
		for(int lineNumber : bookMap.keySet()){
			line = bookMap.get(lineNumber);
			System.out.println(lineNumber + "\t" +line);
			System.out.println(currentMix);
			for(String word : line){
				if(fullWordMap.containsKey(word)){
					tempMap = fullWordMap.get(word);
					for(String hex : tempMap.values()){
						currentMix = blender.colourBlend(currentMix, hex);
					}
				}
			}
		}
		return currentMix;
	}

	public static void main(String args[]){
		ColourABook bookColourer = new ColourABook();
		BasicBookReaderNeedsUpdating basicBookReader = new BasicBookReaderNeedsUpdating();
		basicBookReader.readBook();
		bookColourer.fillMap();
		bookColourer.bookMap = basicBookReader.getWordMap();
		System.out.println("The result is: " + bookColourer.colourBook());
	}
}