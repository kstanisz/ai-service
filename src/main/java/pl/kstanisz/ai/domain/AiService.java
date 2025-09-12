package pl.kstanisz.ai.domain;

import pl.kstanisz.ai.domain.dto.AiGenerateResponseForm;
import pl.kstanisz.ai.domain.dto.AiResponseDto;

public interface AiService {

    AiResponseDto generateResponse(AiGenerateResponseForm form);

}
