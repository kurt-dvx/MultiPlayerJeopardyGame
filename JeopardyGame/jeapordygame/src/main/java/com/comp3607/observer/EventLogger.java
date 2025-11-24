package com.comp3607.observer;

import com.comp3607.model.GameEvent;
import com.comp3607.service.EventLogService;

public class EventLogger implements GameObserver {
    private EventLogService logService;
    
    public EventLogger(EventLogService logService) {
        this.logService = logService;
    }
    
    @Override
    public void update(GameEvent event) {
        logService.logEvent(event);
    }
}