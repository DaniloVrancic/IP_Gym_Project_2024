package org.unibl.etf.onlinefitnessmanager.enums;

public enum DifficultyLevel {
    EASY(1),
    MEDIUM(2),
    HARD(3),
    VERY_HARD(4);

    int level;

    private DifficultyLevel(int level)
    { this.level = level; }

    public int getLevel()
    { return this.level; }
}
