package model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A person boards and alights lifts.
 */
public class Person {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty level = new SimpleIntegerProperty();
    private IntegerProperty destination = new SimpleIntegerProperty();
    private BooleanProperty aboard = new SimpleBooleanProperty();
    private StringProperty aboardText = new SimpleStringProperty("No");

    public Person(int id, String name, int level) {
        this.id.set(id);
        this.name.set(name);
        this.level.set(level);
        this.destination.set(level);

        startListeners();
    }

    // PROPERTIES

    public final int getId() {
        return id.get();
    }
    public final String getName() {
        return name.get();
    }
    public final int getLevel() {
        return level.get();
    }
    public final int getDestination() {
        return destination.get();
    }
    public final boolean isAboard() {
        return aboard.get();
    }

    public StringProperty aboardProperty() {
        return aboardText;
    }
    public ReadOnlyIntegerProperty idProperty() {
        return id;
    }
    public IntegerProperty destinationProperty() {
        return destination;
    }
    public IntegerProperty levelProperty() {
        return level;
    }
    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    // FUNCTIONS and PROCEDURES

    public void call(int destination) {
        this.destination.set(destination);
    }

    public void move(int direction) {
        level.set(level.get() + direction);
    }

    public boolean hasId(int id) {
        return this.id.get() == id;
    }

    public boolean canBoard(int liftLevel, int liftDirection) {
        return isAt(liftLevel) && isHeadingIn(liftDirection);
    }

    public boolean isAt(int level) {
        return this.level.get() == level;
    }

    public boolean isHeadingIn(int direction) {
        return direction == direction();
    }

    public int direction() {
        return Direction.fromTo(level.get(), destination.get());
    }

    public boolean isIdle() {
        return !aboard.get() && level.get() == destination.get();
    }

    public boolean isWaiting() {
        return !aboard.get() && level.get() != destination.get();
    }

    public boolean hasArrived() {
        return level.get() == destination.get();
    }

    /**
     * Determine the direction that this person wants the lift to go in.
     */
    public int liftDirection(int liftLevel) {
        return Direction.fromTo(liftLevel, level.get() == liftLevel ? destination.get() : level.get());
    }

    public void board() {
        aboard.set(true);
    }

    public void alight() {
        aboard.set(false);
    }

    @Override
    public String toString() {
        return name.get();
    }

    private void startListeners() {
        aboard.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (aboard.get())
                    aboardText.set("Yes");
                else
                    aboardText.set("No");
            }
        });
    }
}
