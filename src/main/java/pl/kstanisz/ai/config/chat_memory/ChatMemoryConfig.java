package pl.kstanisz.ai.config.chat_memory;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    private static final int MAX_CONTEXT_WINDOW_MESSAGES = 10;

    @Bean
    ChatMemoryStore chatMemoryStoreService(ChatMemoryStoreRepository repository) {
        return new ChatMemoryStoreImpl(repository);
    }

    @Bean
    ChatMemoryProvider chatMemoryProvider(ChatMemoryStore chatMemoryStoreService) {
        return memoryId ->
                MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(MAX_CONTEXT_WINDOW_MESSAGES)
                        .chatMemoryStore(chatMemoryStoreService)
                        .build();
    }

}
