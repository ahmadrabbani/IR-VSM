import java.io.FileNotFoundException;
import java.io.IOException;

import classes.*;

public class Main {    
    /**
     * Main method
     * @param args
     * @throws Exception 
     */
    public static void main(String args[]) throws Exception
    {    	
        DocumentParser dp = new DocumentParser();
//        
        String folderName = "dataset";
        String lang = "english";    //"english"
        String queryPath = "query";
        
        dp.parseFiles(folderName,lang);//location of your source files, only text file
        dp.buildTFIDForCorpus(); //calculates tfidf        
        dp.getCosineSimilarity(dp.parseQuery(queryPath,lang)); //calculated cosine similarity
//        
//    	String str = "Congress MP Y S  Jagan Mohan Reddy today resumed his controversial 'Odarpu yatra' in Srikakulam district of Andhra Pradesh.   Jagan arrived at Ichapuram in the district from Hyderabad by Faluknuma Express and was received by thousands of his fans and Congress workers.   The young leader unveiled a statue of late Rajasekhara Reddy on the occasion of his father YSR's birth anniversary. ";
//    	String translated = Translator.translateQuery(str);
//    	System.out.println(translated);
    }
}