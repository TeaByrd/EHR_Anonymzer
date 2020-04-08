import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Anonymizer {

    // offset to always have the right position in the text even though the length changes when replacing parts of it
    int offset = 0;

    public String anonymizeData( String input, String pathToAnnFile, Integer gender, Integer birthdate, Integer visitingdate, Integer diagnosisdate) throws Exception {
        /**
         * Function that replaces annotations given as an .ann file with selected anonymization options:
         * 1. Anonymize gender tags
         * 2. Birthdate, Visitingdate and Diagnosisdate can be: (uses anonymizeDate function for all)
         *  a. not changed
         *  b. changed to the month
         *  c. changed to the year
         *  d. exchanged with a placeholder
         * 3. Organisation to placeholder
         * 4. Address to placeholder
         * 5. Person to placeholder
         *
         * The function sorts the ann file and changes each annotation iteratively. When all annotations are changes the text is returned as a String.
         *
         * @param input: The text that is supposed to be anonymized
         * @param pathToAnnFile: absolute path to the annotation file
         * @param gender: If gender is equal to 1 gender annotations are replaced with corresponding words
         * @param birthdate: Option for anonymizing birthdates (Integer values from 0-3 display above changes)
         * @param visitingdate: Option for anonymizing visitingdates (Integer values from 0-3 display above changes)
         * @param diagnosisdate: Option for anonymizing diagnosisdates (Integer values from 0-3 display above changes)
         */
        List<String> text = readAnnFile(pathToAnnFile); // read ann file as an String list
        String output = input;

        // Sort ann file for indices because this way the offset is working
        List<String> orderedText = new ArrayList<>();
        String smallest_s = new String();
        for (int i = 0; i < text.size(); i++) {
            int smallest = 999;
            for (String Item:text) {
                String[] parts = Item.split("\\s+");
                if (Integer.parseInt(parts[2]) < smallest) {
                    smallest_s = Item;
                    smallest = Integer.parseInt(parts[2]);
                }
            }
            text.remove(smallest_s);
            orderedText.add(smallest_s);
        }
        text = orderedText;

        // Go through text and check if two annotations start at the same index if so delete one
        List<String> noDubText = new ArrayList<>();
        int prev = 0;
        for (String Item:text) {
            String[] parts = Item.split("\\s+");
            if (Integer.parseInt(parts[2]) != prev) {
                noDubText.add(Item);
                prev = Integer.parseInt(parts[2]);
            }
        }
        text = noDubText;

        // Loop over every annotation and split it in its components (index of annotation, type of annotation etc.)
        for (String Item: text) {
            String[] parts = Item.split("\\s+");
            if (parts.length > 5) {
                for (int i = 5; i < parts.length; i++) {
                    parts[4] += " "+parts[i];
                }
            }

            // parts[1] is the entity type, depending on the type of entity the anonymizer replaces it differently
            if (!output.substring(Integer.parseInt(parts[2])+offset,Integer.parseInt(parts[3])+offset).equals(parts[4])) {
                throw new Exception();

            }

            // Annotation is a "Person" -> replace the annotated text with J. Doe as placeholder
            if (parts[1].equals("Person")) {
                output = output.substring(0,Integer.parseInt(parts[2])+offset) + "J. Doe" + output.substring(Integer.parseInt(parts[3])+offset);
                offset += "J. Doe".length()-parts[4].length();

            }

            // Annotation is an "Organization" -> replace the annotated text with Organization as placeholder
            else if (parts[1].equals("Organisation")) {
                output = output.substring(0,Integer.parseInt(parts[2])+offset) + "Organisation" + output.substring(Integer.parseInt(parts[3])+offset);
                offset += "Organisation".length()-parts[4].length();

            }

            // Annotation is a "Address" -> replace with Endernichter Allee 19
            else if (parts[1].equals("Adresse")) {
                output = output.substring(0,Integer.parseInt(parts[2])+offset) + "Endenicher Allee 19" + output.substring(Integer.parseInt(parts[3])+offset);
                offset += "Endenicher Allee 19".length()-parts[4].length();
            }

            // Annotation is a Date -> further select date type
            else if (parts[1].equals("Geburtsdatum") || parts[1].equals("Besuchsdatum") || parts[1].equals("Diagnosedatum") || parts[1].equals("Datum")) {

                // If date is a Birthdate -> Call anonymize date function with birthdate annotation option
                if (parts[1].equals("Geburtsdatum")) {
                    output = anonymizeDates(output, parts, birthdate);
                }

                // If date is a Visitingdate -> Call anonymize date function with visitingdate annotation option
                else if (parts[1].equals("Besuchsdatum") || parts[1].equals("Datum")) {
                    output = anonymizeDates(output, parts, visitingdate);
                }

                // If date is a Diagnosisdate -> Call anonymize date function with diagnosis annotation option
                else if (parts[1].equals("Diagnosedatum")) {
                    output = anonymizeDates(output, parts, diagnosisdate);
                }
            }
            else if (parts[1].equals("entfernen")) {
                System.out.println(output.substring(Integer.parseInt(parts[2])+offset,Integer.parseInt(parts[3])+offset));
                output = output.substring(0,Integer.parseInt(parts[2])+offset) + output.substring(Integer.parseInt(parts[3])+offset);
                offset -= parts[4].length();
            }
            // Annotation is a Gender -> replace with gender specific placeholders
            else if (parts[1].equals("Geschlecht")) {
                if (gender == 1) {
                    if (parts[4].toLowerCase().equals("er") || parts[4].toLowerCase().equals("sie")) {
                        if (!parts[4].substring(0, 1).equals(parts[4].substring(0, 1).toUpperCase())) {
                            output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "Es" + output.substring(Integer.parseInt(parts[3]) + offset);
                        } else {
                            output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "es" + output.substring(Integer.parseInt(parts[3]) + offset);
                        }
                        offset += "es".length() - parts[4].length();
                    } else if (parts[4].toLowerCase().equals("seine") || parts[4].toLowerCase().equals("ihre")) {
                        if (!parts[4].substring(0, 1).equals(parts[4].substring(0, 1).toUpperCase())) {
                            output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "Die" + output.substring(Integer.parseInt(parts[3]) + offset);
                        } else {
                            output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "die" + output.substring(Integer.parseInt(parts[3]) + offset);
                        }
                        offset += "die".length() - parts[4].length();
                    } else if (parts[4].toLowerCase().equals("sein") || parts[4].toLowerCase().equals("ihr") || parts[4].toLowerCase().equals("seiner") || parts[4].toLowerCase().equals("ihrer")) {
                        if (!parts[4].substring(0, 1).equals(parts[4].substring(0, 1).toUpperCase())) {
                            output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "Der" + output.substring(Integer.parseInt(parts[3]) + offset);
                        } else {
                            output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "der" + output.substring(Integer.parseInt(parts[3]) + offset);
                        }
                        offset += "der".length() - parts[4].length();
                    } else if (parts[4].toLowerCase().equals("ihm") || parts[4].toLowerCase().equals("ihn")) {
                        output = output.substring(0, Integer.parseInt(parts[2]) + offset) + "der Person" + output.substring(Integer.parseInt(parts[3]) + offset);
                        offset += "der Person".length() - parts[4].length();
                    }

                }
            }
        }

        // Return final text as String
        return output;
    }

    public String anonymizeDates(String text, String[] parts, Integer option){
        /**
         * Function that anonymizes dates that are given as lines out of the annotation file with different options:
         * a. not changed if option is 0
         * b. changed to the month if option is 1
         * c. changed to the year if option is 2
         * d. exchanged with a placeholder if option is 3
         *
         * @param text: The so far annotated text, can contain already exchanged parts
         * @param parts: String list made from annotation file, includes index information, type of annotation and annotated text
         * @param option: Integer input that gives information on how to change the date
         */
        // Go through text and replace
        String output = text;
        String replacement = "";

        // No change
        if (option == 0) {
            replacement = parts[4];
        }
        // To Month
        else if (option == 1) {
            if (parts[4].substring(parts[4].length()-4).matches("\\d+") & parts[4].length() == 4){
                replacement = "00."+parts[4];
            }
            else if (parts[4].substring(parts[4].length()-4).matches("\\d+") & parts[4].length() > 4 & parts[4].substring(parts[4].length()-7).contains(".")) {
                replacement = parts[4].substring(parts[4].length()-7); // 03.10.2001 -> 00.10.2001
            }

            else if (!parts[4].matches("\\d+")) {
                if (parts[4].toLowerCase().matches("januar|februar|marz|april|mai|juni|juli|august|september|oktober|november|dezember")) {
                    replacement = parts[4];
                }
            }
        }
        // To Year
        else if (option == 2) {
            // Year with 19 or 2019
            if (parts[4].substring(parts[4].length()-4,parts[4].length()-2).contains(".")) {
                replacement = "20"+parts[4].substring(parts[4].length()-2);
            }
            else if (!parts[4].matches("\\d+")) {
                if (parts[4].toLowerCase().matches("januar|februar|marz|april|mai|juni|juli|august|september|oktober|november|dezember")) {
                    replacement = "Monat";
                }
            }
            else {
                replacement = parts[4].substring(parts[4].length()-4);
            }
        }
        // to Placeholder
        else if (option == 3) {
            replacement = "00.00.0000";
        }

        // If replacement is not equal to "" replace the annotation in the text
        if (!(replacement.equals(""))) {
            output = output.substring(0,Integer.parseInt(parts[2])+offset) + replacement + output.substring(Integer.parseInt(parts[3])+offset);
            offset += replacement.length()-parts[4].length();
        }
        return output;
    }

    public List<String> readAnnFile(String pathToAnnFile) throws IOException {
        /**
         * Reads a .ann file line by line.
         * @param pathToAnnFile: Absolute path to the .ann file containing all annotations
         */
        File ann = new File(pathToAnnFile);
        List<String> output = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(ann))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        }
        return output;
    }
}
