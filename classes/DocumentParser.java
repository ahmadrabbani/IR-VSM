package classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classes.*;

public class DocumentParser {
	
	public static final String ENGLISH = "english";
	public static final String HINDI = "hindi";

    //This variable will hold all terms of each document in an array.
    public List termsDocsArray = new ArrayList<String[]>();
    public List allTerms = new ArrayList<String>(); //to hold all terms
    public List tfidfDocsVector = new ArrayList<Double>();
    
    public Object arrayOfTermDocumentVectors[];
    public String vocabulary[];
    public Double tfidfDocumentVector[][];
    
    public String queryVector[];
    public Double queryVectors[][];
    
    public int corpusSize = 0;
    public static int fileParseCount = 0;        
    public String[] fileNames;
    public static DocumentScore documentScores[][];
    public static int documentsScored = 0;
    public static File queryFiles[];
    public static String ext = ".txt";
    public static String tag = "content";
    public static int queryCount = 0;
    
	@SuppressWarnings("unchecked")
    public void parseFiles(String filePath, String lang) throws Exception {
        File[] allfiles = new File(filePath).listFiles();
        corpusSize = allfiles.length;
        
        lang = lang.toLowerCase();
        String[] tokenizedTerms;
        List fileNames = new ArrayList<String>();
        
        for (File f : allfiles) {
            if (f.getName().endsWith(ext)&&!(f.getName().contains("index"))) {            	
                tokenizedTerms = tokenize(f,lang,"d");      
                
                for (String term:tokenizedTerms) {
                    if (!allTerms.contains(term) && !StopWords.hindi.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
                fileNames.add(f.getName());
                System.out.println("Total documents parsed: "+(++fileParseCount));
            }
        }                        
        arrayOfTermDocumentVectors =  termsDocsArray.toArray(new Object[termsDocsArray.size()]);                
        vocabulary = new String [allTerms.size()];
        System.out.println("Building Vocabulary");
        vocabulary = (String[]) allTerms.toArray(vocabulary);
        System.out.println("Vocabulary built");
                
		String vocab="";
		for(String word:vocabulary){
			vocab += word+"\n";
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vocabulary\\vocab.txt"),"UTF-8"));
		bw.write(vocab);
		bw.close();
        
        this.fileNames = new String[fileParseCount];
        this.fileNames = (String[]) fileNames.toArray(this.fileNames);
        
    }
    
    public Double[][] parseQuery(String queryPath, String lang) throws Exception {
		File[] allfiles = new File(queryPath).listFiles();    //List all queries 		
		queryFiles = allfiles;
        String[] tokenizedTerms;
        Double[] tfidfQueryVector = null;
        Double[][] tfidfQueryVectors = null;        
        List<Double[]> tfidfQVectors = new ArrayList<Double[]>();
        for (File f : allfiles) {
            if (f.getName().endsWith(ext)) {            	                
                tokenizedTerms = tokenize(f,lang,"q"); //Builds a vector for the document by tokenizing it's words.
                queryVector = tokenizedTerms;
                tfidfQueryVector = getTFIDFVector(queryVector);
                tfidfQVectors.add(tfidfQueryVector);
            }
            System.out.println("Building query tfidf vector "+(++queryCount));
        }        	
//        documentScores = new DocumentScore[queryCount][];
		tfidfQueryVectors = (Double[][]) tfidfQVectors.toArray(new Double[tfidfQVectors.size()][vocabulary.length]);
		return tfidfQueryVectors;
	}

    public String[] tokenize(File f, String lang, String typeOfDoc) throws Exception {
        String s = null;
        s = TagParser.parse(f,tag);                 
    	String[] tokenizedTerms;
    	if(lang == ENGLISH){                	
    		tokenizedTerms = s.replaceAll("[\"\'\\.,\"\':;<>\\-\n\t\\(\\)0-9\\?]+"," ").trim().split("\\s+");
        }
        else{
        	tokenizedTerms = s.replaceAll("[\"\'\\.,\"\':;<>\\-\n\t\\(\\)0-9\\?]+"," ").trim().split("\\s+");
        }      
		return tokenizedTerms;
	}

	@SuppressWarnings("unchecked")
	public void buildTFIDForCorpus() {        
		int docVectorCount = 0;
        Double[] tfidfvector = new Double[allTerms.size()];
        String[] unfilteredTDStringArray;
        for (Object unfilteredTermDocumentVector:arrayOfTermDocumentVectors) {
            tfidfvector = new Double[allTerms.size()];
            unfilteredTDStringArray = (String[]) unfilteredTermDocumentVector;
            tfidfvector = getTFIDFVector(unfilteredTDStringArray);
            tfidfDocsVector.add(tfidfvector);  //storing document vectors;
            System.out.println("Total document tfidf vectors created: "+(++docVectorCount)+"/"+corpusSize);
        }        
        tfidfDocumentVector = (Double[][]) tfidfDocsVector.toArray(new Double[tfidfDocsVector.size()][tfidfvector.length]);        
    }

	public Double[] getTFIDFVector(String[] unfilteredTDStringArray) {
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
	
	public static void output() throws IOException{
		File runFile = new File("results\\run-tfid.txt");
		String results="";
		runFile.createNewFile();
		File queryFile;
		for(int i=0; i<queryFiles.length; i++){
			queryFile = queryFiles[i];
			for(int rank = 0; rank < Math.min(DocumentParser.fileParseCount,100); rank++){
				results += queryFile.getName()+" Q0 "+documentScores[i][rank].fileName+" "+(rank+1)+" "+documentScores[i][rank].score+"\n";												
			}
		}
		FileWriter fw = new FileWriter(runFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(results);
		bw.close();
		System.out.println(results);
	}
}
        