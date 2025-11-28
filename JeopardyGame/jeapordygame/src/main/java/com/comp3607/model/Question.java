package com.comp3607.model;

public class Question {
    private String category;
    private int value;
    private String questionText;
    private String answer;
    private boolean used;
    
    public Question(String category, int value, String questionText, String answer) {
        this.category = category;
        this.value = value;
        this.questionText = questionText;
        this.answer = answer;
        this.used = false;
    }
    
    // Getters
    public String getCategory() { return category; }
    public int getValue() { return value; }
    public String getQuestionText() { return questionText; }
    public String getAnswer() { return answer; }
    public boolean isUsed() { return used; }
    
    // Setters
    public void setUsed(boolean used) { this.used = used; }

    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || this.answer == null) {
            return false;
        }
        
        String cleanUserAnswer = userAnswer.trim().toUpperCase();
        String cleanCorrectAnswer = this.answer.trim().toUpperCase();
        
        // Handle both letter-only (A, B, C, D) and full answer matching
        if (cleanUserAnswer.equals(cleanCorrectAnswer)) {
            return true;
        }
        
        // If correct answer is just a letter (A), accept just the letter
        if (cleanCorrectAnswer.length() == 1 && cleanUserAnswer.length() == 1) {
            return cleanUserAnswer.equals(cleanCorrectAnswer);
        }
        
        // If correct answer is like "OptionA", also accept just "A"
        if (cleanUserAnswer.length() == 1 && cleanCorrectAnswer.contains(cleanUserAnswer)) {
            return true;
        }
        
        return false;
    }
}