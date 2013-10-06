import java.io.FileNotFoundException;
import java.io.IOException;

import classes.*;

public class Main {    
    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        DocumentParser dp = new DocumentParser();
        
        String folderName = "dataset-hindi";
        String lang = "hindi";    //"english"
        String queryPath = "query";
        
        dp.parseFiles(folderName,lang);//location of your source files, only text file
        dp.buildTFIDForCorpus(); //calculates tfidf        
        dp.getCosineSimilarity(dp.parseQuery(queryPath,lang)); //calculated cosine similarity   
    }
}