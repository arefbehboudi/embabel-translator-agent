package com.embabel.translator.domain;

public enum Domain {
    GENERAL("General"),
    LEGAL("Legal"),
    MEDICAL("Medical"),
    TECH("Technology"),
    FINANCE("Finance"),
    SCIENTIFIC("Scientific"),
    LITERARY("Literary"),
    BUSINESS("Business"),
    ACADEMIC("Academic");

    private final String displayName;

    Domain(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Domain valueOfDisplayName(String value) {
        return switch (value) {
            case "Legal" -> LEGAL;
            case "Medical" -> MEDICAL;
            case "Technology" -> TECH;
            case "Finance" -> FINANCE;
            case "Scientific" -> SCIENTIFIC;
            case "Literary" -> LITERARY;
            case "Business" -> BUSINESS;
            case "Academic" -> ACADEMIC;
            default -> GENERAL;
        };
    }

}

