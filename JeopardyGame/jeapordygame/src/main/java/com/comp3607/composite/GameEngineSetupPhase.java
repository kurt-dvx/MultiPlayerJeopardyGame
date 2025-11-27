package com.comp3607.composite;

import com.comp3607.service.GameService;
import com.comp3607.observer.GameNotifier;
import com.comp3607.service.EventLogService;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.util.List;

public class GameEngineSetupPhase implements GamePhase {
    private SetupPhase setupPhase;
    private LoadingPhase loadingPhase;
    private EventLogService logService;
    private GameService gameService;
    private boolean successful;
    
    public GameEngineSetupPhase(SetupPhase setupPhase, LoadingPhase loadingPhase, EventLogService logService) {
        this.setupPhase = setupPhase;
        this.loadingPhase = loadingPhase;
        this.logService = logService;
        this.successful = false;
    }
    
    @Override
    public void execute() {
        try {
            if (!setupPhase.isSuccessful() || !loadingPhase.isSuccessful()) {
                successful = false;
                return;
            }
            
            List<Player> players = setupPhase.getPlayers();
            List<Question> questions = loadingPhase.getQuestions();
            
            GameNotifier notifier = new GameNotifier();
            notifier.addObserver(logService);
            gameService = new GameService(notifier);
            gameService.startGame(players, questions);
            
            successful = true;
            System.out.println("Game engine setup completed");
            
        } catch (Exception e) {
            System.err.println("Game engine setup failed: " + e.getMessage());
            successful = false;
        }
    }
    
    @Override
    public String getPhaseName() {
        return "Game Engine Setup";
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
    
    public GameService getGameService() {
        return gameService;
    }
}