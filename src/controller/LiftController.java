package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.LevelView;
import model.Lift;
import model.Person;

/**
 * Created by Idris on 1/10/2016.
 * nzdose_000 is my computer username
 */
public class LiftController extends Controller<Lift> {
    @FXML private Text currentLevelTxt;
    @FXML private Text bottomLevelTxt;
    @FXML private Text topLevelTxt;
    @FXML private Text directionTxt;
    @FXML private Text subheading;
    @FXML private Button closeBtn;
    @FXML private ListView<Person> passengersLv;
    @FXML private ListView<Person> queueLv;
    @FXML private LevelView levelsLv;

    public final Lift getLift() {
        return model;
    }
    private final Stage getStage() {
        return stage;
    }

    @FXML
    private void initialize() {
        //Added the to prevent layout messup
        getStage().setResizable(false);
        getStage().getIcons().add(new Image("/view/building.png"));

        //Subheading for dynamic change.
        subheading.textProperty().set(getLift().toString());

        //Lift Details - Changed to new LevelView control.
        //currentLevelTxt.textProperty().bind(getLift().levelProperty().asString());
        //bottomLevelTxt.textProperty().set(""+getLift().getBottom());
        //topLevelTxt.textProperty().set(""+getLift().getTop());
        directionTxt.textProperty().bind(getLift().directionTextProperty());

        //changed this to lambda because it's a small function.
        closeBtn.onActionProperty().set(action -> getStage().close());
    }
}
