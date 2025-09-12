package pl.kstanisz.ai.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kstanisz.ai.config.AiAssistant;
import pl.kstanisz.ai.domain.dto.AiGenerateResponseForm;
import pl.kstanisz.ai.domain.dto.AiResponseDto;

@Service
class AiServiceImpl implements AiService {

    private final AiAssistant aiAssistant;

    @Autowired
    AiServiceImpl(AiAssistant aiAssistant) {
        this.aiAssistant = aiAssistant;
    }

    @Override
    public AiResponseDto generateResponse(AiGenerateResponseForm form) {
        String result = aiAssistant.sendMessage(form.prompt());
        return AiResponseDto.builder()
                .content(result)
                .build();
    }

}
