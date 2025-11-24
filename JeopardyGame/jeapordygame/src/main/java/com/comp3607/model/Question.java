package com.comp3607.model;

public class Question {
    private String category;
    private int value;
    private String questionText;
    private String answer;
    private boolean used;
    
    // Default constructor
    public Question() {}
    
    // Parameterized constructor
    public Question(String category, int value, String questionText, String answer) {
        this.category = category;
        this.value = value;
        this.questionText = questionText;
        this.answer = answer;
        this.used = false;
    }
    
    // Getters
    public String getCategory() {
        return category;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public boolean isUsed() {
        return used;
    }
    
    // Setters
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || this.answer == null) {
            return false;
        }
        return this.answer.equalsIgnoreCase(userAnswer.trim());
    }

    public boolean checkMultipleChoiceAnswer(String userAnswer) {
    if (userAnswer == null || this.answer == null) {
        return false;
    }
    
    String cleanUserAnswer = userAnswer.trim().toUpperCase();
    String cleanCorrectAnswer = this.answer.trim().toUpperCase();
    
    // Accept both "A" and "OptionA" style answers
    if (cleanUserAnswer.equals(cleanCorrectAnswer)) {
        return true;
    }
    
    // Also accept just the letter if answer is like "A"
    if (cleanUserAnswer.length() == 1 && cleanCorrectAnswer.length() == 1) {
        return cleanUserAnswer.equals(cleanCorrectAnswer);
    }
    
    return false;
}
}