package dk.kauman.dtu.brint.patterns.observer;

import java.util.ArrayList;

public abstract class Subject {

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.onNotify(this);
        }
    }

    public void notifyObserver(Observer observer) {
        observer.onNotify(this);
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public ArrayList<Observer> getObservers() {
        return this.observers;
    }

}
