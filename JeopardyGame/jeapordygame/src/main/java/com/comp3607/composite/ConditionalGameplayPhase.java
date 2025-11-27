package com.comp3607.composite;

import com.comp3607.service.GameService;
import com.comp3607.UI.GameUI;
import com.comp3607.service.EventLogService;

public class ConditionalGameplayPhase implements GamePhase {
    private GameEngineSetupPhase enginePhase;
    private GameUI ui;
    private EventLogService logService;
    private boolean successful;
    
    public ConditionalGameplayPhase(GameEngineSetupPhase enginePhase, GameUI ui, EventLogService logService) {
        this.enginePhase = enginePhase;
        this.ui = ui;
        this.logService = logService;
        this.successful = false;
    }
    
    @Override
    public void execute() {
        // Only executes if the engine phase was successful
        if (!enginePhase.isSuccessful()) {
            System.err.println("Skipping gameplay - game engine setup failed");
            successful = false;
            return;
        }
        
        try {
            GameService gameService = enginePhase.getGameService();
            GameplayPhase gameplayPhase = new GameplayPhase(gameService, ui, logService);
            gameplayPhase.execute();
            successful = gameplayPhase.isSuccessful();
            
        } catch (Exception e) {
            System.err.println("Gameplay failed: " + e.getMessage());
            successful = false;
        }
    }
    
    @Override
    public String getPhaseName() {
        return "Gameplay";
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
}