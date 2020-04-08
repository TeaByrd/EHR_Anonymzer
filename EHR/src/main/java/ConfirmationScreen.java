import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;


public class ConfirmationScreen {
    public static void display(GUI mainWindow) {
        /**
         * Displays the conformation screen
         * @param mainWindow: linking to the main GUI that called the conformation window.
         */

        // General init
        Stage window = new Stage();
        BorderPane sceneConfStr = new BorderPane();
        Scene sceneConf = new Scene(sceneConfStr, 500, 600);


        // Init seperators
        Pane sep = new Pane();
        Pane sep2 = new Pane();
        Pane sep3 = new Pane();

        sep.setMinSize(0,0);
        sep2.setMinSize(0,0);
        sep3.setMinSize(500,15);


        // SetTop
        Label confirmLabel = new Label("Ist der Text anonymisiert?");
        VBox LabelBox = new VBox(confirmLabel);
        LabelBox.setPadding(new Insets(15,0,15,0));

        LabelBox.setStyle("-fx-background-color: white");
        confirmLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
        LabelBox.setAlignment(Pos.CENTER);
        sceneConfStr.setTop(LabelBox);


        // SetCenter
        WebView webViewConf = new WebView();
        webViewConf.getEngine().loadContent(mainWindow.getTempContent());
        sceneConfStr.setCenter(webViewConf);


        // SetBottom
        Button buttonChangeBack = new Button("NEIN");
        Button buttonConfirmAno = new Button("JA");
        buttonChangeBack.setStyle("-fx-focus-color: firebrick");
        buttonConfirmAno.setStyle("-fx-focus-color: firebrick");
        HBox HBoxButtonChange = new HBox(sep,buttonChangeBack);
        HBox HBoxButtonConfirm = new HBox(buttonConfirmAno,sep2);
        HBox HBoxButtons = new HBox(HBoxButtonChange,HBoxButtonConfirm);
        VBox VBoxButtons = new VBox(HBoxButtons,sep3);

        HBoxButtons.setStyle("-fx-background-color: #ffffff");
        VBoxButtons.setStyle("-fx-background-color: white");
        HBoxButtonChange.setStyle("-fx-background-color: white");
        HBoxButtonConfirm.setStyle("-fx-background-color: white");
        HBoxButtonChange.setAlignment(Pos.CENTER_LEFT);
        HBoxButtonConfirm.setAlignment(Pos.CENTER_RIGHT);
        HBoxButtonChange.setSpacing(20);
        HBoxButtonConfirm.setSpacing(20);
        HBoxButtonChange.setPrefWidth(250);
        HBoxButtonConfirm.setPrefWidth(250);
        sceneConfStr.setBottom(VBoxButtons);


        //Actions
        buttonConfirmAno.setOnAction(e -> {

            // Update .csvs with user added annotations
            DoneUpdateAnnotations finalUpdate = new DoneUpdateAnnotations();
            try {
                finalUpdate.doneUpdateAnnotations(mainWindow.getAnnFilePath(), mainWindow.getCompareFirst());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Save as .doc or .docx
            Writing writer = new Writing();
            String savePath = mainWindow.docPath;
            String resultPath;
            if (savePath.substring(savePath.length()-4).equals(".doc")) {
                resultPath = savePath.substring(0,savePath.length()-4) + "_anonymized.docx";
            }
            else {
                resultPath = savePath.substring(0,savePath.length()-5) + "_anonymized.docx";
            }
            writer.writing(resultPath,mainWindow.tempContent);
            window.close();
        });

        buttonChangeBack.setOnAction(e -> {
            window.close();
        });


        window.setScene(sceneConf);
        window.showAndWait();
    }
}
