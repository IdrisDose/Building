package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 * Created by Idris on 2/10/2016.
 * nzdose_000 is my computer username
 */
public class LevelView extends HBox {
    private IntegerProperty top = new SimpleIntegerProperty();
    private IntegerProperty bottom = new SimpleIntegerProperty();
    private IntegerProperty level = new SimpleIntegerProperty();
    private ObservableList<Level> panes = FXCollections.observableArrayList();


    public LevelView() {
        super();
        getStyleClass().add("LevelView");
        setLevels(getBottom(), getTop(), getLevel());
        level.addListener(change -> {
            updateLevelView();
        });
    }

    private void updateLevelView() {
        panes.clear();
        setLevels(bottom.get(), top.get(), level.get());
        getChildren().clear();
        getChildren().addAll(panes);
    }

    private void setLevels(int bottom, int top, int level) {
        panes.clear();
        for (int i = bottom; i <= top; i++) {
            Level l = new Level("" + i, (i == level));
            panes.add(l);
        }
    }

    // GETTERS AND SETTERS

    public final int getBottom() {
        return bottom == null ? 0 : bottom.getValue();
    }
    public final int getLevel() {
        return level == null ? 0 : level.getValue();
    }
    public final int getTop() {
        return top == null ? 0 : top.getValue();
    }

    public void setBottom(int bottom) {
        this.bottom.set(bottom);
    }
    public void setTop(int top) {
        this.top.set(top);
    }
    public void setLevel(int level) {
        this.level.set(level);
    }

    public IntegerProperty levelProperty() {
        return level;
    }
    public ReadOnlyIntegerProperty bottomProperty() {
        return bottom;
    }
    public ReadOnlyIntegerProperty topProperty() {
        return top;
    }

    //Added this because it's only used by this class and no others.
    private class Level extends StackPane {
        private StringProperty text = new SimpleStringProperty("0");
        private BooleanProperty thisLevel = new SimpleBooleanProperty();


        public Level(String text, boolean currLevel) {
            Text txt = new Text(text);
            Rect rect = new Rect();
            setThisLevel(currLevel);
            if (isThisLevel())
                rect.setStyle("-fx-fill: #ff5722;");

            txt.setStyle("-fx-font-size: 28px;\n -fx-fill: black;\n -fx-padding:10px;\n -fx-font-weight: bold;");

            this.text.addListener(change -> {
                setThisLevel(currLevel);
                //System.out.println("Level: " + isThisLevel());
                if (isThisLevel())
                    rect.setStyle("-fx-fill: #ff5722;");

                txt.setStyle("-fx-font-size: 28px;\n -fx-fill: #212121;\n -fx-padding:10px;\n -fx-font-weight: bold;");
                getChildren().addAll(rect, txt);
            });
            getChildren().addAll(rect, txt);
        }


        public final String getText() {
            return text.get();
        }

        public final void setText(String text) {
            this.text.set(text);
        }

        public ReadOnlyStringProperty textProperty() {
            return text;
        }

        public final boolean isThisLevel() {
            return thisLevel.get();
        }

        public void setThisLevel(boolean thisLevel) {
            this.thisLevel.set(thisLevel);
        }

        public ReadOnlyBooleanProperty thisLevelProperty() {
            return thisLevel;
        }

        //Custom Rectangle class to place in the background.
        private class Rect extends Rectangle {
            public Rect() {
                setWidth(50);
                setHeight(50);
                setStyle("-fx-fill: white;");
            }
        }

    }

}
