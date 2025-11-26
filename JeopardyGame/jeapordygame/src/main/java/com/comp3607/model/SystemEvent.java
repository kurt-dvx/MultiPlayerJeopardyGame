package com.comp3607.model;

public class SystemEvent extends GameEvent {
    private String result;
    private String answerGiven;
    
    public SystemEvent(String caseId, String activity) {
        super(caseId, "System", activity);
    }
    
    public SystemEvent(String caseId, String activity, String result) {
        super(caseId, "System", activity);
        this.result = result;
    }
    
    public SystemEvent(String caseId, String activity, String result, String answerGiven) {
        super(caseId, "System", activity);
        this.result = result;
        this.answerGiven = answerGiven;
    }
    
    @Override
    public String getEventType() { return "SYSTEM"; }
    
    @Override public String getResult() { return result; }
    @Override public String getAnswerGiven() { return answerGiven; }
}
