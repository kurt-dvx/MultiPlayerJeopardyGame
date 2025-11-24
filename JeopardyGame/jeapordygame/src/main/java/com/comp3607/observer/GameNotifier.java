package com.comp3607.observer;

import com.comp3607.model.GameEvent;
import java.util.ArrayList;
import java.util.List;

public class GameNotifier {
    private List<GameObserver> observers = new ArrayList<>();
    
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.update(event);
        }
    }
}