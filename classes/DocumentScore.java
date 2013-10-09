package classes;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

class DocumentScore{
	
	public String fileName; 
	public int docID;
	public double score;
        
    public DocumentScore(String fileName, int docID, double score){
    	this.fileName = fileName;
    	this.docID = docID;
    	this.score = score;
    }    

    public String toString(){
        return "(" + docID + ", " + score +","+ fileName +")\n";
    }
}

