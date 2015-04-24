package WordNet;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.dictionary.Dictionary;

import org.apache.log4j.BasicConfigurator;

public class wordBrowser {
	Dictionary dictionary ;

	private void writeFile(){
		PrintWriter writer;
		try{ 
			writer = new PrintWriter("resources" + File.separator + "trainFile.txt");
			//writer.println();
			//Need to know data type of class-features to print
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void initialise(String filePath){
		try {
			JWNL.initialize(new FileInputStream(filePath));
			dictionary = Dictionary.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public POS convertPOS(String pos) {
		if (pos.startsWith("JJ")) {
			return POS.ADJECTIVE;
		}
		if (pos.startsWith("NN")) {
			return POS.NOUN;
		}
		if (pos.startsWith("VB")) {
			return POS.VERB;
		}
		if (pos.startsWith("RB")) {
			return POS.ADVERB;
		}
		return POS.NOUN;
	}


	public StringBuffer wordnet_hypernymTreeRecursive(String word, String pos) {
		StringBuffer sb = null;
		try {
			POS p = this.convertPOS(pos);
			IndexWord f = dictionary.getIndexWord(p, word);
			if (f != null) {
				sb = new StringBuffer();
				for (int i = 1; i <= f.getSenseCount(); i++) {
					sb.append(wordnet_hypernymTreeRecursive(new PointerTargetNode(f.getSense(i)), new StringBuffer()));
					Word[] words = f.getSense(i).getWords();
					StringBuffer sbtemp = new StringBuffer();
					for (int j = 0; j < words.length; j++) {
						sbtemp.append(words[j].getLemma() + "\t");
					}
					sb.append(sbtemp.toString().trim()).append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb;

	}

	public StringBuffer wordnet_hypernymTreeRecursive(PointerTargetNode n, StringBuffer sb) {
		try {
			PointerTargetNodeList directHypernyms = PointerUtils.getInstance().getDirectHypernyms(n.getSynset());
			for (int k = 0; k < directHypernyms.size(); k++) {
				PointerTargetNode p = (PointerTargetNode) directHypernyms.get(k);
				wordnet_hypernymTreeRecursive(p, sb);
				Word[] words = p.getSynset().getWords();
				StringBuffer sbtemp = new StringBuffer();
				for (int i = 0; i < words.length; i++) {
					sbtemp.append(words[i].getLemma() + "\t");
				}
				sb.append(sbtemp.toString().trim()).append("|");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return sb;
	}
	
}
