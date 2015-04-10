package BasicBookReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BookObjectPopulater {

	private String filePath = "resources" + File.separator +"books" + File.separator + "Book_";
	private String fileExtension = ".txt";
	private int lastBookNumber = 38;
	private int pageWordLimit = 300;

	private void readBook(int bookNumber){
		boolean allowedRead = false;
		int currentWordCount = 0;
		String currentLine = "";
		String overflowWords = "";
		FileInputStream fis;
		BufferedReader br;
		String line = "";
		String title = "";
		String author = "";
		try{
			fis = new FileInputStream(filePath + bookNumber + fileExtension);
			br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			while((line=br.readLine())!=null){
				if(allowedRead){
					if(line.contains("***")){
						break;
					}
					else{
						if(overflowWords != ""){
							String[] splitOverflow = overflowWords.split(" ");
							for(int lineIndex = 0; lineIndex < splitOverflow.length; lineIndex++){
								if(currentWordCount <= pageWordLimit){
									currentLine += splitOverflow[lineIndex] + " ";
									currentWordCount++;
								}
								else{
									overflowWords += splitOverflow[lineIndex] + " ";
								}
							}
						}
						else{
							String[] splitLine = line.split(" ");
							for(int lineIndex = 0; lineIndex < splitLine.length; lineIndex++){
								if(currentWordCount <= pageWordLimit){
									currentLine += splitLine[lineIndex] + " ";
									currentWordCount++;
								}
								else{
									overflowWords += splitLine[lineIndex] + " ";
								}
							}
						}
						System.out.println(currentLine);
						currentLine = "";
					}
				}
				else{
					if(line.contains("***")){
						allowedRead = true;
					}
					else if(line.contains("Title:")){
						title = line.substring(7, line.length());
					}
					else if(line.contains("Author:")){
						author = line.substring(8, line.length());
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(title + " by " + author);
	}

	public static void main(String args[]){
		BookObjectPopulater populater = new BookObjectPopulater(); //lpt = ling pipe test
		//for(int i = 1; i <= 38; i++){
		populater.readBook(1);
		//}
	}
}