package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import classes.*;

public class DocumentParser {
	
	public static final String ENGLISH = "english";
	public static final String HINDI = "hindi";

    //This variable will hold all terms of each document in an array.
    private List termsDocsArray = new ArrayList<String[]>();
    private List allTerms = new ArrayList<String>(); //to hold all terms
    private List tfidfDocsVector = new ArrayList<Double>();
    
    private Object arrayOfTermDocumentVectors[];
    private String vocabulary[];
    private Double tfidfDocumentVector[][];
    
    private String queryVector[];
        
    public void parseFiles(String filePath, String lang) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        lang = lang.toLowerCase();
        BufferedReader in = null;
        String[] tokenizedTerms;
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
            	
                in = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
                
                tokenizedTerms = tokenize(in,lang); //Builds a vector for the document by tokenizing it's words.     
                
                for (String term:tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }                
        
        arrayOfTermDocumentVectors =  termsDocsArray.toArray(new Object[termsDocsArray.size()]);                
        vocabulary = new String [allTerms.size()];
        vocabulary = (String[]) allTerms.toArray(vocabulary);               
    }

    public String[] tokenize(BufferedReader in, String lang) throws IOException {
    	 StringBuilder sb = new StringBuilder();
         String s = null;
         while ((s = in.readLine()) != null) {
             sb.append(s);
         }
    	String[] tokenizedTerms;
    	if(lang == ENGLISH){                	
        	tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]+", "").split("\\W+");   //to get individual terms
        }
        else{
        	tokenizedTerms = sb.toString().replaceAll("[\"\'\\.,\"\':;<>\\?p\\[\\]\\(\\)\\-]","").split("\\s+");   //to get individual terms
        }      
		return tokenizedTerms;
	}

	@SuppressWarnings("unchecked")
	public void buildTFIDForCorpus() {        
        Double[] tfidfvector = new Double[allTerms.size()];
        String[] unfilteredTDStringArray;
        for (Object unfilteredTermDocumentVector:arrayOfTermDocumentVectors) {
            tfidfvector = new Double[allTerms.size()];
            unfilteredTDStringArray = (String[]) unfilteredTermDocumentVector;
            tfidfvector = getTFIDFVector(unfilteredTDStringArray);
            tfidfDocsVector.add(tfidfvector);  //storing document vectors;            
        }        
        tfidfDocumentVector = (Double[][]) tfidfDocsVector.toArray(new Double[tfidfDocsVector.size()][tfidfvector.length]);        
    }

	private Double[] getTFIDFVector(String[] unfilteredTDStringArray) {
		Double tf; //term frequency
        Double idf; //inverse document frequency
        Double tfidf; //term frequency inverse document frequency 
		Double[] tfidfvector = new Double[allTerms.size()];
		int count = 0;
		for (String term:vocabulary){
		    tf = TfIdf.tfCalculator(unfilteredTDStringArray, term);
		    idf = TfIdf.idfCalculator(arrayOfTermDocumentVectors, term);
		    tfidf = tf * idf;
		    tfidfvector[count] = tfidf;
		    count++;
		}
		return tfidfvector;
	}
    
    public void getCosineSimilarity(Double tfidfQueryVectors[][]) {
    	for(int i = 0; i<tfidfQueryVectors.length; i ++){
		    for (int j = 0; j < tfidfDocsVector.size(); j++) {            	
		    	System.out.println("between query and " + j + "  =  "+ CosineSimilarity.cosineSimilarity(tfidfQueryVectors[i],tfidfDocumentVector[j]));            		
		    }             
    	}
    }

	public Double[][] parseQuery(String queryPath, String lang) throws IOException {
		File[] allfiles = new File(queryPath).listFiles();    //List all queries    
        BufferedReader in = null;
        String[] tokenizedTerms;
        Double[] tfidfQueryVector = null;
        Double[][] tfidfQueryVectors = null;        
        List<Double[]> tfidfQVectors = new ArrayList<Double[]>();
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {            	
                in = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
                tokenizedTerms = tokenize(in,lang); //Builds a vector for the document by tokenizing it's words.
                queryVector = tokenizedTerms;
                tfidfQueryVector = getTFIDFVector(queryVector);
                tfidfQVectors.add(tfidfQueryVector);
            }
        }        	
		tfidfQueryVectors = (Double[][]) tfidfQVectors.toArray(new Double[tfidfQVectors.size()][vocabulary.length]);
		return tfidfQueryVectors;
	}
}
        