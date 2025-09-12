package pl.kstanisz.ai.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AiResponseDto(String conversationId,
                            String content,
                            List<AiResponseSourceDto> sources) {

}
