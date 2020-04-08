import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexModifier {
	/**
	 * Class that implements modifies ordinary German letters by specific key words and trims white spaces.
	 */
	
	public String modify(String text) {
		/**
		 * Method to modify german letters. It specifically cuts out the letters head and the end part, in case it's
		 * formally written. Writes the modified letter in same format.
		 * @param text: the input letter in String format.
		 * @return modified letter in String format
		 */
		
		String pattern_end = "mit freundlichen"
				+ "|mit kollegialen|viele grüße|(liebe|beste|freundliche) grüße|"
				+ "mit besten empfehlungen|mfg";
		String pattern_start = "sehr geehrte|liebe kolleg(en|innen)";
		
		Pattern pattern_1 = Pattern.compile(pattern_start);
		Pattern pattern_2 = Pattern.compile(pattern_end);
		Matcher matcher_1 = pattern_1.matcher(text.toLowerCase());
		Matcher matcher_2 = pattern_2.matcher(text.toLowerCase());
		
		int index_1 = 0;
		int index_2= text.length()-1;
		
		if (matcher_1.find()) {
		    index_1 = matcher_1.start();
			}        
		else {
			System.out.print("Attention! Letter form of address might be unappropriate. Head will be still included.\n");
			}
		
		if (matcher_2.find()) {
		    index_2 = matcher_2.start();
			}        
		else {
			System.out.print("Attention! Letter ending might be unappropriate. Ending will still be included. \n");
			}
		
		String modified_text = text.substring(index_1,index_2);
		modified_text.trim();
		return modified_text+"MfG";
	}
}
