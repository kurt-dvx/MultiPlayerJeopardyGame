// factory/GameEventFactory.java
package com.comp3607.factory;

import com.comp3607.model.*;

public class GameEventFactory {
    
    public static GameEvent createQuestionAnsweredEvent(String caseId, String playerId,
                                                      String category, int value, 
                                                      String answer, boolean isCorrect, 
                                                      int scoreAfter) {
        return new QuestionAnsweredEvent(caseId, playerId, category, value, answer, isCorrect, scoreAfter);
    }
    
    public static GameEvent createSystemEvent(String caseId, String activity) {
        return new SystemEvent(caseId, activity);
    }
    
    public static GameEvent createSystemEvent(String caseId, String activity, String result) {
        return new SystemEvent(caseId, activity, result);
    }
    
    public static GameEvent createSystemEvent(String caseId, String activity, String result, String answerGiven) {
        return new SystemEvent(caseId, activity, result, answerGiven);
    }
    
    public static GameEvent createCategorySelectionEvent(String caseId, String playerId, String category) {
        return new CategorySelectionEvent(caseId, playerId, category);
    }
    
    public static GameEvent createQuestionSelectionEvent(String caseId, String playerId, String category, int value) {
        return new QuestionSelectionEvent(caseId, playerId, category, value);
    }
    
    public static GameEvent createScoreUpdateEvent(String caseId, String playerId, int scoreAfter) {
        return new ScoreUpdateEvent(caseId, playerId, scoreAfter);
    }
    
    public static GameEvent createPlayerNameEvent(String caseId, String playerId, String playerName) {
        return new SystemEvent(caseId, "Enter Player Name", "Success", playerName);
    }
    
    public static GameEvent createPlayerCountEvent(String caseId, int playerCount) {
        return new SystemEvent(caseId, "Select Player Count", "Success", String.valueOf(playerCount));
    }
}