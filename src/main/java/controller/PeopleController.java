package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Building;
import model.Person;

/**
 * Created by Idris on 1/10/2016.
 * nzdose_000 is my computer username
 */
public class PeopleController extends Controller<Building> {
    @FXML
    private Button closeBtn;
    @FXML
    private TableView<Person> peopleTv;
    @FXML
    private Text subheading;
    @FXML
    private TableColumn<Person, String> idClm;
    @FXML
    private TableColumn<Person, String> nameClm;
    @FXML
    private TableColumn<Person, String> levelClm;
    @FXML
    private TableColumn<Person, String> destinationClm;
    @FXML
    private TableColumn<Person, String> aboardClm;

    public final Building getBuilding() {
        return model;
    }

    public ObservableList<Person> getPeople() {
        return getBuilding().getPeople();
    }

    private Stage getStage() {
        return stage;
    }

    @FXML
    private void initialize() {
        //Added the to prevent layout messup
        getStage().setResizable(false);
        subheading.setText("People");


        idClm.setCellValueFactory(cellData ->
                cellData.getValue().idProperty().asString());
        nameClm.setCellValueFactory(cellData ->
                cellData.getValue().nameProperty());
        levelClm.setCellValueFactory(cellData ->
                Bindings.concat("Level ", cellData.getValue().levelProperty().asString()));
        destinationClm.setCellValueFactory(cellData ->
                Bindings.concat("Level ", cellData.getValue().destinationProperty().asString()));
        aboardClm.setCellValueFactory(cellData ->
                cellData.getValue().aboardProperty());

        closeBtn.onActionProperty().set(event -> getStage().close());
    }
}
