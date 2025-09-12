package pl.kstanisz.ai.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kstanisz.ai.config.tool.AiTools;

@Configuration
public class LangChain4jConfig {

    protected static final double TEMPERATURE = 0.9;

    @Bean
    AiAssistant assistant(@Value("${open-ai.api-key}") String apiKey,
                          ChatMemoryProvider chatMemoryProvider,
                          RetrievalAugmentor retrievalAugmentor,
                          AiTools aiTools) {
        return AiServices.builder(AiAssistant.class)
                .chatModel(chatModel(apiKey))
                .chatMemoryProvider(chatMemoryProvider)
                .retrievalAugmentor(retrievalAugmentor)
                .tools(aiTools)
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
