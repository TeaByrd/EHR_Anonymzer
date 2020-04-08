import java.io.*;
import java.nio.charset.StandardCharsets;

public class WriteTxt {
	/**
	 * Class that handles the writing of simple .txt file using java.io modules.
	 */
	
	public void writeTxt (String text, String path) {
		
		/**
		 * Function that writes .txt files.
		 * @param path: Specifies the final path of the file the user wants to write.
		 * @param text: The actual input text as a string for the .txt file. 
		 */

		// converting German Umlauts and "ß"
		text = text.replace("ä","a");
		text = text.replace("Ä","A");
		text = text.replace("ö","o");
		text = text.replace("Ö","O");
		text = text.replace("ü","u");
		text = text.replace("Ü","u");
		text = text.replace("ß","s");
		 try {
			 	//Setting up a new File instance from the chosen output path and a use it to create the file writer.
	            File newTextFile = new File(path);
	            //FileWriter fw = new FileWriter(newTextFile);
	            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(newTextFile), StandardCharsets.US_ASCII);


	            
	            //write the input String to the file writer and close it
	            fw.write(text);
	            fw.close();
	            
	            System.out.println("Written successfully");
	            
	        }catch (IOException iox) {
	            iox.printStackTrace();
	            }
		}

}
