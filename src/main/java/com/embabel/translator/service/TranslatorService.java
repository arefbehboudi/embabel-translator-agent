package com.embabel.translator.service;

import com.embabel.agent.core.*;
import com.embabel.translator.domain.TranslationRequest;
import com.embabel.translator.domain.TranslationResult;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TranslatorService {

    private final AgentPlatform agentPlatform;

    public TranslatorService(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
    }

    @PostMapping(path = "/translate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TranslationResult translate(@RequestBody @Valid TranslationRequest request) {
        Optional<Agent> translatorOptional = agentPlatform.agents()
                .stream()
                .filter(agent -> agent.getName().toLowerCase().contains("translator"))
                .findAny();
        if (translatorOptional.isEmpty())
            throw new IllegalArgumentException("No translator agent found.");

        Agent translator = translatorOptional.get();

        AgentProcess agentProcess = agentPlatform.createAgentProcessFrom(
                translator,
                ProcessOptions.builder()
                        .verbosity(Verbosity
                                .builder()
                                .showLlmResponses(true)
                                .showPrompts(true)
                                .build())
                        .build(),
                request
        );
        CompletableFuture<AgentProcess> start = agentPlatform.start(agentProcess);
        Object o = null;
        try {
            o = start.get().lastResult();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return (TranslationResult) o;
    }

}
