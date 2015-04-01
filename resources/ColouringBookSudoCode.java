private String Start = ""
private String End = "*** END OF THIS PROJECT GUTENBERG EBOOK [Title] ***"
private String Title = ""
private String Author = ""
private boolean SeenEndOfBook = false
private int PageWordLimit = ???
private ???[] ChapterColours


public boolean bookFinished(){
	return SeenEndOfBook
}

public void readPage(linesLeftToRead){
	int currentWordCount = 0
	for(line : linesLeftToRead)
		if(line.contains(End))
			//end page
			
		else
			if(currentWordCount >= PageWordLimit)
				//end page
			else
				//read line
				currentWordCount++
}

removeUnnessaryWords(wordsDataStructure){
	//somthing somthing somthing 
	//read up on EnglishStopTokenizerFactory
	return refinedWordsDataStructure
}

analyseWords(refinedWordsDataStructure){
	//for the most basic linguistic rule:
	for(word : refindedWords)
		if(HashMap<String, HashMap<String, String>>.containsKey(word))//the hashMap is from readyMadeGallery.
			innerMap = HashMap<String, HashMap<String, String>>.get(key)
			if(innerMap.containsKey(words+1))
				hexForWord = innerMap.get(words+1)
			else
				for(String key : innerMap.keyset())
					hexForWord = colourBlender.blend(hexForWord, innerMap.get(key)) //and declaration in colourBlender for: blend(null, hex){ return hex }
	return wordHexDataStructure
}

colourPage(wordHexDataStructure){
	for(hex : wordHexDataStructure)
		pageColour = colourBlender.blend(pageColour, hex)
	return allPageColours
}

colourChapter(
	//need to edit some of the above methods to account for chapters.
}

blendChapters{
	//iterate through data structure holding chapter colours and blend them.
	//return result
}