package BasicBookReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.aliasi.io.FileLineReader;
import com.aliasi.tokenizer.*;


public class BasicBookReaderNeedsUpdating {
	private int wordCount = 0;

	Set<String> stopSet = new HashSet<String>();
	{
		stopSet.add("a");
		stopSet.add("be");
		stopSet.add("had");
		stopSet.add("it");
		stopSet.add("only");
		stopSet.add("she");
		stopSet.add("was");
		stopSet.add("about");
		stopSet.add("because");
		stopSet.add("has");
		stopSet.add("its");
		stopSet.add("of");
		stopSet.add("some");
		stopSet.add("we");
		stopSet.add("after");
		stopSet.add("been");
		stopSet.add("have");
		stopSet.add("last");
		stopSet.add("on");
		stopSet.add("such");
		stopSet.add("were");
		stopSet.add("all");
		stopSet.add("but");
		stopSet.add("he");
		stopSet.add("more");
		stopSet.add("one");
		stopSet.add("than");
		stopSet.add("when");
		stopSet.add("also");
		stopSet.add("by");
		stopSet.add("her");
		stopSet.add("most");
		stopSet.add("or");
		stopSet.add("that");
		stopSet.add("which");
		stopSet.add("an");
		stopSet.add("can");
		stopSet.add("his");
		stopSet.add("mr");
		stopSet.add("other");
		stopSet.add("the");
		stopSet.add("who");
		stopSet.add("any");
		stopSet.add("co");
		stopSet.add("if");
		stopSet.add("mrs");
		stopSet.add("out");
		stopSet.add("their");
		stopSet.add("will");
		stopSet.add("and");
		stopSet.add("corp");
		stopSet.add("in");
		stopSet.add("ms");
		stopSet.add("over");
		stopSet.add("there");
		stopSet.add("with");
		stopSet.add("are");
		stopSet.add("could");
		stopSet.add("inc");
		stopSet.add("mz");
		stopSet.add("s");
		stopSet.add("they");
		stopSet.add("would");
		stopSet.add("as");
		stopSet.add("for");
		stopSet.add("into");
		stopSet.add("no");
		stopSet.add("so");
		stopSet.add("this");
		stopSet.add("up");
		stopSet.add("at");
		stopSet.add("from");
		stopSet.add("is");
		stopSet.add("not");
		stopSet.add("says");
		stopSet.add("to");
		stopSet.add(".");
		stopSet.add(",");
		stopSet.add("!");
		stopSet.add("my");
		stopSet.add("I");
		stopSet.add("you");
		stopSet.add("yours");
		stopSet.add("thou");
		stopSet.add("-");
		stopSet.add("his");
		stopSet.add("hers");
		stopSet.add("me");
		stopSet.add("he");
		stopSet.add("she");
		stopSet.add(",");
		stopSet.add("\"");
		stopSet.add("'");
		stopSet.add("?");
		stopSet.add("He");
		stopSet.add("She");
		stopSet.add("His");
		stopSet.add("Hers");
		stopSet.add("Ours");
		stopSet.add("ours");
		stopSet.add("a");
		stopSet.add("A");
		stopSet.add("--");
		stopSet.add(";");
		stopSet.add(":");
		stopSet.add("am");
		stopSet.add("no");
		stopSet.add("No");
		stopSet.add("(");
		stopSet.add(")");
		stopSet.add("Where");
		stopSet.add("When");
		stopSet.add("But");
		stopSet.add("but");
<<<<<<< Updated upstream
		stopSet.add("~");

=======
		stopSet.add("it");
		stopSet.add("It");
		
		
>>>>>>> Stashed changes
	}
	int pageWordLimit = 350;
	HashMap<Integer, List<String>> wordMap = new HashMap<Integer, List<String>>();

	public static void main(String args[]){
		BasicBookReaderNeedsUpdating bbr = new BasicBookReaderNeedsUpdating(); //lpt = ling pipe test
		bbr.readBook();
		for(int key : bbr.wordMap.keySet()){
			System.out.println(key + "\t" + bbr.wordMap.get(key));
		}
	}

	public void readBook(){
		File file = new File("resources" + File.separator + "TextToLexer");
		boolean allowRead = false;

		TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
		StopTokenizerFactory stf = new StopTokenizerFactory(TOKENIZER_FACTORY, stopSet);

		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();

		int pageNumber = 0;
		int currentWordTotal = 0;
		try{
			FileLineReader lines = new FileLineReader(file,"UTF-8");
			for (String line : lines) {
				if(allowRead){
					if(line.contains("***END OF THE PROJECT GUTENBERG EBOOK")){
						allowRead = false;
					}
					else{
						Tokenizer tokenizer = stf.tokenizer(line.toCharArray(),0,line.length());
						tokenizer.tokenize(tokenList,whiteList);
						currentWordTotal += line.split(" ").length;
<<<<<<< Updated upstream
						if(currentWordTotal >= pageWordLimit){
							wordMap.put(pageNumber, tokenList);
=======
						if(currentWordTotal >= lpt.pageWordLimit){
							map.put(pageNumber, tokenList);
							lpt.wordCount += currentWordTotal;
>>>>>>> Stashed changes
							currentWordTotal = 0;
							pageNumber++;
							tokenList = new ArrayList<String>();
						}
					}
				}
				else{
					if(line.contains("***")){
						allowRead = true;
					}
				}
			}
			wordMap.put(pageNumber, tokenList);
			lines.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}

	public HashMap<Integer, List<String>> getWordMap(){
		return wordMap;
	}
}
