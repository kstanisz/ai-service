package pl.kstanisz.ai.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kstanisz.ai.domain.dto.AiGenerateResponseForm;
import pl.kstanisz.ai.domain.dto.AiResponseDto;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AiController {

    private final AiService service;

    @PostMapping("_generate_response")
    public ResponseEntity<AiResponseDto> generateResponse(@RequestBody AiGenerateResponseForm form) {
        AiResponseDto aiResponse = service.generateResponse(form);
        return ResponseEntity.ok(aiResponse);
    }

}
