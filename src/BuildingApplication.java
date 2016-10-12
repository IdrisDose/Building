import au.edu.uts.ap.javafx.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Building;

/**
 * This class launches the building application.
 */
public class BuildingApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewLoader.showStage(new Building(), "/view/building.fxml", "Building", stage);
    }
}
