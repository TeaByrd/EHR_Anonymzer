import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadAnnotationFile {

    /**
     * Class to read the .ann files and return a list in different formats for different comparisons
     *
     * @throws FileNotFoundException
     */

    public static List<String> GetFullAnnotations(String pathToAnnFile) throws FileNotFoundException {
        /**
         * A function to read the .ann file, returns each full line and appends it to a list (returns the list)
         */
        File ann = new File(pathToAnnFile);
        List<String> output = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(ann))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static List<String> GetShortAnnotations(String pathToAnnFile) throws FileNotFoundException {
        /**
         * Reads the .ann file, splits each line and creates a new annotation
         * example: "Person":"Peter"
         * each shortened annotation is appended to a new list and returned as output
         */


        List<String> originalList = GetFullAnnotations(pathToAnnFile);
        List<String> finalList = new ArrayList<>(); // create new list to return

        for (String anno : originalList) {
            String[] split_anno = anno.split("\t");  // split each line
            String annoWord = split_anno[2];   // extract word
            String annoType;

            // determine the category of the annotation
            if (split_anno[1].contains("Person")) {
                annoType = "Person";
            } else if (split_anno[1].contains("Adresse")) {
                annoType = "Adresse";
            } else if (split_anno[1].contains("Organisation")) {
                annoType = "Organisation";
            } else if (split_anno[1].contains("Geschlecht")) {
                annoType = "Geschlecht";
            } else {
                annoType = "";
            }

            String result = annoType + ":" + annoWord;   // concat type and word
            finalList.add(result);  // append to final list to return
        }

        Collections.sort(finalList);
        return finalList;
    }

    public static List<String> GetWordsOnly(String pathToAnnFile) throws FileNotFoundException {
        /**
         * Reads .ann file and splits each line and extracts only the word
         * This is used for comparing the beginning .ann file list with the final output list
         * to determine new user defined annotations
         */


        List<String> originalList = GetFullAnnotations(pathToAnnFile);
        List<String> finalList = new ArrayList<>();

        for (String anno : originalList) {
            String[] split_anno = anno.split("\t");  // split each line
            String annoWord = split_anno[2];  // extract word
            finalList.add(annoWord);  // append word to list
        }
        return finalList;
    }
}
