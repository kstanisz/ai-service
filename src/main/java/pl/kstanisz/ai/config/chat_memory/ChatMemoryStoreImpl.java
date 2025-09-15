package pl.kstanisz.ai.config.chat_memory;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ChatMemoryStoreImpl implements ChatMemoryStore {
    private final ChatMemoryStoreRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessages(Object memoryId) {
        ChatMemoryId chatMemoryId = (ChatMemoryId) memoryId;
        return repository.getMessagesAsJsonString(chatMemoryId.conversationId(), chatMemoryId.userId())
                .map(ChatMessageDeserializer::messagesFromJson)
                .orElseGet(List::of);
    }

    @Override
    @Transactional
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        ChatMemoryId chatMemoryId = (ChatMemoryId) memoryId;

        ChatConversationEntity entity = new ChatConversationEntity();
        entity.setConversationId(chatMemoryId.conversationId());
        entity.setUserId(chatMemoryId.userId());
        entity.setMessages(ChatMessageSerializer.messagesToJson(messages));
        entity.setUpdateTs(LocalDateTime.now());

        repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteMessages(Object memoryId) {
        ChatMemoryId chatMemoryId = (ChatMemoryId) memoryId;
        repository.deleteByConversationIdAndUserId(chatMemoryId.conversationId(), chatMemoryId.userId());
    }

}
