package com.embabel.translator.domain;

public enum Tone {
    FORMAL("Formal"),
    CASUAL("Casual"),
    NEUTRAL("Neutral"),
    PROFESSIONAL("Professional"),
    BUSINESS("Business"),
    MARKETING("Marketing"),
    SALESY("Sales-oriented"),
    FRIENDLY("Friendly"),
    ENTHUSIASTIC("Enthusiastic"),
    PERSUASIVE("Persuasive"),
    EDUCATIONAL("Educational"),
    STORYTELLING("Storytelling"),
    HUMOROUS("Humorous"),
    MOTIVATIONAL("Motivational");

    private final String displayName;

    Tone(String displayName) {
        this.displayName = displayName;
    }

    public static Tone valueOfDisplayName(String value) {
        return switch (value) {
            case "Casual" -> CASUAL;
            case "Neutral" -> NEUTRAL;
            case "Professional" -> PROFESSIONAL;
            case "Business" -> BUSINESS;
            case "Marketing" -> MARKETING;
            case "Sales" -> SALESY;
            case "Friendly" -> FRIENDLY;
            case "Enthusiastic" -> ENTHUSIASTIC;
            case "Persuasive" -> PERSUASIVE;
            case "Educational" -> EDUCATIONAL;
            case "Storytelling" -> STORYTELLING;
            case "Humorous" -> HUMOROUS;
            case "Motivational" -> MOTIVATIONAL;
            default -> FORMAL;
        };
    }

    public String getDisplayName() {
        return displayName;
    }
}

