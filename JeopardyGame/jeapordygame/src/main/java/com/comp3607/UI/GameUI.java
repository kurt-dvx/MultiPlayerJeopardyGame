package com.comp3607.UI;

import com.comp3607.service.GameService;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.util.List;

public interface GameUI {
    void displayWelcome();
    List<Player> setupPlayers();
    void displayPlayerTurn(Player player);
    String selectCategory(GameService gameService, Player player);
    int selectQuestionValue(GameService gameService, String category, Player player);
    String askQuestion(Question question, String category, int value);
    void displayAnswerResult(boolean isCorrect, int value, Question question, Player player);
    void showFinalResults(GameService gameService);
    void showCurrentScores(GameService gameService);
}