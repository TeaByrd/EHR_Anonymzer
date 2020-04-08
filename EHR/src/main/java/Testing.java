import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testing {


	public static void main(String[] args) {
		
		ReadingDocDocx reader = new ReadingDocDocx();
		String text_docexample = reader.reading("/Users/funkyt/Desktop/LSI/Sem 3/PLab 2/exercises/group-06-perelman/Letters_german/_X8.doc");
		String doc_noheader = text_docexample.substring(text_docexample.toLowerCase().indexOf("sehr geehrte"));
		doc_noheader.trim();

		// regex for letter cutting
		String pattern_end = "mit freundlichen"
				+ "|mit kollegialen|viele grüße|(liebe|beste|freundliche) grüße|"
				+ "mit besten empfehlungen|mfg";
		String pattern_start = "sehr geehrte|liebe kolleg(en|innen)";
		Pattern pattern_1 = Pattern.compile(pattern_start);
		Pattern pattern_2 = Pattern.compile(pattern_end);
		Matcher matcher_1 = pattern_1.matcher(text_docexample.toLowerCase());
		Matcher matcher_2 = pattern_2.matcher(text_docexample.toLowerCase());
		
		int index_1 = 0;
		int index_2= text_docexample.length()-1;
		
		if (matcher_1.find()) {
		    index_1 = matcher_1.start();
		}        
		else {
			System.out.print("Attention! Letter form of address not appropriate. Can lead to errors.");
			}
		
		if (matcher_2.find()) {
		    index_2 = matcher_2.start();
		}        
		else {
			System.out.print("Attention! Letter ending not appropriate. Can lead to errors.");
			}

		String relevant_letter = text_docexample.substring(index_1,index_2);
		relevant_letter.trim();
		System.out.print(relevant_letter + "MfG");
	}
	
}
