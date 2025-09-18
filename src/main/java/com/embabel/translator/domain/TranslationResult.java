package com.embabel.translator.domain;

public class TranslationResult {
    private String detectedSourceLang;
    private String targetLang;
    private String translation;
    private String notes;

    public TranslationResult(String detectedSourceLang, String targetLang, String translation, String notes) {
        this.detectedSourceLang = detectedSourceLang;
        this.targetLang = targetLang;
        this.translation = translation;
        this.notes = notes;
    }

    public String getTranslation() {
        return translation;
    }

    public String getNotes() {
        return notes;
    }
}