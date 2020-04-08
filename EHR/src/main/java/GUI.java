import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*

    ----------------------------------------------------------
    Written by: Thomas Lordick, Bryce Fransen, Alexander Hoch
    Brat web server IP: http://localhost:8001
    Runs only on linux or macOS
    ----------------------------------------------------------


*/


public class GUI extends Application implements EventHandler<ActionEvent> {

    Stage window;
    Scene scene;
    String textFilePath;
    String annFilePath;
    String docPath, fileName;
    String finalContent ="";
    String tempContent = "";
    String bratpath = "000000000";
    boolean bratSet = false;
    Label loading = new Label("");
    String finalLink = "";
    List<String> compareFirst;


    @Override
    public void start(Stage primaryStage) throws IOException {


        // Init primary Window
        window = primaryStage;
        window.setTitle("EHR anonymizer");
        window.setMinHeight(700);
        window.setMinWidth(1100);

       /* logo = new Image(GUI.class.getResourceAsStream("logo.png"));
        window.getIcons().add(logo);*/


        // Look for brat path
        File brat = new File("bratPath.txt");
        if (brat.exists()) {
            bratpath = readFile("bratPath.txt", Charset.defaultCharset());
            bratSet = true;
        }


        // Init sceneAnno
        BorderPane sceneStr = new BorderPane();
        scene = new Scene(sceneStr, 1000, 600);


        // SetTop
        Button newFile = new Button("Dokument öffnen");
        Button closeFile = new Button("Dokument schließen");
        Button credits = new Button("Credits");
        Button changeBrat = new Button("Brat Pfad ändern");
        Button refresh = new Button("Neu laden");
        newFile.setMinWidth(150);
        closeFile.setMinWidth(150);
        credits.setMinWidth(150);
        changeBrat.setMinWidth(150);
        refresh.setMinWidth(150);


        loading.setText("Das Dokument wird geladen. Dies kann bis zu 30 Sekunden dauern.");
        loading.setVisible(false);
        loading.setMinSize(25,25);

        HBox menuBar = new HBox(newFile,closeFile,changeBrat,refresh,credits,loading);
        menuBar.setPadding(new Insets(5,10,10,10));
        menuBar.setStyle("-fx-background-color: white");

        menuBar.setSpacing(10);
        sceneStr.setTop(menuBar);


        // SetCenter
        WebView webViewOri = new WebView();
        WebView webViewAnn = new WebView();
        HBox hBox1 = new HBox(webViewOri, webViewAnn);

        webViewOri.getEngine().loadContent("");
        webViewAnn.getEngine().loadContent("");
        webViewOri.setMinSize(500,500);
        webViewAnn.setMinSize(500,500);
        sceneStr.setCenter(hBox1);


        // SetBottom
        Button buttonOpenOptions = new Button("Anonymisieren");
        buttonOpenOptions.setMinWidth(150);
        HBox bottomHBox = new HBox(buttonOpenOptions);
        bottomHBox.setPadding(new Insets(15,15,10,15));
        bottomHBox.setStyle("-fx-background-color: white");
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        sceneStr.setBottom(bottomHBox);


        // Actions
        changeBrat.setOnAction(e-> {

            // Choose directory
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Bitte den Pfad zum Brat 'data' Ordner auswählen!");

            // bratpath as String
            bratpath = dirChooser.showDialog(window).toString();

            //Check if its the correct path by testing of last 4 character are = data
            if (!bratpath.substring(bratpath.length()-4).equals("data")) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Bevor du Dokumente auswählen kannst musst du den Pfad zum Brat 'data' Ordner setzen! ", ButtonType.OK);
                alert.showAndWait();
                bratSet = false;
            }
            else{
                // brat path is set correctly now the annotation.conf file is written
                bratSet = true;

                // A working writer function would be good
                WriteTxt txtwriter = new WriteTxt();
                txtwriter.writeTxt("# -*- Mode: Text; tab-width: 8; indent-tabs-mode: nil; coding: utf-8; -*-\n" +
                            "# vim:set ft=conf ts=2 sw=2 sts=2 autoindent:\n" +
                            "\n" +
                            "# Simple text-based definitions of entity, relation and event types\n" +
                            "# and event attributes for the BioNLP Shared Task 2011 ID task.\n" +
                            "\n" +
                            "[entities]\n" +
                            "\n" +
                            "Person\n" +
                            "Adresse\n" +
                            "Datum\n" +
                            "Geburtsdatum\n" +
                            "Besuchsdatum\n" +
                            "Diagnosedatum\n" +
                            "Organisation\n" +
                            "Geschlecht\n" +
                            "entfernen\n" +
                            "\n" +
                            "[relations]\n" +
                            "\n" +
                            "[events]\n" +
                            "\n" +
                            "[attributes]", bratpath+"\\annotation.conf");

                txtwriter.writeTxt("\n" +
                        "[drawing]\n" +
                        "\n" +
                        "Person\tbgColor:lightblue\n" +
                        "Adresse\tbgColor:lightblue\n" +
                        "Datum\tbgColor:lightblue\n" +
                        "Geburtsdatum\tbgColor:lightblue\n" +
                        "Besuchsdatum\tbgColor:lightblue\n" +
                        "Diagnosedatum\tbgColor:lightblue\n" +
                        "Organisation\tbgColor:lightblue\n" +
                        "Geschlecht\tbgColor:lightblue\n" +
                        "entfernen\tbgColor:blue\n" +
                        "\n" +
                        "SPAN_DEFAULT\tfgColor:black, bgColor:lightgreen, borderColor:darken\n" +
                        "\n" +
                        "# (no abbreviations, so empty \"labels\" section)\n" +
                        "\n" +
                        "[labels]", bratpath+"\\visual.conf");


                // Create pathfile
                try {
                    File newTextFile = new File("bratPath.txt");
                    FileWriter fw = new FileWriter(newTextFile);
                    fw.write(bratpath);
                    fw.close();

                } catch (IOException iox) {
                    iox.printStackTrace();
                }
            }

        });

