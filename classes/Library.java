package classes;
import java.util.Arrays;
import java.util.Comparator;

public class Library{

    // These variables are static because you don't need multiple copies
    // for sorting, as they have no intrinsic state.    
    static private Comparator<DocumentScore> descScore;

    // We initialize static variables inside a static block.
    static {               
        descScore = new Comparator<DocumentScore>(){
            @Override
            public int compare(DocumentScore b1, DocumentScore b2){
                return (int)((b2.score - b1.score)*100000000);
            }
        };
    }

    private DocumentScore[] documentScores;
    
    public DocumentScore[] getDocuments(){
    	return documentScores; 
    }
    
    public void sortDescScore(){
        Arrays.sort(documentScores, descScore);
    }

    public Library(DocumentScore[] documentScores){
        this.documentScores = documentScores;
    }   
}