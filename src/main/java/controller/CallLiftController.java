package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Building;
import model.Person;

/**
 * Created by Idris on 1/10/2016.
 * nzdose_000 is my computer username
 */
public class CallLiftController extends Controller<Building> {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button callBtn;
    @FXML
    private ComboBox<Person> callerCb;
    @FXML
    private TextField destinationTf;
    @FXML
    private Text errorText;

    public final Building getBuilding() {
        return model;
    }

    public final Stage getStage() {
        return stage;
    }

    private Person getSelectedPerson() {
        return callerCb.getSelectionModel().isEmpty() ? null : callerCb.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void initialize() {
        //Added the to prevent layout messup
        getStage().setResizable(false);

        callerCb.setPromptText("choose");
        callerCb.getItems().addAll(getBuilding().getPeople());
        cancelBtn.onActionProperty().set(event -> getStage().close());
    }

    //Not going to move this to lambda as it's wayy to big.
    @FXML
    private void handleCall(ActionEvent event) throws Exception {
        Person person = getSelectedPerson();
        int destination = 0;
        if (!destinationTf.getText().isEmpty())
            destination = Integer.parseInt(destinationTf.getText());

        if (person != null)
            if (destination == 0)
                errorText.textProperty().set("Destination must be an integer");
            else if (!getBuilding().call(person, destination))
                errorText.textProperty().set("No suitable lift found");
            else
                getStage().close();
        else
            errorText.textProperty().set("You must select a caller");
    }

}
