package com.comp3607.observer;

import com.comp3607.model.GameEvent;

public interface GameObserver {
    void update(GameEvent event);
}
