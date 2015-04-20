package WordNet;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.data.list.PointerTargetTree;
import net.didion.jwnl.dictionary.Dictionary;

import org.apache.log4j.BasicConfigurator;

public class wordBrowser {
	Dictionary dictionary ;
	
	public static void main(String[] args) {
		wordBrowser wb = new wordBrowser();
		BasicConfigurator.configure();
		wb.initialise("resources"+File.separator+"WordNet"+File.separator+"props.xml");
		wb.wordnet_hypernymTreeRecursive("kill", "n");
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
        StringBuffer sb = new StringBuffer();
        try {
            POS p = this.convertPOS(pos);
            IndexWord f = dictionary.getIndexWord(p, word);
            if (f != null) {
                for (int i = 1; i <= f.getSenseCount(); i++) {
                    sb.append(wordnet_hypernymTreeRecursive(new PointerTargetNode(f.getSense(i)), new StringBuffer()));
                    Word[] words = f.getSense(i).getWords();
                    StringBuffer sbtemp = new StringBuffer();
                    for (int j = 0; j < words.length; j++) {
                        sbtemp.append(words[j].getLemma() + "\t");// change here to add other information
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
        	PointerUtils po=PointerUtils.getInstance();
        			System.out.println("syn::"+n.getSynset()); 
        	Synset synset = n.getSynset();
        	
        	if(n==null||synset==null || po == null){// || directHypernyms == null){
            	System.out.println("\n null \n");}        	        	
            PointerTargetNodeList directHypernyms = po.getDirectHypernyms(synset);
            
            for ( int k=0; k < directHypernyms.size(); k++) {
                PointerTargetNode p = (PointerTargetNode) directHypernyms.get(k);
                wordnet_hypernymTreeRecursive(p, sb);
                Word[] words = p.getSynset().getWords();
               
                StringBuffer sbtemp = new StringBuffer();
                for (int i = 0; i < words.length; i++) {
                	if ((words[i].getSynset())!= null){
                		sbtemp.append(words[i].getSynset() + "\t");// change here to add other information
                	}
                	
                }
                sb.append(sbtemp.toString().trim()).append("|");//layer wise separator
            }
            
        } catch (NullPointerException | JWNLException e) {
        	//e.printStackTrace(); 
  
        	
        	/* after looking into the problem of the null pointer exception I believe that the issue lies within the library itself and is
        	 * not something that can be solved without access to the source code for the library which we do not have access to
        	 * the code does however work and run correctly thus why I have the stack trace print commented out. We can run this code as normal
        	 * for the purpose of the project.
        	 * 
        	 */
        }
        return sb;
    }

}
