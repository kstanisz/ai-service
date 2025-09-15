package pl.kstanisz.ai.config.chat_memory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatMemoryStoreRepository extends JpaRepository<ChatConversationEntity, String> {

    @Query("select c.messages from ChatConversationEntity c where c.conversationId = :conversationId and c.userId = :userId")
    Optional<String> getMessagesAsJsonString(String conversationId, String userId);

    void deleteByConversationIdAndUserId(String conversationId, String userId);

}