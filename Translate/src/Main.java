import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class Main {
  public static void main(String[] args) throws Exception {
    // Set your Windows Azure Marketplace client info - See http://msdn.microsoft.com/en-us/library/hh454950.aspx
//    Translate.setClientId("AmoghsTranslatorApp");
//    Translate.setClientSecret("ty8ytm6WO5LU50vcQtcy5S8jCOT5TPRy36497rWY9UU=");
//    
//    String str = "Congress MP Y S  Jagan Mohan Reddy today resumed his controversial 'Odarpu yatra' in Srikakulam district of Andhra Pradesh.   Jagan arrived at Ichapuram in the district from Hyderabad by Faluknuma Express and was received by thousands of his fans and Congress workers.   The young leader unveiled a statue of late Rajasekhara Reddy on the occasion of his father YSR's birth anniversary. ";
//    
//    String translatedText = Translate.execute(str, Language.ENGLISH, Language.HINDI);
//
//    System.out.println(translatedText);
////    FileInputStream fis = new FileInputStream("test.txt");
////    InputStreamReader in = new InputStreamReader(fis,"UTF-8");
	  
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  String str;
	  int i=0;
	  Character c[] = new Character[100];
	  do{
		  str = (String) br.readLine();
		  c[i++] = str;
				  
	  }while(str!='q');    
	  
	  for(char each:c){
		  System.out.print(each);
	  }
  }
}