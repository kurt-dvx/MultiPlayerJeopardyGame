package com.comp3607.builder;

import com.comp3607.model.GameEvent;

public class GameEventBuilder {
    private GameEvent event;
    
    // Updated constructor to include activity
    public GameEventBuilder(String caseId, String activity) {
        this.event = new GameEvent(caseId, activity); // Use the GameEvent constructor that takes both parameters
    }
    
    public GameEventBuilder withPlayerId(String playerId) {
        event.setPlayerId(playerId);
        return this;
    }
    
    public GameEventBuilder withCategory(String category) {
        event.setCategory(category);
        return this;
    }
    
    public GameEventBuilder withQuestionValue(Integer value) {
        event.setQuestionValue(value);
        return this;
    }
    
    public GameEventBuilder withAnswerGiven(String answer) {
        event.setAnswerGiven(answer);
        return this;
    }
    
    public GameEventBuilder withResult(String result) {
        event.setResult(result);
        return this;
    }
    
    public GameEventBuilder withScoreAfterPlay(Integer score) {
        event.setScoreAfterPlay(score);
        return this;
    }
    
    public GameEvent build() {
        return event;
    }
}