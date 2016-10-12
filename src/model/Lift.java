package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A Lift carries people to their destinations.
 */
public class Lift {
    private int number;
    private int bottom;
    private int top;
    private IntegerProperty level = new SimpleIntegerProperty();
    private IntegerProperty direction = new SimpleIntegerProperty();
    private ObservableList<Person> passengers = FXCollections.observableArrayList();
    private ObservableList<Person> queue = FXCollections.observableArrayList();
    private IntegerProperty passengersSize = new SimpleIntegerProperty();
    private IntegerProperty queueSize = new SimpleIntegerProperty();
    private StringProperty directionText = new SimpleStringProperty("--");

    public Lift(int number, int bottom, int top, int level) {
        this.number = number;
        this.bottom = bottom;
        this.top = top;
        this.level.set(level);
        this.direction.set(Direction.STATIONARY);
        startListeners();
    }


    // PROPERTIES

    public final int getNumber() {
        return number;
    }
    public final int getBottom() {
        return bottom;
    }
    public final int getTop() {
        return top;
    }
    public final int getLevel() {
        return level.get();
    }
    public final int getDirection() {
        return direction.get();
    }
    public final int getPassengerSize() {
        return passengersSize.get();
    }
    public final int getQueueSize() {
        return queueSize.get();
    }
    public final String getDirectionText() {
        return directionText.get();
    }

    public ReadOnlyStringProperty directionTextProperty() {
        return directionText;
    }
    public ReadOnlyIntegerProperty levelProperty() {
        return level;
    }
    public ReadOnlyIntegerProperty queueSizeProptery() {
        return queueSize;
    }
    public ReadOnlyIntegerProperty passengerSizeProperty() {
        return passengersSize;
    }
    public ReadOnlyIntegerProperty directionProperty() {
        return direction;
    }
    public ObservableList<Person> getPassengers() {
        return passengers;
    }
    public ObservableList<Person> getQueue() {
        return queue;
    }

    // FUNCTIONS and PROCEDURES

    public void call(Person person) {
        queue.add(person);
    }

    private void board(Person person) {
        queue.remove(person);
        passengers.add(person);
        person.board();
    }

    private void alight(Person person) {
        passengers.remove(person);
        person.alight();
    }

    /**
     * This procedure carries out the operation of a lift for one step of time.
     * It is intended to be called repeatedly.
     */
    public void operate() {
        // This is slightly different from Assignment 1
        Person nextPerson = nextPerson();
        if (direction.get() == Direction.STATIONARY) {
            if (nextPerson != null)
                direction.set(nextPerson.liftDirection(getLevel()));
        }
        if (alight() || board()) {
            if (passengers.isEmpty())
                direction.set(Direction.STATIONARY);
        } else {
            move();
            if (isAtBoundary() || passengers.isEmpty() && anyoneWaitingHere())
                direction.set(Direction.STATIONARY);
        }
    }

    private boolean anyoneWaitingHere() {
        if (queue.isEmpty())
            return false;
        Person person = queue.get(0);
        return person.isAt(level.get());
    }

    private void move() {
        level.set(level.get() + direction.get());
        for (Person person : passengers)
            person.move(direction.get());
    }

    /**
     * Determine the next person to service.
     */
    private Person nextPerson() {
        // Take the next passenger if there is one
        if (passengers.size() > 0)
            return passengers.get(0);
            // Otherwise go to pick up the next waiting if there is one
        else if (queue.size() > 0)
            return queue.get(0);
            // Otherwise there is no next person
        else
            return null;
    }

    private boolean board() {
        int count = 0;
        for (Person person : new LinkedList<Person>(queue))
            if(!person.isAboard()) //Added this to check if they are aboard, if they are skip boarding.
                if (person.canBoard(getLevel(), direction.get())) {
                    board(person);
                    count++;
                }
        return count > 0;
    }

    private boolean alight() {
        int count = 0;
        for (Person person : new ArrayList<Person>(passengers))
            if (person.hasArrived()) {
                alight(person);
                count++;
            }
        return count > 0;
    }

    private boolean isAtBoundary() {
        return getLevel() == bottom || getLevel() == top;
    }

    private int distanceTo(int target) {
        return Math.abs(target - getLevel());
    }

    public int suitability(int distance, int start, int destination) {
        if (!canTake(start, destination))
            return 0;
        else if (direction.get() * Direction.fromTo(getLevel(), start) < 0)
            return 1;
        else if (direction.get() == -Direction.fromTo(start, destination))
            return distance + 1 - distanceTo(start);
        else
            return distance + 2 - distanceTo(start);
    }

    private boolean canTake(int start, int destination) {
        return canReach(start) && canReach(destination);
    }

    private boolean canReach(int level) {
        return level >= bottom && level <= top;
    }

    private void startListeners() {
        passengers.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {

            }
        });

        queue.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                queueSize.set(queue.size());
            }
        });

        direction.addListener((o, oldValue, newValue) -> {
            int newVal = newValue.intValue();
            switch (newVal) {
                case -1:
                    directionText.set("DOWN");
                    break;
                case 0:
                    directionText.set("--");
                    break;
                case 1:
                    directionText.set("UP");
                    break;
                default:
                    directionText.set("--");
                    break;
            }
        });
    }

    @Override
    public String toString() {
        return "Lift " + number;
    }
}