        // Is called when the user wants to anonymize the text, opens option menu
        buttonOpenOptions.setOnAction(e -> {
            if (bratSet) {
                if (getFinalContent() == "") {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Öffne eine doc/docx Datei bevor du anonymisierst.", ButtonType.OK);
                    alert.showAndWait();
                }
                else OptionsMenu.display(this);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte den Brat Ornder festlegen!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Open new file
        newFile.setOnAction(e -> {

            // Select doc/docx file
            try {
                loading.setVisible(true);
                File selectedFile = chooseFile();
                docPath = selectedFile.getAbsolutePath();

                // Select last 4 chars of the path to determine file type
                String lastBit = selectedFile.getName().substring(selectedFile.getName().length()-4);
                // If selected file is not type .doc/.docx show warning
                if (lastBit.equals(".doc") || lastBit.equals("docx")) {



                    // Start with the Parsing and NER
                    GermanNER gner = new GermanNER();

                    // Path of the .ann file and .txt file
                    String target = "";

                    if (lastBit.equals(".doc")) {
                        target = ".doc";
                    } else {
                        target = ".docx";
                    }
                    window.setTitle("EHR anonymizer - "+selectedFile.getName());
                    fileName = bratpath + "\\" + selectedFile.getName().replace(target, "");
                    annFilePath = fileName + ".ann";
                    textFilePath = fileName + ".txt";

                    // Run function in all in one NER
                    gner.all_in_one_german(docPath, annFilePath, textFilePath);

                    // Call function to modify ann file
                    try {
                        StartFindingOtherAnnotations startF = new StartFindingOtherAnnotations();
                        compareFirst = startF.startFindingOtherAnnotations(annFilePath,textFilePath);
                    } catch(Exception a) {
                        System.out.println("doesnt work\n\n\n");
                    }



                    // Try to open the .txt in webViewOri and the annotated file in webViewAnn
                    try {
                        String content = readFile(textFilePath, StandardCharsets.ISO_8859_1);
                        String link = "http://localhost:8001/index.xhtml#/"+selectedFile.getName().replace(target ,"");
                        finalLink = link;
                        webViewOri.getEngine().loadContent("<html>"+content+"</html>", "text/html");
                        webViewAnn.getEngine().load(link);
                        webViewAnn.setZoom(0.7);
                        webViewAnn.getEngine().setJavaScriptEnabled(true);
                        webViewAnn.getEngine().setConfirmHandler(new Callback<String, Boolean>() {
                            @Override public Boolean call(String msg) {
                                return Boolean.TRUE;
                            }
                        });
                        finalContent = content;
                        System.out.println(finalContent);
                    } catch (IOException ex) {}
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte ein doc/docx Dokument auswählen!", ButtonType.OK);
                    alert.showAndWait();
                }
            loading.setVisible(false);
            } catch (Exception error) {loading.setVisible(false);}
        });

        // Close current file
        closeFile.setOnAction(e -> {
            webViewOri.getEngine().loadContent("");
            webViewAnn.getEngine().loadContent("");
            window.setTitle("EHR anonymizer");
        });


        // Show credits
        credits.setOnAction(e -> {
            CreditsScreen creditScreen = new CreditsScreen();
            creditScreen.display(this);
        });

        refresh.setOnAction(e -> {
            if (!(finalLink == "")) {
                webViewAnn.getEngine().load(finalLink);
            }
        });

        // Start program
        window.setScene(scene);
        window.sizeToScene();
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {

    }

    public File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(window);
    }

    //Just here to import txt files as Strings (https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file)
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public String getFinalContent() {
        return finalContent;
    }

    public String getTempContent() {
        return tempContent;
    }

    public List<String> getCompareFirst() {
        return compareFirst;
    }

    public String getAnnFilePath() {
        return annFilePath;
    }

    public void callAnonymizer(Boolean che1, int birth, int visit, int diagn) {
        /**
         * initializes an anonymizer object and runs the anonymization on the annotions in the .ann file and the text
         * @param che1: Status of the gender checkbox on the options menu
         * @param birth: Status of the birthdate option dropdown menu
         * @param visit: Status of the visiting date option dropdown menu
         * @param diagn: Status of the diagnosis date option dropdown menu
         *
         */
        // Create anonymizer object and use it to replace annotations in the text with placeholders
        Anonymizer ano = new Anonymizer();
        try {
            tempContent = ano.anonymizeData(finalContent,annFilePath,che1 ? 1 : 0,birth,visit,diagn);
            ConfirmationScreen.display(this);}
        catch (Exception error){
            callError("Während dem anonymisieren kam es zu einer\n Indexverschiebung! Bitte überprüfe das doc/docx\n Dokument auf Fehler!");
        }
    }

    public void callError(String message) {
        /**
         * displayes an error popup with the input message
         * @param message: String message that is shown
         */
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.showAndWait();
    }

}