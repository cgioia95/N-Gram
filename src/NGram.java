import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NGram {
	
	public static List<String> ngrams(int n, String str) {
	    ArrayList<String> ngrams = new ArrayList<String>();
	    
	    for (int i = 0; i < str.length() - n + 1; i++) {
	        ngrams.add(str.substring(i, i + n));}
	    
	    return ngrams;
	}
	
	 public static <T> List<T> intersection(List<T> list1, List<T> list2) {
	        List<T> list = new ArrayList<T>();

	        for (T t : list1) {
	            if(list2.contains(t)) {
	                list.add(t);
	            }
	        }

	        return list;
	    }
	
	 public static int nGramDist(String s1, String s2, int n) {
		 
		 List<String> words1 = ngrams(n, s1);
		 int magn1 = words1.size();
		 List<String> words2 = ngrams(n, s2);
		 int magn2 = words2.size();

		 List<String> intersection = intersection(words1, words2);
		 int magnI = intersection.size();
		 int nGramDistance = magn1 + magn2 -2*(magnI);
		 
		 return nGramDistance;
		 
	 }
	 
	 
	public static void main(String[] args) throws IOException {
		
		int correctMatches = 0;
		int results = 0;
		
		int THRESHOLD = 2;
	
		FileWriter writer = new FileWriter("C:\\Users\\Marina Office\\Google Drive\\UNI\\Knowledge Technologies\\"
				+ "Project 1\\2019S1-proj1-data\\nGram Distance\\NGRam Two.csv");
		
		writer.append("Mispelt,Predicted,CorrectMatches,Correct Word");
		writer.append("\n");
		
		// Constructed an array list of our dictionary words
		Dictionary dict = new Dictionary("C:\\Users\\Marina Office\\Google Drive\\UNI\\Knowledge Technologies\\"
				+ "Project 1\\2019S1-proj1-data\\dict.txt");
			
		Dictionary misspell = new Dictionary("C:\\Users\\Marina Office\\Google Drive\\UNI\\Knowledge "
				+ "Technologies\\Project 1\\2019S1-proj1-data\\misspell.txt");

		Dictionary correct = new Dictionary("C:\\Users\\Marina Office\\Google Drive\\UNI\\Knowledge"
				+ " Technologies\\Project 1\\2019S1-proj1-data\\correct.txt");
				
//		Cycle through all the mispelt words
		for (int i=0; i < misspell.words.size(); i++) {
			
			int localCorrect = 0;

			// Declare the mispelt word and its associated correct word, aswell as an arraylist for this word of the closest words to it in the dictionary 
			String mispeltWord = misspell.words.get(i);
			
		
			writer.append(mispeltWord + ",");
			
			String correctWord = correct.words.get(i);
			
		
			ArrayList<WWS> candidates = new ArrayList<WWS>(); // or you can use an ArrayList
			
			// Cycle through the dictionary, each cycle declare the dictionary word inspected
			// if minEditDistance bwetween our mispelt word and this dictWord is < the threshold,
			// then add it to our list of close word 
			
			for (int j=0; j < dict.words.size(); j++) {
				
				String dictWord = dict.words.get(j);
				
				if (nGramDist(mispeltWord, dictWord,1) <= THRESHOLD){
					
					WWS wws = new WWS(dictWord, nGramDist(mispeltWord, dictWord,2));
					
					candidates.add(wws);
					
				}
				
			}
			
if(candidates.size()>0) {
				
				Collections.sort(candidates);
				
				writer.append("|||");
				
				int z =0;
				for (WWS wws: candidates) {
					
					z++;
					
					if (wws.word.equals(correctWord)) {
						
						correctMatches++;
						localCorrect++;
					}
					
					writer.append(wws.word +"|||");
					
					
					if (z == 3) {
						break;
					}
				}
				
				results = results + z;
				
				writer.append(",");
				
//				finalAnswer = candidates.get(0).word;
//				writer.append(finalAnswer +",");

			}
			
	else {
	
	results++;
	
	if (mispeltWord.contentEquals(correctWord)) {
		
		correctMatches++;
		localCorrect++;
}
	
	writer.append(mispeltWord + ",");
	
	
}
			
System.out.println(i+1);


writer.append(localCorrect + ",");

writer.append(correctWord + "\n");

				
			}
		System.out.println("Total Results:" +  results + "  Total Correct Matches: " + correctMatches + " out of " + misspell.words.size());
		
		writer.append("\n \n");
		writer.append("FINAL RESULTS: Correct Matches: " + correctMatches + " Total Results: " + results + "\n");
		writer.append("PRECISION: " + correctMatches + " / " + results + "\n");
		writer.append("RECALL: " + correctMatches + " / " + misspell.words.size());

		writer.close();


	}

}
