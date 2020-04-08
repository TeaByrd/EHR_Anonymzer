import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddAnnoToAnnFile {
    /**
     * This class takes a list of annotations (full) and the .ann file path
     * It will write each string in the list on the .ann file
     * @param newAnnoList
     * @param annFilePath
     * @throws IOException
     */
        public static void addAnnoToAnnFile(List<String> newAnnoList, String annFilePath) throws IOException {
            ReadAnnotationFile readAnnotationFile = new ReadAnnotationFile();

            // appends full annotations that are currently on the .ann file (original annotations)
            List<String> oldList = readAnnotationFile.GetFullAnnotations(annFilePath);

            Integer numberOfAnnos = oldList.size(); // finds number of original annotations
            Integer i = numberOfAnnos + 1;

            List<String> addList = new ArrayList<>();

            for(String annotations : newAnnoList) {
                String  newAnno = "T" + i.toString() + "\t" + annotations;
                i = i+1;
                addList.add(newAnno);



        }
        oldList.addAll(addList); //combine existing annotations with the annotations from csv (user input) annos
        String final_annos = String.join("\n", oldList);
        WriteTxt writeTxt = new WriteTxt();
        writeTxt.writeTxt(final_annos, annFilePath);

        }
    }

