package pl.kstanisz.ai.domain.dto;

public record AiGenerateResponseForm(String userId,
                                     String conversationId,
                                     String prompt) {
}
