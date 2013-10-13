package classes;

import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TagParser{
	public static String parse(File input,String tag) throws Exception {	    
		String parsedText = "";		
		Document doc = Jsoup.parse(input, "UTF-8");
		parsedText = doc.select(tag).text();
		return parsedText;
	}
}