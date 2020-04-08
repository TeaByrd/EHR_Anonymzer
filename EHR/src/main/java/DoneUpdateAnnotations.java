import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class DoneUpdateAnnotations {

    public static void doneUpdateAnnotations(String annoFilePath, List<String> annoList_OG) throws IOException {
        ReadAnnotationFile readAnnotationFile = new ReadAnnotationFile();
        CompareAnnoLists compareAnnoLists = new CompareAnnoLists();
        WriteCSV writeCSV = new WriteCSV();


        List<String> finalAnnoList = readAnnotationFile.GetShortAnnotations(annoFilePath);
        // read the final .ann file into a list for comparing to the original .ann file list
        // to determine new user defined annotations to store in the CSVs for later reference

        List<String> annosNeedUploaded = compareAnnoLists.findDiff(annoList_OG, finalAnnoList);
        // compares the original list of annotations with the final list from above
        // determines which annotations are in the final list that was not in the original list


        writeCSV.appendToCSV(annosNeedUploaded);
        // appends the new annotations to the corresponding CSV file


    }
}
