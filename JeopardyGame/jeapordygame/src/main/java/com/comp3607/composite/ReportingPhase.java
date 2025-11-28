package com.comp3607.composite;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.strategy.ReportGenerator;
import com.comp3607.config.GameConfig;

public class ReportingPhase implements GamePhase {
    private GameService gameService;
    private EventLogService logService;
    private boolean successful;
    
    public ReportingPhase(GameService gameService, EventLogService logService) {
        this.gameService = gameService;
        this.logService = logService;
        this.successful = false;
    }
    
    @Override
    public void execute() {
        try {
            System.out.println("\nGenerating reports...");
            
            // Uses config to get default report generator
            ReportGenerator reporter = GameConfig.getDefaultReportGenerator();
            reporter.generateReport(gameService.getSession(), GameConfig.TEXT_REPORT_FILE);
            logService.generateEventLog(GameConfig.EVENT_LOG_FILE);
            
            System.out.println("=> Reports generated: " + GameConfig.TEXT_REPORT_FILE + ", " + GameConfig.EVENT_LOG_FILE);
            successful = true;
            
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