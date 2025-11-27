package com.comp3607.composite;

import com.comp3607.UI.GameUI;
import com.comp3607.model.Player;
import com.comp3607.service.EventLogService;
import java.util.List;

public class SetupPhase implements GamePhase {
    private GameUI ui;
    private EventLogService logService;
    private List<Player> players;
    private boolean successful;
    
    public SetupPhase(GameUI ui, EventLogService logService) {
        this.ui = ui;
        this.logService = logService;
        this.successful = false;
    }
    
    @Override
    public void execute() {
        try {
            ui.displayWelcome();
            logService.logSystemEvent("Start Game");
            
            players = ui.setupPlayers();
            successful = true;
            
            System.out.println("Setup completed: " + players.size() + " players ready");
        } catch (Exception e) {
            System.err.println("Setup failed: " + e.getMessage());
            successful = false;
        }
    }
    
    @Override
    public String getPhaseName() {
        return "Game Setup";
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
    
    public List<Player> getPlayers() {
        return players;
    }
}