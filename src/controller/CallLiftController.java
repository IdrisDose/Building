package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Building;
import model.Person;

/**
 * Created by Idris on 1/10/2016.
 * nzdose_000 is my computer username
 */
public class CallLiftController extends Controller<Building> {
    @FXML private Button cancelBtn;
    @FXML private Button callBtn;
    @FXML private ComboBox<Person> callerCb;
    @FXML private TextField destinationTf;
    @FXML private Text errorText;

    public final Building getBuilding() {
        return model;
    }
    public final Stage getStage() {
        return stage;
    }

    private Person getSelectedPerson() throws Exception{
        if(!callerCb.getSelectionModel().isEmpty() )
            return callerCb.getSelectionModel().getSelectedItem();
        else
            throw new Exception ("You must select a caller.");
    }

    private Integer getDestination() throws Exception{
        if (destinationTf.getText().matches("[0-9]*") && (!destinationTf.getText().isEmpty()))
            return Integer.parseInt(destinationTf.getText());
        else
            throw new Exception("Desination must be an integer.");
    }
    @FXML
    private void initialize() {
        //Added the to prevent layout messup
        getStage().setResizable(false);
        getStage().getIcons().add(new Image("/view/building.png"));

        callerCb.setPromptText("choose");
        cancelBtn.onActionProperty().set(event -> getStage().close());
    }


    //Not going to move this to lambda as it's wayy to big.
    //UPDATE 19/10/2016 - Re-read requirements and saw that I need to pass these as errors instead.
    //Changed 0 to -1 ... because why not?
    //Made this even better .. thanks to late thinking
    @FXML
    private void handleCall(ActionEvent event) throws Exception {
        try {
            getBuilding().call(getSelectedPerson(), getDestination());
            getStage().close();
        } catch (Exception e){
            errorText.textProperty().set(e.getMessage());
        }
    }
}
