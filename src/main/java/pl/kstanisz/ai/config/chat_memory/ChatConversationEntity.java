package pl.kstanisz.ai.config.chat_memory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chat_conversation")
public class ChatConversationEntity {

    @Id
    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Lob
    @Column(name = "messages", columnDefinition = "TEXT")
    private String messages;

    @Column(name = "update_ts")
    private LocalDateTime updateTs = LocalDateTime.now();

}