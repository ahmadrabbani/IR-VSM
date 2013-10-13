package classes;

import java.util.Arrays;

public class Score {
	public static void cosineSimilarity(Double queryVectors[][], Double documentVectors[][], String[] fileNames, int fileParseCount) {
		DocumentScore docScores[] = new DocumentScore[fileParseCount];
	 	Library library;    	
    	for(int i = 0; i<queryVectors.length; i ++){
		    for (int j = 0; j < documentVectors.length; j++) {          			    	
		    	double cosineScore = CosineSimilarity.cosineSimilarity(queryVectors[i],documentVectors[j]);
		    	docScores[j] = new DocumentScore(fileNames[j],j,cosineScore);			    	
		    }             
    	}
    	library = new Library(docScores);
    	library.sortDescScore();
    	DocumentParser.documentScores = docScores;
    }
}
