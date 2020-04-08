import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.StringUtils;

import java.util.Properties;

public class Pipeline {
	
	/**
	 * This class sets up all settings and prerequisites in order to use Stanford's Named Entity Recognition (NER) properly.
	 */
	
    private static StanfordCoreNLP stanfordCoreNLP;
    private static String propertiesNames = "tokenize, ssplit, pos, lemma, ner";
    private static Properties properties;

    private Pipeline() {
    }
    
    static {
    	
    	//setting up the properties of our NER
    	
       //include german settings, english is included by default:
    	properties = StringUtils.argsToProperties(
                new String[]{"-props", "StanfordCoreNLP-german.properties"});
        
    	properties = new Properties();
        properties.setProperty("annotators", propertiesNames);
        
        // disable the "fine grained NER" to keep the tags simple and the program faster.
        properties.setProperty("ner.applyFineGrained", "false");
        
        // include rule-based models in the NER process.
        properties.setProperty("ner.statisticalOnly", "false");
        
        
    }

    public static StanfordCoreNLP getPipeline() {
    	/**
    	 * This method will be called when the user wants to use NER to tag a core document. 
    	 * In t
    	 */
        if(stanfordCoreNLP == null) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}