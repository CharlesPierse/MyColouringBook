//11510667
//Charles Pierse
//Comp30050
package TwitterBot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
public class ColorMap {
	
	String colorInfo = "";
	String rgbCode = "";
	String colorDescriptor = "";
	private HashMap<String,  String> map;
	
	public ColorMap(){
		map = new HashMap<String,String>();
		loadColorMap("colorList.txt");
		
	} 
	
	
	public Map<String, String> getMap() {
		return map;
	}

	public void loadColorMap(String filePath) {
		FileInputStream in = null;
		try {
			in = new FileInputStream("Resource"+ File.separator + filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} //ReadyMadeGallery.class.getResourceAsStream("Color_Unigram.txt");
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
				String[] cmvalues = line.split("\\t");
				colorInfo = cmvalues[0] +" " + cmvalues[1];
				colorDescriptor = cmvalues[0];
				rgbCode = cmvalues[2].substring(1,7);
				map.put(colorDescriptor,rgbCode); 
				
				//System.out.println("Key is: <" + colorDescriptor+">"); //test code
				//System.out.println("Value/Color name is: <#" + rgbCodeKey+">");//test code
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private String intToHex(int[] rgbInt){   //method for the conversion back of the newly produced int[] to a string in hex values
		
		String Rhex = Integer.toHexString(rgbInt[0]);
		if (Rhex.length() == 1){
			Rhex = "0" + Rhex;
		}
		String Ghex =  Integer.toHexString(rgbInt[1]);
		if (Ghex.length() == 1){
			Ghex = "0" + Ghex;
		}
		String Bhex = Integer.toHexString(rgbInt[2]);
		if (Bhex.length() == 1){
			Bhex = "0" + Bhex;
		}
		String hexStr = "#" + Rhex + Ghex + Bhex;
		//System.out.println("The new RGB in hex is: " + hexStr.toUpperCase());
		return hexStr; //returns the newly blended RGB hex in string form. 
		
	}
	
	 public int[] hexToIntRGB(String rgbString){  //this method does the conversion of a hex string into a decimal number
		
		String newRGB = rgbString;  
		String redHex = newRGB.substring(0,2);
		String greenHex= newRGB.substring(2,4);
		String blueHex = newRGB.substring(4,6);
		int Rcolor = Integer.parseInt(redHex, 16); //parses the newly converted Ints
		int Gcolor = Integer.parseInt(greenHex, 16);
		int Bcolor = Integer.parseInt(blueHex, 16);
		int[] RGBcolors = {Rcolor,Gcolor,Bcolor}; //array made up of three int values for RGB
		return  RGBcolors; //returns RGB colors in the form of a int array, will be called for distance function and blender
	}
	
	
	
	public String ColorBlender(String rgbString1, String rgbString2){  
		int[] blender1 = hexToIntRGB(rgbString1);
		int[] blender2 = hexToIntRGB(rgbString2); 
		int blendedResultRed = 0;
		int blendedResultGreen = 0;
		int blendedResultBlue = 0;
		blendedResultRed = (int) Math.round((((blender1[0]+blender2[0])/2) *1)); //weights can be adjusted here for desired mix type				
		blendedResultGreen = (int) Math.round((((blender1[1]+blender2[1])/2) *1));//currently set to one, but can be set to lower decimal values
		blendedResultBlue = (int) Math.round((((blender1[2]+blender2[2])/2) *1));		
		int[] blendedArray = {blendedResultRed, blendedResultGreen, blendedResultBlue};
		String hexRGB = intToHex(blendedArray);
		RGBdistance(rgbString1, rgbString2);
		
		return hexRGB;
		
			
	}
	
	public double RGBdistance (String rgbString1, String rgbString2){   
		int[] RGBpoint1 = hexToIntRGB(rgbString1);
		int[] RGBpoint2 =  hexToIntRGB(rgbString2);
		double a = Math.pow(RGBpoint1[0]- RGBpoint2[0],2);
		double b = Math.pow(RGBpoint1[1] - RGBpoint2[1],2);
		double c = Math.pow(RGBpoint1[2] - RGBpoint2[2],2);
		double distance = Math.sqrt(a + b + c); 
		//System.out.println("distance between two points is " + distance); 
			
		return distance;
		
	}
	
//	public static void main(String[] args) throws IOException{  //this is my test code used to implement the above methods
//		
//		ColorMap cm = new ColorMap();
//		cm.loadColorMap("Resource"+ File.separator + "colorList.txt");	
//		cm.ColorBlender("FFFFFF","FFF66F");			
//	}
	
}


