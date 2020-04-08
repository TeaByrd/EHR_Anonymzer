import java.io.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class ReadingDocDocx {
	/**
	 * Class that implements the reading of Microsoft Word files using several Apache Poi modules.
	 */
	
	public String reading(String path) {	
		
		/**	 
		 * Function that either reads DOC or DOCX documents and return them as a formatted string. 
		 * We use Apache Poi's XWPF moudle to read DOCX and HWPF to read DOC files. 
		 * @param path: Specifies the path of the word file you want to read.
		 * @return the word file as String or Null in case an error throws up.
		 */
		
		// in case it's DOC: 
		 if(path.endsWith("doc")) {
			 
			 	File file = null;
		        WordExtractor extractor = null;
		        try
		        {
		            file = new File(path);
		            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		            StringBuffer doc_buffer = new StringBuffer("");
		            HWPFDocument document = new HWPFDocument(fis);
		            extractor = new WordExtractor(document);
		            String[] fileData = extractor.getParagraphText();
		            for (int i = 0; i < fileData.length; i++)
		            {
		                if (fileData[i] != null)
		                	doc_buffer.append(fileData[i]);
		            }
		            String doc_string = doc_buffer.toString(); 
		            return doc_string;
		        }
		        catch (Exception exep)
		        {
		            exep.printStackTrace();
		        }
		        return null;
		 }
			 

		 //in case it's DOCX
		 else if (path.endsWith("docx")) {
			 
			 try {
				   
				   FileInputStream file = new FileInputStream(path);
				  
				   XWPFDocument docx = new XWPFDocument(OPCPackage.open(file));
				   
				   XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
				   
				   String text = extractor.getText();
				   
				   extractor.close();
				   
				   return text;
				   
				} catch(Exception ex) {
				    ex.printStackTrace();
			}
			 return null;	 
		 }
		 else {
			 String error = "Error in path or file name invalide.";
			 return error;
		 }
	}
}
	
	
