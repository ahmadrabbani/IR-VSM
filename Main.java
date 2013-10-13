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

        String folderName = "dataset";
        String lang = "english";   
//      String lang = "hindi";   
        String queryPath = "query";
        
        dp.parseFiles(folderName,lang);//location of your source files, only text file
        dp.buildTFIDForCorpus(); //calculates tfidf        
        dp.queryVectors = dp.parseQuery(queryPath, lang);
        Score.cosineSimilarity(dp.queryVectors, dp.tfidfDocumentVector, dp.fileNames, dp.fileParseCount);  
        dp.output();
    }
}