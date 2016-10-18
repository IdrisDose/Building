package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Building;
import model.Lift;

import java.io.IOException;

/**
 * The controller for building.fxml.
 * Cleanup on 3/10/2016 at 12:55AM by Idris
 */
public class BuildingController extends Controller<Building> {
    @FXML
    private Slider delaySl;
    @FXML
    private ListView<Lift> liftsLv;
    @FXML
    private TableView<Lift> liftsTv;
    @FXML
    private Button callBtn;
    @FXML
    private Button viewBtn;
    @FXML
    private Button peopleBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Text timeTxt;

    @FXML
    private TableColumn<Lift, String> liftClm;
    @FXML
    private TableColumn<Lift, String> levelClm;
    @FXML
    private TableColumn<Lift, String> directionClm;
    @FXML
    private TableColumn<Lift, String> passengersClm;
    @FXML
    private TableColumn<Lift, String> waitingClm;

    public final Building getBuilding() {
        return model;
    }

    public final Stage getStage() {
        return stage;
    }

    //public Lift getLift(){ return liftsLv.getSelectionModel().getSelectedItem();}
    public Lift getLift() {
        return liftsTv.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void initialize() {
        getBuilding().startup();

        //Added the to prevent layout messup
        getStage().setResizable(false);

        //Remove // to enable listview
        //liftsLv.setItems(getBuilding().getLifts());

        //Table View and Columns - now bound in FXML
        //liftsTv.setItems(getBuilding().getLifts());

        //Move these to functions to clean up init;
        startLevelColumnFactory();
        populateColumns();

        //Bottom bar - Timings and Delays
        timeTxt.textProperty().bind(getBuilding().timeProperty().asString());

        //Started placing these in classes to clean up the massive amounts of listeners.
        startListeners();
    }

    @FXML
    public void handleCall(ActionEvent event) throws IOException {
        Building building = getBuilding();
        ViewLoader.showStage(building, "/view/call_lift.fxml", "Building", new Stage());
    }

    @FXML
    public void handleViewLift(ActionEvent event) throws IOException {
        //Lift lift = liftsLv.getSelectionModel().getSelectedItem();
        Lift lift = liftsTv.getSelectionModel().getSelectedItem();
        if (lift != null) {
            ViewLoader.showStage(lift, "/view/lift.fxml", lift.toString(), new Stage());
        } else
            errorMessage("Lift");
    }

    @FXML
    public void handlePeople(ActionEvent event) throws IOException {
        Building building = getBuilding();
        ViewLoader.showStage(building, "/view/people.fxml", "People", new Stage());
    }

    @FXML
    public void handleExit(ActionEvent evet) {
        getBuilding().shutdown();
        getStage().close();
    }

    /**
     * This accessor method returns the selected number on the delay slider.
     *
     * @return the the selected delay
     */
    private int getDelay() {
        return (int) delaySl.getValue();
    }

    private void errorMessage(String obj) {
        System.out.println("No " + obj + " selected.");
    }

    private void startListeners() {

        //Change building delay when slider changed.
        delaySl.valueProperty().addListener((o, oldValue, newValue) -> {
            getBuilding().setDelay(getDelay());
        });

        //Change enable button when valid lift is selected in ListView
        /* Don't need unless using ListView
        liftsLv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lift>() {
            @Override
            public void changed(ObservableValue<? extends Lift> observable, Lift oldValue, Lift newValue) {
                viewBtn.setDisable(liftsLv.getSelectionModel().getSelectedItem() == null);
            }
        });
        */

        //Change enable button when valid lift is selected in TableView
        liftsTv.getSelectionModel().selectedItemProperty().addListener(change -> {
            viewBtn.setDisable(getLift() == null);
        });

        //Close Window Listener
        getStage().setOnHiding(event -> {
            getBuilding().shutdown();
            getStage().close();
        });
    }

    private void startLevelColumnFactory() {
        levelClm.setCellFactory(column -> new TableCell<Lift, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                String s = "";

                if (item == null || empty) {
                    setText(null);
                } else {
                    if (getTableRow() != null) {
                        Lift l = (Lift) getTableRow().getItem();

                        s = l.getBottomSpace();
                        s+= l.getBottomToLevel();
                        s+= l.getTopSpace();
                    }
                    //System.out.println(item);
                    setText(s);
                    setStyle("-fx-font-family: monospace; -fx-alignment: CENTER-LEFT;");
                }
            }
        });
    }

    private void populateColumns() {
        liftClm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        directionClm.setCellValueFactory(cellData -> cellData.getValue().directionTextProperty());
        levelClm.setCellValueFactory(cellData -> cellData.getValue().levelProperty().asString());
        passengersClm.setCellValueFactory(cellData -> cellData.getValue().passengerSizeProperty().asString());
        waitingClm.setCellValueFactory(cellData -> cellData.getValue().queueSizeProptery().asString());
    }
}
