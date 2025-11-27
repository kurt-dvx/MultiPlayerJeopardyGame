package com.comp3607.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeGamePhase implements GamePhase {
    private String phaseName;
    private List<GamePhase> subPhases = new ArrayList<>();
    private boolean successful;
    
    public CompositeGamePhase(String phaseName) {
        this.phaseName = phaseName;
        this.successful = false;
    }
    
    public void addPhase(GamePhase phase) {
        subPhases.add(phase);
    }
    
    @Override
    public void execute() {
        System.out.println("\nStarting phase: " + phaseName);
        successful = true;
        
        for (GamePhase phase : subPhases) {
            phase.execute();
            if (!phase.isSuccessful()) {
                successful = false;
                System.err.println("Phase failed: " + phase.getPhaseName());
                break; // Stop execution if any phase fails
            }
        }
        
        if (successful) {
            System.out.println("Completed phase: " + phaseName);
        } else {
            System.out.println("Phase completed with errors: " + phaseName);
        }
    }
    
    @Override
    public String getPhaseName() {
        return phaseName;
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
}