package com.comp3607.strategy;

import com.comp3607.model.GameSession;

public interface ReportGenerator {
    void generateReport(GameSession session, String filename);
}