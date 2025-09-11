package com.embabel.translator.web;

import com.embabel.agent.api.common.OperationContext;
import com.embabel.translator.agent.TranslatorAgent;
import com.embabel.translator.domain.TranslationRequest;
import com.embabel.translator.domain.TranslationResult;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/translator", produces = MediaType.APPLICATION_JSON_VALUE)
public class TranslatorController {

    private final TranslatorAgent agent;

    public TranslatorController(TranslatorAgent agent) {
        this.agent = agent;
    }

    @PostMapping(path = "/detect", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String detect(@RequestBody @Valid String text, OperationContext ctx) {
        return agent.detectLanguage(ctx, text);
    }

    @PostMapping(path = "/translate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TranslationResult translate(@RequestBody @Valid TranslationRequest request, OperationContext ctx) {
        return agent.translate(ctx, request);
    }
}