package classes;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class Translator {
  public static String translateQuery(String str) throws Exception {
    //Set your Windows Azure Marketplace client info - See http://msdn.microsoft.com/en-us/library/hh454950.aspx
    Translate.setClientId("AmoghsTranslatorApp");
    Translate.setClientSecret("ty8ytm6WO5LU50vcQtcy5S8jCOT5TPRy36497rWY9UU=");
           
    String translatedText = Translate.execute(str, Language.ENGLISH, Language.HINDI);

    return translatedText;
  }
}
