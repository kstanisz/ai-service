package pl.kstanisz.ai.config;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AiAssistant {

    @SystemMessage("""
            Jesteś wewnętrznym chatbotem dla pracowników firmy IT.
            Twoim zadaniem jest wspieranie pracowników poprzez udzielanie informacji
            zgodnie z polityką firmy i zasadami bezpieczeństwa danych.
            Bądź uprzejmy i rzeczowy. Odpowiadaj krótko, bez zbędnych komentarzy.
            Jeśli nie znasz odpowiedzi na zadane pytanie, poinformuj o tym użytkownika.
            """)
    String sendMessage(@UserMessage String prompt);

}
