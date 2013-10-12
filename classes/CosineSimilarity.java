package classes;

public class CosineSimilarity {
   
    public static double cosineSimilarity(Double[] docVector1, Double[] docVector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
        {
        	if(docVector1[i] == 0 || docVector2[i] ==0){
        		dotProduct += 0;        		
        	}
        	else{
        		dotProduct += docVector1[i] * docVector2[i];  //a.b
        	}
        	
        	if(docVector1[i] == 0){
        		magnitude1 += 0;
        	}
        	else{
        		magnitude1 += Math.pow(docVector1[i], 2);  //(a^2)
        	}
            
        	if(docVector2[i] == 0){
        		magnitude2 += 0;
        	}
        	else{
        		magnitude2 += Math.pow(docVector2[i], 2); //(b^2)
        	}        	            
        }

        magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            return 0.0;
        }
        return cosineSimilarity;
    }
}