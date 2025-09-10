package com.embabel.translator.web;

import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.translator.domain.TranslationRequest;
import com.embabel.translator.domain.TranslationResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

/*    private final AgentPlatform invoker;

    public TranslationController(AgentInvoker invoker) {
        this.invoker = invoker;
    }

    @PostMapping
    public TranslationResult translate(@RequestBody TranslationRequest req) {
        Agent agent = invoker.agents().get(0);
        invoker.createAgentProcess(agent, null, null)
        //return invoker.invoke("TranslatorAgent.translate", req);
    }*/
}