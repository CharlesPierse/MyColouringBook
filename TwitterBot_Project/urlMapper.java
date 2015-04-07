package TwitterBot_Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class urlMapper {
	String RGBcode ="";
	String URL ="";
	ReadyMadeGallery rm = new ReadyMadeGallery();
	ColorMap cm = new ColorMap();
	Map<String,String> map = new HashMap<String,String>();

	public void loadRMtweets(){
		rm.loadAll();
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator + "EveryColorTweets.txt");
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
		try{
			while((line = br.readLine()) != null)
			{
				String[] UMvalues = line.split("\\t");
				RGBcode = UMvalues[0].substring(2, 8);
				URL = UMvalues[1];
				String rmName = readyMadeDetermine(RGBcode);
				map.put(rmName, URL);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getMap() {
		return map;
	}

	public String readyMadeDetermine(String RGB){		String idealRM = null;

		cm.loadColorMap("colorList.txt");

		for(Entry<String,String> entry : rm.getMap().entrySet()){
			String RMkey = entry.getKey();
			if( RMkey != null){
				RMkey = RMkey.substring(1,7);
			}
			if (cm.RGBdistance(RGB.toUpperCase(), RMkey) <= 25.00){ //range i have set for close matches
				RMkey = '#' + RMkey;
				idealRM =  rm.getMap().get(RMkey);
				return idealRM;				
			}
		}
		return idealRM;
	}
}
