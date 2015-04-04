package colourBot_BiGrams;

public class ColourBlender {
	public String colourBlend(double alpha, String colour1, String colour2)
	{
		if(colour1 == null || colour1 == ""){
			return colour2;
		}
		else{
			if(colour2 == null || colour2 == ""){
				return colour1;
			}
			else{
				int red = (int) ((getRedFromHex(colour1)*alpha) + (getRedFromHex(colour2)*(1-alpha)));
				int green = (int) ((getGreenFromHex(colour1)*alpha) + (getGreenFromHex(colour2)*(1-alpha))); 
				int blue = (int) ((getBlueFromHex(colour1)*alpha) + (getBlueFromHex(colour2)*(1-alpha)));
				return "#" + getHexFromInt(red) + getHexFromInt(green) + getHexFromInt(blue);
			}
		}
	}

	public String colourBlend(String colour1, String colour2)
	{
		if(colour1 == null || colour1 == ""){
			return colour2;
		}
		else{
			if(colour2 == null || colour2 == ""){
				return colour1;
			}
			else{
				double alpha = 0.5;
				return (colourBlend(alpha, colour1, colour2));
			}
		}
	}

	public double rgbDistance(String rgb1, String rgb2){
		return Math.sqrt(Math.pow(getRedFromHex(rgb1)-getRedFromHex(rgb2),2) 
				+ Math.pow(getGreenFromHex(rgb1)-getGreenFromHex(rgb2),2) 
				+ Math.pow(getBlueFromHex(rgb1)-getBlueFromHex(rgb2),2));
	}

	public String getHexFromInt(int value){
		String output="";
		if(value<16){
			output= "0" + Integer.toHexString(value);
		}else{
			output=  Integer.toHexString(value);
		}
		return output;		
	}

	private int getRedFromHex(String hex){
		return Integer.parseInt(hex.substring(1, 3),16);		
	}

	private int getGreenFromHex(String hex){
		return Integer.parseInt(hex.substring(3, 5),16);	
	}

	private int getBlueFromHex(String hex){
		return Integer.parseInt(hex.substring(5, 7),16);
	}
}
