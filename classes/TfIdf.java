package classes;

import java.util.List;
import classes.*;

public class TfIdf {
        
    public static double tfCalculator(String[] allTermsInDocument, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        double tf_normalized = 0.00;
        for (String s:allTermsInDocument) {
        	String term = (String) s;
            if (term.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
              
        //Returns length normalized tf weight
        tf_normalized = count / allTermsInDocument.length;
        return tf_normalized;
//        return 1+(Math.log(tf_normalized)/Math.log(10));
    }
    //
    
    //
    /**
     * Calculated idf of term termToCheck
     * @param arrayOfTermDocumentVectors : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public static double idfCalculator(Object[] arrayOfTermDocumentVectors, String termToCheck) {
        double count = 0.0;
        double NumberOfDocuments = 0.0;
        for (Object unfilteredTermDocumentVector : arrayOfTermDocumentVectors) {
        	String[] unfilteredTDStringArray = (String[]) unfilteredTermDocumentVector;
            for (String s : unfilteredTDStringArray) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
            NumberOfDocuments++;
        }
        //Returns log(N/df)
        return (Math.log(NumberOfDocuments / count));
    }
//
}
//