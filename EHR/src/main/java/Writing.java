import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;  

public class Writing {
	/**
	 * Class that implements the writing of Microsoft DOCX files using modules from the Apache Poi library.
	 */
	public void writing(String path, String text ){
		/**
		 * Function that writes the file from input.
		 * @param path: Specifies the final path of the file the user wants to write.
		 * @param text: The actual input text as a string for the final DOCX file. 
		 */
		//creating new XWPF instance
		XWPFDocument document = new XWPFDocument();
		
	    //create a FileOutputStream instance
	    FileOutputStream out;
	    
	    //using the chosen output path for the FileOutputStream instance "out"
		try {
			out = new FileOutputStream(
			        new File(path));
			
		    //create a HWPF Paragraph from the document
		    XWPFParagraph paragraph = document.createParagraph();
		    
		    //setting up the actual file writing using the createRUn method
		    XWPFRun run = paragraph.createRun();
		    run.setText(text);
		    
		    //writing it 
		    document.write(out);
		   
		    //Close documents
		    out.close();
		    document.close();
		    
		    System.out.println("Written successfully");
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
