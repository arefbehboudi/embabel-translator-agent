package com.embabel.translator.domain;

import java.util.Map;

public class TranslationRequest {
    private String text;
    private String sourceLang;          // nullable: auto-detect
    private String targetLang;          // e.g., "gr", "en", "de-AT"
    private String tone;                // formal, casual, marketing, ...
    private String domain;              // legal, medical, tech, ...
    private Map<String, String> glossary; // term -> enforced translation

    public TranslationRequest() {
    }

    public TranslationRequest(String text, String sourceLang, String targetLang, String tone, String domain) {
        this.text = text;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.tone = tone;
        this.domain = domain;
    }

    public String getText() {
        return text;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public String getTone() {
        return tone;
    }

    public String getDomain() {
        return domain;
    }

    public Map<String, String> getGlossary() {
        return glossary;
    }
}