package WordNet;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.list.PointerTargetTree;
import net.didion.jwnl.dictionary.Dictionary;

public class wordBrowser {
	Dictionary dictionary ;
	
	public static void main(String[] args) {
		wordBrowser wb = new wordBrowser();
		wb.initialise("resources"+File.separator+"WordNet"+File.separator+"props.xml");
		wb.getCategory("horse", "n");
	}
	
	public void initialise(String filePath){
		try {
			JWNL.initialize(new FileInputStream(filePath));
			dictionary = Dictionary.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getHypernymTree(IndexWord word){
		try {
			PointerTargetTree hypernym = PointerUtils.getInstance().getHypernymTree(word.getSense(0));
			System.out.println("Hyponyms of \"" + word.getLemma() + "\":");
			hypernym.print();//getroot.getchildtree
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public String getCategory(String word, String pos){
		try {
			POS p=convertPOS(pos);
			IndexWord w=dictionary.getIndexWord(p,word);
			if(w!=null){
				PointerTargetTree phypernym=PointerUtils.getInstance().getHypernymTree(w.getSense(0));
				List l=phypernym.toList();
				for (int i = 0; i < l.size(); i++) {
					System.out.println(l.get(i));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	private POS convertPOS(String pos){
		POS output=null;
		try {
			if(pos.equals("n")){
				output=POS.NOUN;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return output;
	}
}
