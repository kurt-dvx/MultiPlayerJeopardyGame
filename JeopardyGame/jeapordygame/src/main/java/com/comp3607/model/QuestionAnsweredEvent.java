package com.comp3607.model;

public class QuestionAnsweredEvent extends GameEvent {
    private String category;
    private Integer questionValue;
    private String answerGiven;
    private String result;
    private Integer scoreAfterPlay;
    
    public QuestionAnsweredEvent(String caseId, String playerId, String category, 
                               int questionValue, String answerGiven, 
                               boolean isCorrect, int scoreAfterPlay) {
        super(caseId, playerId, "Answer Question");
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = isCorrect ? "Correct" : "Incorrect";
        this.scoreAfterPlay = scoreAfterPlay;
    }
    
    @Override
    public String getEventType() { return "QUESTION_ANSWERED"; }
    
    // Override getters to return actual values
    @Override public String getCategory() { return category; }
    @Override public Integer getQuestionValue() { return questionValue; }
    @Override public String getAnswerGiven() { return answerGiven; }
    @Override public String getResult() { return result; }
    @Override public Integer getScoreAfterPlay() { return scoreAfterPlay; }
}