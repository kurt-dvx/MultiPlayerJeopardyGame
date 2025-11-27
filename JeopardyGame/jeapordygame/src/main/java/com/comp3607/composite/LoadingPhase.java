package com.comp3607.composite;

import com.comp3607.model.Question;
import com.comp3607.service.EventLogService;
import com.comp3607.factory.QuestionParserFactory;
import com.comp3607.config.GameConfig;
import java.util.List;
import java.util.Arrays;

public class LoadingPhase implements GamePhase {
    private String fileName;
    private EventLogService logService;
    private List<Question> questions;
    private boolean successful;
    
    public LoadingPhase(EventLogService logService) {
        this.fileName = GameConfig.DEFAULT_QUESTIONS_FILE;
        this.logService = logService;
        this.successful = false;
    }
    
    
    @Override
    public void execute() {
        try {
            System.out.println("Loading questions from: " + fileName);
            logService.logFileLoad(fileName, true);
            
            var parser = QuestionParserFactory.createParser(fileName);
            questions = parser.parse(fileName);
            
            if (questions.isEmpty()) {
                System.out.println("No questions loaded. Using sample questions.");
                questions = createSampleQuestions();
            }
            
            successful = true;
            System.out.println("Loading completed: " + questions.size() + " questions loaded");
        } catch (Exception e) {
            System.err.println("Loading failed: " + e.getMessage());
            successful = false;
        }
    }
    
    @Override
    public String getPhaseName() {
        return "Question Loading";
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    private List<Question> createSampleQuestions() {
        // USE CONFIG FOR SAMPLE SIZE
        return Arrays.asList(
            new Question("Science", 100, "What is H2O?", "Water"),
            new Question("Science", 200, "What planet is red?", "Mars"),
            new Question("History", 100, "First US president?", "Washington"),
            new Question("History", 200, "WWII ended year?", "1945")
        );
    }
}