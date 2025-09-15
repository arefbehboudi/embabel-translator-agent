package com.embabel.translator.agent;


import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.translator.domain.TranslationRequest;
import com.embabel.translator.domain.TranslationResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Agent(description = "Language-to-language translation agent with tone, domain, and glossary support")
@Component
public class TranslatorAgent {

    @Action(description = "Detect ISO language code from input")
    public TranslationRequest detectLanguage(OperationContext ctx, TranslationRequest req) {
        Ai ai = ctx.ai();
        String prompt = """
        You are a language detector. Return ONLY an ISO language code like "fa", "en", "de", "es-ES".
        If uncertain, choose the most probable.
        INPUT:
        %s
        """.formatted(req.getText());

        String code = ai.withLlm(LlmOptions.withDefaultLlm().withTemperature(0.1))
                .generateText(prompt)
                .trim();
        String detectedLang = code.split("\\s+")[0].trim();
        req.setSourceLang(detectedLang);
        return req;
    }

    @Action(description = "Translate with targetLang/tone/domain and enforce glossary if provided")
    public TranslationResult translate(OperationContext ctx, TranslationRequest req) {
        Ai ai = ctx.ai();

        String glossaryBlock = "n/a";
        Map<String, String> gl = req.getGlossary();
        if (gl != null && !gl.isEmpty()) {
            glossaryBlock = gl.entrySet().stream()
                    .map(e -> "- " + e.getKey() + " => " + e.getValue())
                    .collect(Collectors.joining("\n"));
        }

        String translationPrompt = """
        You are a professional translator.
        TASK: Translate SOURCE to TARGET language precisely.

        CONSTRAINTS:
        - Preserve meaning, numbers, and named entities.
        - Keep line breaks and basic markdown formatting.
        - Tone: %s
        - Domain: %s
        - Glossary (enforce exact mappings if present):
          %s
        - If a glossary term appears, replace it accordingly (respect case if possible).
        - Do NOT add explanations; return ONLY the translated text.

        META:
        - SOURCE_LANG: %s
        - TARGET_LANG: %s

        SOURCE:
        ```
        %s
        ```
        """.formatted(
                (req.getTone() == null ? "neutral" : req.getTone()),
                (req.getDomain() == null ? "general" : req.getDomain()),
                glossaryBlock,
                req.getSourceLang(),
                req.getTargetLang(),
                req.getText()
        );

        String translated = ai.withLlm(LlmOptions.withDefaultLlm().withTemperature(0.2))
                .generateText(translationPrompt)
                .trim();

        String notes = ai.withLlm(LlmOptions.withDefaultLlm().withTemperature(0.3))
                .generateText("""
            You are a translation reviewer. In <= 60 words, list any potential ambiguities,
            idioms, or glossary conflicts between SOURCE and TRANSLATION. If none, say "Looks good."
            SOURCE:
            %s
            TRANSLATION:
            %s
            """.formatted(req.getText(), translated))
                .trim();

        return new TranslationResult(req.getSourceLang(), req.getTargetLang(), translated, notes);
    }
}

