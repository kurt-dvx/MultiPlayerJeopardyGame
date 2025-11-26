package com.comp3607.model;

public class QuestionSelectionEvent extends GameEvent {
    private String category;
    private Integer questionValue;
    
    public QuestionSelectionEvent(String caseId, String playerId, String category, int questionValue) {
        super(caseId, playerId, "Select Question");
        this.category = category;
        this.questionValue = questionValue;
    }
    
    @Override
    public String getEventType() { return "QUESTION_SELECTION"; }
    
    @Override public String getCategory() { return category; }
    @Override public Integer getQuestionValue() { return questionValue; }
}