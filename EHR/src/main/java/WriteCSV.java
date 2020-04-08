import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteCSV {

    /**
     * This class takes in a list of new annotations that need to be added to the csv file for future reference
     * @param newAnnoList
     * @throws IOException
     */

    public static void appendToCSV(List<String> newAnnoList) throws IOException {
        /**
         * This function deconstructs the new annotation list and splits it into the corresponding topics
         * these lists are then appended to the corresponding csv file for future reference
         */


        List<String> persons = new ArrayList<>();
        List<String> adresse = new ArrayList<>();
        List<String> organisation  = new ArrayList<>();
        List<String> geschlecht  = new ArrayList<>();

        for(String anno : newAnnoList){
            String[] split_anno = anno.split(":");
            if (anno.contains("Adresse")) {
                adresse.add(split_anno[1]);
            }
            else if (anno.contains("Person")) {
                persons.add(split_anno[1]);
            }
            else if (anno.contains("Organisation")) {
                organisation.add(split_anno[1]);
            }
            else if (anno.contains("Geschlecht")) {
                geschlecht.add(split_anno[1]);
            }

        }


        CSVWriter personsWriter = new CSVWriter(new FileWriter("csv_annotations\\Person.csv", true));
        CSVWriter adresseWriter = new CSVWriter(new FileWriter("csv_annotations\\Adresse.csv", true));
        CSVWriter organisationWriter = new CSVWriter(new FileWriter("csv_annotations\\Organisation.csv", true));
        CSVWriter geschlechtWriter = new CSVWriter(new FileWriter("csv_annotations\\Geschlecht.csv", true));

        // csv path needs to be a parameter

        for (String t : persons){
            personsWriter.writeNext(t);
        }
        personsWriter.close();

        for (String t : organisation){
            organisationWriter.writeNext(t);
        }
        organisationWriter.close();

        for (String t : adresse){
            adresseWriter.writeNext(t);
        }
        adresseWriter.close();

        for (String t : geschlecht){
            geschlechtWriter.writeNext(t);
        }
        geschlechtWriter.close();
    }


}
