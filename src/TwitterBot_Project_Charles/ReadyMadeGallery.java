
//11510667
//charles pierse

package TwitterBot_Project_Charles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ReadyMadeGallery  {

	Map<String, String> map= new HashMap<String,String>();
	ColorMap cm = new ColorMap();
	String colorName ="";
	String RGBcode = "";

	public void loadBracketBigrams()  {
		
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator + "bracketedColorBigram.txt");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String line = null;
		try {
			while((line = br.readLine()) != null)
			{
				String[] rmvalues = line.split("\\t");
				colorName = rmvalues[0] +" " + rmvalues[1];
				RGBcode = assignRGB(rmvalues[0],rmvalues[1]);
				map.put(RGBcode,colorName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}


	public void loadUnbracketedBigrams()  {
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator +"UnbracketedColorBigram.txt");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String line = null;
		try {
			while((line = br.readLine()) != null)
			{
				String[] rmvalues = line.split("\\t");
				colorName = rmvalues[0] +" " + rmvalues[1];
				RGBcode = assignRGB(rmvalues[0].toLowerCase(),rmvalues[1].toLowerCase());
				map.put(RGBcode,colorName);
			}
			br.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void loadUnigrams() {
		
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator +"Color_Unigram.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String line = null;
		String second_word = null;
		try {
			while((line = br.readLine()) != null)
			{
				String[] rmvalues = line.split("\\t");
				for(int i = 0; i< rmvalues[0].length(); i++){  
					if (cm.getMap().containsKey(rmvalues[0].substring(0, i))){ //checks if the letters in the given range are present in the 
						//color list
						second_word = rmvalues[0].substring(i, rmvalues[0].length());
						rmvalues[0] = rmvalues[0].substring(0, i);
					}


				}
				colorName = rmvalues[0]+ second_word;
				//System.out.println("Value/Color name is: <" + colorName+">");//test code
				RGBcode = assignRGB(rmvalues[0].toLowerCase(),second_word);
				map.put(RGBcode,colorName);
			}
			br.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPluralBigrams() {
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator +"Plural_bigram.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line = null;
		try {
			while((line = br.readLine()) != null)
			{
				String[] rmvalues = line.split("\\t");
				colorName = rmvalues[0] +" " + rmvalues[1];
				rmvalues[1] =  rmvalues[1].substring(0, rmvalues[1].length() -1);	
				//System.out.println("Value/Color name is: <" + colorName+">");//test code
				RGBcode = assignRGB(rmvalues[0].toLowerCase(),rmvalues[1].toLowerCase());
				map.put(RGBcode,colorName);
			}
			br.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}




	}

	public void loadAll(){
		loadUnigrams();
		loadPluralBigrams();
		loadUnbracketedBigrams();
		loadBracketBigrams();

	}

	public double linguistic_rules(String cname1, String cname2){ //to be filled as the project progresses
		double header = 0.6; 
		
		return header;
	}


	public String assignRGB(String cname1, String cname2) {

		cm.loadColorMap("colorList.txt");
		String rgbval1 = null;
		String rgbval2 = null;
		if (cm.getMap().containsKey(cname1)){

			rgbval1 = cm.getMap().get(cname1);
		}

		else {

			rgbval1 = "7F7F7F"; //if there is no corresponding color, it adds the 
			//given value instead, which is a neutral color midway between white and black
		}

		if (cm.getMap().containsKey(cname2)){

			rgbval2 = cm.getMap().get(cname2);
		}


		else {

			rgbval2 = "7F7F7F";
		}

		String RGBstring = cm.ColorBlender(rgbval1.toUpperCase(), rgbval2.toUpperCase());
		//System.out.println("new mixed color is : " + RGBstring.toUpperCase());

		return RGBstring;


	}


	public static void main(String[] args) throws IOException{  //this is my test code used to implement the above methods
		ReadyMadeGallery rm = new ReadyMadeGallery();
		rm.loadAll();
	}


	public Map<String, String> getMap() {
		return map;
	}

}
