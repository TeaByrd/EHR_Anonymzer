import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class GermanNER {
	
	/**
	 * Class that implements the NER workflow: Reading input core -> Annotating  ->  Writing output files.
	 */
	
	public void all_in_one_german(String input_path, String output_ann, String output_report) {
		
		/**
		 * Wrapper function that parses doc/docx files using ReadingDocDocx and implements Named Entity Recognition for German cores using Stanford's NER library.
		 * Used tags are "DATE, PERSON (including words implying the gender e.g pronouns) and "ORGANIZATION". 
		 * Those entities including the position specified by start and end indices and the core itself will be written to an annotation file (.ann) and a report file (.txt) that contains the used core.
		 * Both output files deal as input for the BRAT viewer.
		 * 
		 * @param input_path: path of the core document in doc/docx format. 
		 * @param output_ann: desired path of the annotation file (.ann).
		 * @param output_report desired path of the core document (report) (.txt).
		 */
		
		//create ReadingDocDox instance to read in the input file, either doc or docx
		ReadingDocDocx reader = new ReadingDocDocx();
		String text_docexample = reader.reading(input_path);
	
		//cut the whole header and ending part from the letter using the RegexModifier
		
		/*String doc_noheader = text_docexample.substring(text_docexample.toLowerCase().indexOf("sehr geehrte"));
		doc_noheader.trim();
		String doc_noheader_noending = doc_noheader.substring(0,doc_noheader.toLowerCase().indexOf("mit freundlichen"));
		*/
		
		RegexModifier regexmodifier = new RegexModifier();
		String modified_text = regexmodifier.modify(text_docexample);
		
		
		//import Stanford's modules
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        
        //declaring the doc file as our core document
        CoreDocument coreDocument = new CoreDocument(modified_text); 
        
        //annotating the core document
        stanfordCoreNLP.annotate(coreDocument);
        
        //create an empty string buffer in order to store our tags while iterating over them in the next lines
        StringBuffer ann_buffer = new StringBuffer("");
        
        int a = 1;
        int date_counter = 1;
        String entity = null;
        
        //iterate over all tags, edit them in the annotation file format and add to string buffer.
        for (CoreEntityMention em : coreDocument.entityMentions()) {
        	
        	if(em.entityType().equals("PERSON")) {
    
        		if (em.text().toLowerCase().matches("er|sein|seiner|ihm|ihn|sie|ihrer|ihr|es|ihnen|seines|ihres")) {
        			entity="Geschlecht";
        			}
        		else{
        			entity="Person";
        			}
        		}
        	
        	else if ((em.entityType().equals("DATE"))) {
        		if (date_counter==1) {
        			entity="Geburtsdatum";
        			date_counter++;
        			}
        		else{
        			entity="Datum";
        			}
        		}
        	
        	else if ((em.entityType().equals("ORGANIZATION"))) {
        		entity="Organisation";
        	}
         	
        	else{
        		entity=em.entityType();
        		}
        	
        	if (entity.matches("Person|Geburtsdatum|Datum|Geschlecht")) {
        		String substring = modified_text.substring(em.charOffsets().first(),em.charOffsets().second());
        		String[] arrOftags = substring.split("\n+");
        		//System.out.println(Arrays.toString(arrOftags));
        		
        		if (arrOftags.length>1) {
        	        for (String b : arrOftags) {
     
        	        	int index_start= modified_text.indexOf(b,em.charOffsets().first());
        	        	int index_end = modified_text.indexOf(b,em.charOffsets().first())+b.length();
        	        	
        	        	String current_tag = "T"+a+"\t"+entity+" "+index_start+" "+index_end+"\t"+b;
    		        	ann_buffer.append(current_tag+"\n");
    		        	a++;
        	        }
        			
        		}
        		else {
		        	String current_tag = "T"+a+"\t"+entity+" "+em.charOffsets().first()+" "+em.charOffsets().second()+"\t"+em.text();
		        	ann_buffer.append(current_tag+"\n");
		        	a++;
        			}
        		}
        	}
        
        //now run the RegexGenderTagger over the core to specifically tag german gender indicating word, e.g pronouns.
        RegexGenderTagger gender = new RegexGenderTagger();
        StringBuffer ann_buffer_gender = gender.tag(modified_text,ann_buffer,a);
        
        //convert the string buffer to a common String
    	String ann_string = ann_buffer_gender.toString(); 

    	//final file writing
		WriteTxt ann_writer = new WriteTxt();
		WriteTxt report_writer = new WriteTxt();
		ann_writer.writeTxt(ann_string, output_ann);
		report_writer.writeTxt(modified_text,output_report); 
		System.out.print("Annotation succeeded.");
	}
}