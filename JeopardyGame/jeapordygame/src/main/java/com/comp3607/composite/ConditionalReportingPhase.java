package com.comp3607.composite;

import com.comp3607.service.EventLogService;

public class ConditionalReportingPhase implements GamePhase {
    private GameEngineSetupPhase enginePhase;
    private ConditionalGameplayPhase gameplayPhase;
    private EventLogService logService;
    private boolean successful;
    
    public ConditionalReportingPhase(GameEngineSetupPhase enginePhase, 
                                   ConditionalGameplayPhase gameplayPhase, 
                                   EventLogService logService) {
        this.enginePhase = enginePhase;
        this.gameplayPhase = gameplayPhase;
        this.logService = logService;
        this.successful = false;
    }
    
    @Override
    public void execute() {
        // Only generates reports if gameplay completed successfully
        if (!enginePhase.isSuccessful() || !gameplayPhase.isSuccessful()) {
            System.out.println("Skipping reports - game did not complete successfully");
            successful = true; // Not a failure, just skipped
            return;
        }
        
        try {
            ReportingPhase reportingPhase = new ReportingPhase(enginePhase.getGameService(), logService);
            reportingPhase.execute();
            successful = reportingPhase.isSuccessful();
        } catch (Exception e) {
            System.err.println("Reporting failed: " + e.getMessage());
            successful = false;
        }
    }
    
    @Override
    public String getPhaseName() {
        return "Report Generation";
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
}