package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    private int corpusSize = 0;
    int fileParseCount = 0;
    
    DocumentScore docScores[];
    String[] fileNames;
    
    public void parseFiles(String filePath, String lang) throws Exception {
        File[] allfiles = new File(filePath).listFiles();
        corpusSize = allfiles.length;
        
        lang = lang.toLowerCase();
        BufferedReader in = null;
        String[] tokenizedTerms;
        List fileNames = new ArrayList<String>();
        
        for (File f : allfiles) {
            if (f.getName().endsWith(".utf8")&&!(f.getName().contains("index"))) {            	
                tokenizedTerms = tokenize(f,lang,"d");      
                
                for (String term:tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
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
        
        docScores = new DocumentScore[fileParseCount];       
        this.fileNames = new String[fileParseCount];
        this.fileNames = (String[]) fileNames.toArray(this.fileNames);
        
    }
    
    public Double[][] parseQuery(String queryPath, String lang) throws Exception {
		File[] allfiles = new File(queryPath).listFiles();    //List all queries            
        String[] tokenizedTerms;
        Double[] tfidfQueryVector = null;
        Double[][] tfidfQueryVectors = null;        
        List<Double[]> tfidfQVectors = new ArrayList<Double[]>();
        for (File f : allfiles) {
            if (f.getName().endsWith(".utf8")) {            	                
                tokenizedTerms = tokenize(f,lang,"q"); //Builds a vector for the document by tokenizing it's words.
                queryVector = tokenizedTerms;
                tfidfQueryVector = getTFIDFVector(queryVector);
                tfidfQVectors.add(tfidfQueryVector);
            }
        }        	
		tfidfQueryVectors = (Double[][]) tfidfQVectors.toArray(new Double[tfidfQVectors.size()][vocabulary.length]);
		return tfidfQueryVectors;
	}

    public String[] tokenize(File f, String lang, String typeOfDoc) throws Exception {
    	 BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
    	 StringBuilder sb = new StringBuilder();
         String s = null;
         if(typeOfDoc =="d"){
        	 s = TagParser.parse("dataset\\"+f.getName());
         }
         else{
        	 s = TagParser.parse("query\\"+f.getName());
         }
//         while ((s = in.readLine()) != null) {
//             sb.append(s);
//         }
         sb.append(s);
    	String[] tokenizedTerms;
    	if(lang == ENGLISH){                	
//        	tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]+", "\\s").split("\\s+");   //to get individual terms
    		tokenizedTerms = sb.toString().replaceAll("[\"\'\\.,\"\':;<>\\-\n\t\\(\\)0-9\\?]+"," ").trim().split("\\s+");
        }
        else{
        	tokenizedTerms = sb.toString().replaceAll("[\"\'\\.,\"\':;<>\\-]","").split("\\s+");   //to get individual terms
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
		 	Library library;
	    	
	    	for(int i = 0; i<tfidfQueryVectors.length; i ++){
			    for (int j = 0; j < tfidfDocsVector.size(); j++) {          			    	
			    	double cosineScore = CosineSimilarity.cosineSimilarity(tfidfQueryVectors[i],tfidfDocumentVector[j]);
			    	docScores[j] = new DocumentScore(this.fileNames[j],j,cosineScore);			    	
			    }             
	    	}
	    	library = new Library(docScores);
	    	library.sortDescScore();
	    	System.out.println(Arrays.toString(library.getDocuments()));
	    }
}
        