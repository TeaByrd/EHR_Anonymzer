import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FindIndexes {
        public static List<String> newAnnoIndexList(List<String> newAnnoList, String txtFilePath, String annoType) throws IOException {
        // searches through text file to find indexes of targeted words in the csv

        ReadTxt readTxt = new ReadTxt();

        String text = readTxt.readFile(txtFilePath, Charset.defaultCharset());  // read .txt file

        List<String> resultList = new ArrayList();   // create list to store new strings

        for(String anno : newAnnoList) {
            Integer anno_length = anno.length();
            List<Integer> indexes = findWord(text, anno);

            for(Integer index : indexes){
                Integer end_index = index + anno_length;
                String str_start_index = index.toString();
                String str_end_index = end_index.toString();
                String result = annoType + " " + str_start_index + " " + str_end_index + "\t" + anno;
                resultList.add(result);

            }



        }

        return resultList;
    }

    public static List<Integer> findWord(String textString, String word) {
        List<Integer> indexes = new ArrayList<Integer>();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        int index = 0;
        while(index != -1){
            index = lowerCaseTextString.indexOf(lowerCaseWord, index);
            if (index != -1) {
                indexes.add(index);
                index++;
            }
        }
        return indexes;
    }


}
