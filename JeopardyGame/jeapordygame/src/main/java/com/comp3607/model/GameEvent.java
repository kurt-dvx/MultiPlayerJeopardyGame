package com.comp3607.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameEvent {
    private String caseId;
    private String playerId;
    private String activity;
    private LocalDateTime timestamp;
    private String category;
    private String questionValue;
    private String answerGiven;
    private String result;
    private String scoreAfterPlay;
    
    public GameEvent(String caseId, String playerId, String activity, 
                    String category, String questionValue, String answerGiven, 
                    String result, String scoreAfterPlay) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = result;
        this.scoreAfterPlay = scoreAfterPlay;
    }
    
    // Getters
    public String getCaseId() { return caseId; }
    public String getPlayerId() { return playerId; }
    public String getActivity() { return activity; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getCategory() { return category; }
    public String getQuestionValue() { return questionValue; }
    public String getAnswerGiven() { return answerGiven; }
    public String getResult() { return result; }
    public String getScoreAfterPlay() { return scoreAfterPlay; }
    
    public String toCSV() {
        return String.join(",",
            caseId,
            playerId,
            activity,
            timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            category,
            questionValue,
            answerGiven,
            result,
            scoreAfterPlay
        );
    }
}