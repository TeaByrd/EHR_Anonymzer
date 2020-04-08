import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class EHR_Anonymisierer extends Application implements EventHandler<ActionEvent> {

    @Override
    public void start(Stage stage) throws Exception {
        GUI main = new GUI();
        main.start(stage);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
