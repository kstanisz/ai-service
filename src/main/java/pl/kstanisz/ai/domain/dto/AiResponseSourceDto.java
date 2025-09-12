
package pl.kstanisz.ai.domain.dto;

import lombok.Builder;

@Builder
public record AiResponseSourceDto(Double score,
                                  String text,
                                  String fileName) {
}
