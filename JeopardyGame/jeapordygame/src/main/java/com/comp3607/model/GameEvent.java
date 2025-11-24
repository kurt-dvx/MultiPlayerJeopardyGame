package com.comp3607.model;

import java.time.LocalDateTime;

public class GameEvent {
    private String caseId;
    private String playerId;
    private String activity;
    private LocalDateTime timestamp;
    private String category;
    private Integer questionValue;
    private String answerGiven;
    private String result;
    private Integer scoreAfterPlay;
    
    // Constructor with caseId and activity
    public GameEvent(String caseId, String activity) {
        this.caseId = caseId;
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
    }
    
    // Your existing getters and setters...
    public String getCaseId() { return caseId; }
    public String getPlayerId() { return playerId; }
    public String getActivity() { return activity; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getCategory() { return category; }
    public Integer getQuestionValue() { return questionValue; }
    public String getAnswerGiven() { return answerGiven; }
    public String getResult() { return result; }
    public Integer getScoreAfterPlay() { return scoreAfterPlay; }
    
    public void setCaseId(String caseId) { this.caseId = caseId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public void setActivity(String activity) { this.activity = activity; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setCategory(String category) { this.category = category; }
    public void setQuestionValue(Integer questionValue) { this.questionValue = questionValue; }
    public void setAnswerGiven(String answerGiven) { this.answerGiven = answerGiven; }
    public void setResult(String result) { this.result = result; }
    public void setScoreAfterPlay(Integer scoreAfterPlay) { this.scoreAfterPlay = scoreAfterPlay; }
}