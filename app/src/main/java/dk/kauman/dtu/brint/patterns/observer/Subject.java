package dk.kauman.dtu.brint.patterns.observer;

import java.util.ArrayList;

public abstract class Subject {

    private ArrayList<Observer> observers;

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.onNotify(this);
        }
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public ArrayList<Observer> getObservers() {
        return this.observers;
    }

}
