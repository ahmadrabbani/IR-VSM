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
        dp.parseFiles("dataset-hindi","hindi");//location of your source files, only text file
//        dp.parseFiles("dataset","english");//location of your source files, only text file        
        dp.tfIdfCalculator(); //calculates tfidf
        dp.getCosineSimilarity(); //calculated cosine similarity   
    }
}