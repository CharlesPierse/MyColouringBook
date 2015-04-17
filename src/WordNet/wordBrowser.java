package WordNet;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
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
		//wb.getCategory("kill", "n");
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

//	public void getHypernymTree(IndexWord word){
//		try {
//			PointerTargetTree hypernym = PointerUtils.getInstance().getHypernymTree(word.getSense(0));
//			System.out.println("Hyponyms of \"" + word.getLemma() + "\":");
//			hypernym.print();//getroot.getchildtree
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	
//	public String getCategory(String word, String pos){
//		try {
//			POS p=convertPOS(pos);
//			IndexWord w=dictionary.getIndexWord(p,word);
//			if(w!=null){
//				PointerTargetTree phypernym =PointerUtils.getInstance().getHyponymTree(w.getSense(0));
//				List l=phypernym.toList();
//				for (int i = 0; i < l.size(); i++) {
//					System.out.println(l.get(i));
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
	


public StringBuffer wordnet_hypernymTreeRecursive(String word, String pos) {
        StringBuffer sb = new StringBuffer();
        try {
            POS p = this.convertPOS(pos);
            IndexWord f = dictionary.getIndexWord(p, word);
            if (f != null) {
                for (int i = 1; i <= f.getSenseCount(); i++) {
                    sb.append(wordnet_hypernymTreeRecursive(new PointerTargetNode(f.getSense(i)), new StringBuffer()));
                    System.out.println(sb);
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
        	
        	
            PointerTargetNodeList directHypernyms = po.getDirectHypernyms(n.getSynset());
            if(n==null||n.getSynset()==null || po == null || directHypernyms == null){
            	System.out.println("\n null \n");}
            for ( int k=0; k < directHypernyms.size(); k++) {
                PointerTargetNode p = (PointerTargetNode) directHypernyms.get(k);
                wordnet_hypernymTreeRecursive(p, sb);
                Word[] words = p.getSynset().getWords();
               
                StringBuffer sbtemp = new StringBuffer();
                for (int i = 0; i < words.length; i++) {
                	//System.out.println(words[i]);
                	if ((words[i].getSynset())!= null){
                		sbtemp.append(words[i].getSynset() + "\t");// change here to add other information
                	}
                	
                }
                sb.append(sbtemp.toString().trim()).append("|");//layer wise separator
            }
            
        } catch (Exception e) {
           
        	//e.printStackTrace();
        }
        return sb;
    }

}
