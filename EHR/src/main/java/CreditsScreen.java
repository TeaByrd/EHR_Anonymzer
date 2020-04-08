import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class CreditsScreen {
    public static void display(GUI mainWindow) {
        /**
         * Displays the credits screen
         * @param mainWindow: linking to the main GUI that called the Credits window.
         */

        // General init
        Stage window = new Stage();
        GridPane CreditsPane = new GridPane();
        Scene sceneCredits = new Scene(CreditsPane, 420, 350);

        Label author1 = new Label("Thomas Lordick");
        Label author2 = new Label("Bryce Fransen");
        Label author3 = new Label("Alexander Hoch");
        author1.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black");
        author2.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black");
        author3.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black");

        Hyperlink linkBrat = new Hyperlink("Brat annotation software v1.3");
        String linkBratString = "https://brat.nlplab.org/";

        Hyperlink linkNLP = new Hyperlink("Stanford NLP v3.9.2");
        String linkNLPString = "https://stanfordnlp.github.io/CoreNLP/index.html";

        Hyperlink linkStack = new Hyperlink("Stackoverflow");
        String linkStackString = "https://stackoverflow.com/";


        Label authors = new Label("Autoren:");
        authors.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black");
        Label tools = new Label("Softwarepakete in Benutzung:");
        tools.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black");
        Label questions = new Label("Fragen:");
        questions.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black");

        Hyperlink linkQuestions = new Hyperlink("Restliche Fragen");
        String linkQuestionsString = "www.google.com";

        CreditsPane.add(authors, 0,0);
        CreditsPane.add(author1, 1,1);
        CreditsPane.add(author2, 1,2);
        CreditsPane.add(author3, 1,3);

        CreditsPane.add(tools, 0, 5);
        CreditsPane.add(linkBrat, 1,6);
        CreditsPane.add(linkNLP, 1,7);
        CreditsPane.add(linkStack, 1, 9);

        CreditsPane.add(questions, 0,9);
        CreditsPane.add(linkQuestions,1,10);

        CreditsPane.setHgap(10);
        CreditsPane.setVgap(10);
        CreditsPane.setPadding(new Insets(10, 10, 10, 10));


        linkBrat.setOnAction(e -> {
            HostServices services = mainWindow.getHostServices();
            services.showDocument(linkBratString);
        });

        linkNLP.setOnAction(e -> {
            HostServices services = mainWindow.getHostServices();
            services.showDocument(linkNLPString);
        });

        linkStack.setOnAction(e -> {
            HostServices services = mainWindow.getHostServices();
            services.showDocument(linkStackString);
        });

        linkQuestions.setOnAction(e -> {
            HostServices services = mainWindow.getHostServices();
            services.showDocument(linkQuestionsString);
        });

        CreditsPane.setStyle("-fx-background-color: white");
        window.setScene(sceneCredits);
        window.setTitle("Credits");
        window.showAndWait();
    }
}