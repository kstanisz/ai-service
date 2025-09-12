package pl.kstanisz.ai.config;

import dev.langchain4j.service.*;
import dev.langchain4j.service.guardrail.InputGuardrails;
import pl.kstanisz.ai.config.chat_memory.ChatMemoryId;
import pl.kstanisz.ai.config.guardrail.EmployeeIdGuardrail;

import java.time.LocalDate;

public interface AiAssistant {

    @SystemMessage("""
            Jesteś wewnętrznym chatbotem dla pracowników firmy IT.
            Twoim zadaniem jest wspieranie pracowników poprzez udzielanie informacji
            zgodnie z polityką firmy i zasadami bezpieczeństwa danych.
            Bądź uprzejmy i rzeczowy. Odpowiadaj krótko, bez zbędnych komentarzy.
            Obecna data to {{currentDate}}
            """)
    @InputGuardrails(EmployeeIdGuardrail.class)
    Result<String> sendMessage(@MemoryId ChatMemoryId chatMemoryId, @UserMessage String prompt, @V("currentDate") LocalDate currentDate);

}
