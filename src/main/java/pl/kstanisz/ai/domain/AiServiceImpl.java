package pl.kstanisz.ai.domain;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.ContentMetadata;
import dev.langchain4j.service.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kstanisz.ai.config.AiAssistant;
import pl.kstanisz.ai.config.chat_memory.ChatMemoryId;
import pl.kstanisz.ai.domain.dto.AiGenerateResponseForm;
import pl.kstanisz.ai.domain.dto.AiResponseDto;
import pl.kstanisz.ai.domain.dto.AiResponseSourceDto;

import java.time.LocalDate;
import java.util.*;

@Service
class AiServiceImpl implements AiService {

    private final AiAssistant aiAssistant;

    @Autowired
    AiServiceImpl(AiAssistant aiAssistant) {
        this.aiAssistant = aiAssistant;
    }

    @Override
    public AiResponseDto generateResponse(AiGenerateResponseForm form) {
        final String conversationId = getOrGenerateConversationId(form.conversationId());
        ChatMemoryId chatMemoryId = new ChatMemoryId(form.userId(), conversationId);
        Result<String> result = aiAssistant.sendMessage(chatMemoryId, form.prompt(), LocalDate.now());

        List<AiResponseSourceDto> sources = Objects.requireNonNullElseGet(result.sources(), ArrayList<Content>::new).stream()
                .map(this::asAiResponseSourceDto)
                .sorted(Comparator.comparingDouble(AiResponseSourceDto::score).reversed())
                .toList();

        return AiResponseDto.builder()
                .conversationId(conversationId)
                .content(result.content())
                .sources(sources)
                .build();
    }

    private AiResponseSourceDto asAiResponseSourceDto(Content content) {
        TextSegment textSegment = content.textSegment();
        Metadata textMetadata = textSegment.metadata();

        double score = (Double) content.metadata().get(ContentMetadata.SCORE);

        return AiResponseSourceDto.builder()
                .score(Math.round(score * 100.0) / 100.0)
                .text(textSegment.text())
                .fileName(textMetadata.getString("file_name"))
                .build();
    }

    private String getOrGenerateConversationId(String conversationId) {
        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }
        return conversationId;
    }

}
