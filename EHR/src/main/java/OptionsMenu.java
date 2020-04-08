import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OptionsMenu {

    public static void display(GUI mainWindow) {
        /**
         * Displays the Option menu screen
         * @param mainWindow: linking to the main GUI that called the option menu window.
         */

        //General init
        Stage window = new Stage();
        BorderPane mainBorder = new BorderPane();
        Scene sceneOpt = new Scene(mainBorder, 300, 300);


        // Init Labels and Checkboxes
        Label genderLabel = new Label("Geschlecht anonymisieren");
        CheckBox genderCheck = new CheckBox("");

        // Choicebox Birthdate
        Label bdateLabel = new Label("Geburtsdatum:");
        ChoiceBox bdateBox = new ChoiceBox();
        bdateBox.getItems().add("keine Änderung");
        bdateBox.getItems().add("zu Monat");
        bdateBox.getItems().add("zu Jahr");
        bdateBox.getItems().add("zu Platzhalter");
        bdateBox.getSelectionModel().selectFirst();

        // Choicebox Visitingdate
        Label vdateLabel = new Label("Besuchsdatum:");
        ChoiceBox vdateBox = new ChoiceBox();
        vdateBox.getItems().add("keine Änderung");
        vdateBox.getItems().add("zu Monat");
        vdateBox.getItems().add("zu Jahr");
        vdateBox.getItems().add("zu Platzhalter");
        vdateBox.getSelectionModel().selectFirst();

        // Choicebox Diagnosedate
        Label ddateLabel = new Label("Diagnosedatum:");
        ChoiceBox ddateBox = new ChoiceBox();
        ddateBox.getItems().add("keine Änderung");
        ddateBox.getItems().add("zu Monat");
        ddateBox.getItems().add("zu Jahr");
        ddateBox.getItems().add("zu Platzhalter");
        ddateBox.getSelectionModel().selectFirst();

        // Additional label

        CheckBox che1 = new CheckBox("");


        //SetCenter
        TilePane labelVBox = new TilePane(genderLabel, genderCheck, bdateLabel, bdateBox, vdateLabel, vdateBox, ddateLabel, ddateBox);
        labelVBox.setPrefColumns(2);
        labelVBox.setHgap(30);
        labelVBox.setVgap(15);
        labelVBox.setStyle("-fx-background-color: white");
        labelVBox.setAlignment(Pos.CENTER);
        mainBorder.setCenter(labelVBox);


        //SetBottom
        Button run = new Button("Start");
        TilePane tilep = new TilePane(run);
        tilep.setPadding(new Insets(15,15,15,15));
        tilep.setAlignment(Pos.BOTTOM_RIGHT);
        tilep.setStyle("-fx-background-color: white");
        mainBorder.setBottom(tilep);


        // SetTop
        Label descText = new Label("Bitte Anonymisierungsoptionen auswählen:");
        HBox hbtop = new HBox(descText);
        hbtop.setPadding(new Insets(15,15,15,15));
        descText.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-background-color: white");
        hbtop.setStyle("-fx-background-color: white");
        mainBorder.setTop(hbtop);


        // Actions

        // Anonymization should run
        run.setOnAction(e -> {

            /*
             Check the states of all Checkboxes and Choiceboxes

             0 = No change
             1 = to month
             2 = to Year
             3 = to placeholder
             */

            ChoiceBox[] list = new ChoiceBox[] {bdateBox, vdateBox, ddateBox};
            List<Integer> ints = new ArrayList<>();

            for (ChoiceBox Item:list) {
                if (Item.getSelectionModel().getSelectedItem() == "keine Änderung") {
                    ints.add(0);
                }
                else if (Item.getSelectionModel().getSelectedItem() == "zu Monat") {
                    ints.add(1);
                }
                else if (Item.getSelectionModel().getSelectedItem() == "zu Jahr") {
                    ints.add(2);
                }
                else if (Item.getSelectionModel().getSelectedItem() == "zu Platzhalter") {
                    ints.add(3);
                }
            }

            // Call anonymizing function in GUI
            mainWindow.callAnonymizer(che1.isSelected(),ints.get(0), ints.get(1), ints.get(2));
            window.close();
        });


        window.setMinHeight(300);
        window.setMinWidth(400);
        window.setScene(sceneOpt);
        window.setResizable(false);
        window.showAndWait();
    }
}
