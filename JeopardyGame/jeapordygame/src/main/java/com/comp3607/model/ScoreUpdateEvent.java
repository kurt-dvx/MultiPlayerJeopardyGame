package com.comp3607.model;

public class ScoreUpdateEvent extends GameEvent {
    private Integer scoreAfterPlay;
    
    public ScoreUpdateEvent(String caseId, String playerId, int scoreAfterPlay) {
        super(caseId, playerId, "Score Updated");
        this.scoreAfterPlay = scoreAfterPlay;
    }
    
    @Override
    public String getEventType() { return "SCORE_UPDATE"; }
    
    @Override public Integer getScoreAfterPlay() { return scoreAfterPlay; }
}