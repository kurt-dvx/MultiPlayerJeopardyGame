package com.comp3607.model;

public class CategorySelectionEvent extends GameEvent {
    private String category;
    
    public CategorySelectionEvent(String caseId, String playerId, String category) {
        super(caseId, playerId, "Select Category");
        this.category = category;
    }
    
    @Override
    public String getEventType() { return "CATEGORY_SELECTION"; }
    
    @Override public String getCategory() { return category; }
}