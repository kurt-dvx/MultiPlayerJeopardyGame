package com.comp3607.service;

import com.comp3607.composite.*;
import com.comp3607.UI.GameUI;

public class GameOrchestrator {
    private GameUI ui;
    private EventLogService logService;
    
    public GameOrchestrator(GameUI ui, EventLogService logService) {
        this.ui = ui;
        this.logService = logService;
    }
    
    public boolean runGame() {
        CompositeGamePhase gameFlow = new CompositeGamePhase("Complete Jeopardy Game");
        
        // Setup Phase
        SetupPhase setupPhase = new SetupPhase(ui, logService);
        gameFlow.addPhase(setupPhase);
        
        // Loading Phase
        LoadingPhase loadingPhase = new LoadingPhase(logService);
        gameFlow.addPhase(loadingPhase);
        
        // Game Engine Setup
        GameEngineSetupPhase enginePhase = new GameEngineSetupPhase(setupPhase, loadingPhase, logService);
        gameFlow.addPhase(enginePhase);
        
        // Gameplay Phase
        ConditionalGameplayPhase gameplayPhase = new ConditionalGameplayPhase(enginePhase, ui, logService);
        gameFlow.addPhase(gameplayPhase);
        
        // Reporting Phase
        ConditionalReportingPhase reportingPhase = new ConditionalReportingPhase(enginePhase, gameplayPhase, logService);
        gameFlow.addPhase(reportingPhase);
        
        // Execute the entire game flow
        gameFlow.execute();
        
        return gameFlow.isSuccessful();
    }
}