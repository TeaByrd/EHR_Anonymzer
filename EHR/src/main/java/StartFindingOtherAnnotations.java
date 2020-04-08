import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartFindingOtherAnnotations {

    /**
     * This class runs after NER and before the user can edit annotations
     * Reads all CSVs containing additional (user defined) annotation from "brat"
     * If CSV and document have the same annotation, the word is added into the .ann file with its index and proper label
     * 
     *
     * @param annoFilePath
     * @param txtFilePath
     * @return
     * @throws IOException
     */

    public static List<String> startFindingOtherAnnotations(String annoFilePath, String txtFilePath) throws IOException {
        // create new objects of classe
        ReadCSV readCSV = new ReadCSV();
        Tokenize tokenize = new Tokenize();
        ReadAnnotationFile readAnnotationFile = new ReadAnnotationFile();
        CompareAnnoLists compareAnnoLists = new CompareAnnoLists();
        FindIndexes findIndexes = new FindIndexes();
        AddAnnoToAnnFile addAnnoToAnnFile = new AddAnnoToAnnFile();

        List<String> tokenList_OG = tokenize.getTokenList(txtFilePath);
        // get list of all words in the document (.txt file)

        // append all annotations from all CSV files to corresponding lists

        List<String> personAnnoList_OG = readCSV.returnAnnotationList("csv_annotations\\Person.csv");
        List<String> personAnnos = compareAnnoLists.findSame(personAnnoList_OG, tokenList_OG); // compare the csv list with tokenized list of all words in the .txt file
        List<String> personIndx = findIndexes.newAnnoIndexList(personAnnos, txtFilePath, "Person");
        // create new annotation list from words from csv list found in the .txt file (annotations with indexes)


        List<String> adresseAnnoList_OG = readCSV.returnAnnotationList("csv_annotations\\Adresse.csv");
        List<String> adresseAnnos = compareAnnoLists.findSame(adresseAnnoList_OG, tokenList_OG);
        List<String> adresseIndx = findIndexes.newAnnoIndexList(adresseAnnos, txtFilePath, "Adresse");

        List<String> organisationAnnoList_OG = readCSV.returnAnnotationList("csv_annotations\\Organisation.csv");
        List<String> organisationAnnos = compareAnnoLists.findSame(organisationAnnoList_OG, tokenList_OG);
        List<String> organisationIndx = findIndexes.newAnnoIndexList(organisationAnnos, txtFilePath, "Organisation");

        List<String> geschlechtAnnoList_OG = readCSV.returnAnnotationList("csv_annotations\\Geschlecht.csv");
        List<String> geschlechtAnnos = compareAnnoLists.findSame(geschlechtAnnoList_OG, tokenList_OG);
        List<String> geschlechtIndx = findIndexes.newAnnoIndexList(geschlechtAnnos, txtFilePath, "Geschlecht");


        // store all new annotations found in all the CSVs
        List<String> newAnnoList = new ArrayList<>(personIndx);
        newAnnoList.addAll(adresseIndx);
        newAnnoList.addAll(organisationIndx);
        newAnnoList.addAll(geschlechtIndx);





        addAnnoToAnnFile.addAnnoToAnnFile(newAnnoList, annoFilePath);
        // add the new annotations to the .ann file

        List<String> finalFirstList = readAnnotationFile.GetShortAnnotations(annoFilePath);
        // save a list of all the annotations to compare later after the user edits the annotations

        return finalFirstList;
        // return the final first list of annotations for comparing the final second list of annotations
        // after user edits the annotations
    }
}
