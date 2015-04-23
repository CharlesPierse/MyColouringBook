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
	wordBrowser wordBrowser;
	
	public PageCategoryFinder() {
		


		// TODO Auto-generated constructor stub
	}
	public wordBrowser getWordBrowser() {
		return wordBrowser;
	}
	public void setWordBrowser(wordBrowser wordBrowser) {
		this.wordBrowser = wordBrowser;
	}
	public LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
		   List mapKeys = new ArrayList(passedMap.keySet());
		   List mapValues = new ArrayList(passedMap.values());
		   Collections.sort(mapValues, Collections.reverseOrder());
		   Collections.sort(mapKeys, Collections.reverseOrder());

		   LinkedHashMap sortedMap = new LinkedHashMap();

		   Iterator valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((String)key, (Integer)val);
		               break;
		           }

		       }

		   }
		   return sortedMap;
		}
	
	public HashMap<String, Integer> getTopCategories(TermFrequencyInverseDocumentFrequency termFrequency, int bookNumber , int pageNumber, double depthLevel){
		HashMap<String, Integer> count=new HashMap<String,Integer>();
		ValueComparator bvc=new ValueComparator(count);
		StringBuffer sb =  new StringBuffer();
		
		HashMap<String, Double> pageInfo = termFrequency.readFile(bookNumber, pageNumber);
		sb=new StringBuffer();
		for(String word : pageInfo.keySet()){
	
			StringBuffer s1=wordBrowser.wordnet_hypernymTreeRecursive(word, "VB");
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
					if(!count.containsKey(token[i])){
						count.put(token[i], 0);
					}
					count.put(token[i], count.get(token[i])+1);
				}
			}

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
					if(!count.containsKey(token[i])){
						count.put(token[i], 0);
					}
					count.put(token[i], count.get(token[i])+1);
				}
			}
		}
		
		
		LinkedHashMap sort_map=this.sortHashMapByValuesD(count);
		return sort_map;
	}

}
