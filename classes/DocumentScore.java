package classes;

public class DocumentScore implements Comparable<DocumentScore> {
	public int documentID = 0;
	public double score = 0.0;
		
	public int compareTo(DocumentScore compDocScore){
		double compareQuantity = ((DocumentScore) compDocScore).score;
		return (int)((compareQuantity - this.score)*100000);		
	}
}
