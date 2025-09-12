package pl.kstanisz.ai.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jConfig {

    protected static final double TEMPERATURE = 0.9;

    @Bean
    AiAssistant assistant(@Value("${open-ai.api-key}") String apiKey) {
        return AiServices.builder(AiAssistant.class)
                .chatModel(chatModel(apiKey))
                .build();
    }

    private ChatModel chatModel(String apiKey) {
        return OpenAiChatModel.builder()
                .modelName(OpenAiChatModelName.GPT_4)
                .apiKey(apiKey)
                .temperature(TEMPERATURE)
                .build();
    }

}
