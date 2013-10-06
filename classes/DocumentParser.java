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
        
    public void parseFiles(String filePath, String lang) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        lang = lang.toLowerCase();
        BufferedReader in = null;
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
//                	System.out.println(sb);
                }
                String[] tokenizedTerms;
                if(lang == ENGLISH){                	
                	tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]+", "").split("\\W+");   //to get individual terms
                }
                else{
                	tokenizedTerms = sb.toString().replaceAll("[\"\'\\.,\"\':;<>\\?p\\[\\]\\(\\)\\-]","").split("\\s+");   //to get individual terms
                }              
                
                for (String term:tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
        
//        termsInDocument = new String [termsDocsArray.size()];
        arrayOfTermDocumentVectors =  termsDocsArray.toArray(new Object[termsDocsArray.size()]);
        
        
        vocabulary = new String [allTerms.size()];
        vocabulary = (String[]) allTerms.toArray(vocabulary);               
    }

    @SuppressWarnings("unchecked")
	public void tfIdfCalculator() {
        Double tf; //term frequency
        Double idf; //inverse document frequency
        Double tfidf; //term frequency inverse document frequency 
        Double[] tfidfvector = new Double[allTerms.size()];
        String[] unfilteredTDStringArray;
        for (Object unfilteredTermDocumentVector:arrayOfTermDocumentVectors) {
            tfidfvector = new Double[allTerms.size()];
            unfilteredTDStringArray = (String[]) unfilteredTermDocumentVector;
            int count = 0;
            for (String term:vocabulary){
                tf = TfIdf.tfCalculator(unfilteredTDStringArray, term);
                idf = TfIdf.idfCalculator(arrayOfTermDocumentVectors, term);
                tfidf = tf * idf;
                tfidfvector[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvector);  //storing document vectors;            
        }        
        tfidfDocumentVector = (Double[][]) tfidfDocsVector.toArray(new Double[tfidfDocsVector.size()][tfidfvector.length]);        
    }
    
    public void getCosineSimilarity() {    	
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = i+1; j < tfidfDocsVector.size(); j++) {            	
            	System.out.println("between " + i + " and " + j + "  =  "+ CosineSimilarity.cosineSimilarity(tfidfDocumentVector[i],tfidfDocumentVector[j]));            		
            }         
        }
    }
}