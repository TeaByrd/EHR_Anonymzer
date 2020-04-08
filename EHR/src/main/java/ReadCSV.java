import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadCSV {

    /**
     * Class to read annotations from a given csv file
     * @param csvFilePath
     * @return
     * @throws IOException
     */

    public static List<String> returnAnnotationList(String csvFilePath) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFilePath), ',', '"', 0);
        // create csv reader

        String[] nextLine;
        List<String> annoList = new ArrayList();  // create new list to hold annotations


        while ((nextLine = reader.readNext()) != null) {
            // loops through annotation in csv reader and appends the value to annoList

            if (nextLine != null) {
                String value = StringUtils.join(nextLine, "");
                annoList.add(value);
                }

            }
        return annoList;
        }



}


