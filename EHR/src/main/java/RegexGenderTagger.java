import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexGenderTagger {
	
	/**
	 * Class that was built to compensate the inability of Stanfords german NER tool to tag German pronouns.
	 */
	
	public StringBuffer tag(String text, StringBuffer buffer, int counter) {
		
		/**
		 * Method that tags gender indicating words - german pronouns using regex.
		 * @param text: Core document in String format
		 * @param buffer: A StringBuffer instance used to store the tags in annotation file format used for BRAT
		 * @param counter: simple Integer 
		 * @return the StringBuffer filled with tags
		 */
		
		//Regex pattern for German pronouns 
		Pattern pattern = Pattern.compile("\\ber(\\b|[.!?])|\\bsein(e|er|es|s)(\\b|[.!?])|"
				+ "\\bsie(\\b|[.!?])|\\bihr(e|er|es)(\\b|[.!?])|\\bih(nen|res|n|m|r)(\\b|[.!?])");
		//Regex matcher built from pattern and the core document.
		Matcher matcher = pattern.matcher(text.toLowerCase());
		//iterating over all tags and write them to the StringBuffer that gets returned in the end.
		while (matcher.find()) {
			String current_tag = "T"+counter+"\t"+"Geschlecht"+" "+matcher.start()+" "+matcher.end()+"\t"+text.substring(matcher.start(),matcher.end());
			buffer.append(current_tag+"\n");
			counter++;
			}
		return buffer;
	}
}
