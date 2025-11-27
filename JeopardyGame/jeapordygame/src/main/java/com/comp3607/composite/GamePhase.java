package com.comp3607.composite;

public interface GamePhase {
    void execute();
    String getPhaseName();
    boolean isSuccessful();
}