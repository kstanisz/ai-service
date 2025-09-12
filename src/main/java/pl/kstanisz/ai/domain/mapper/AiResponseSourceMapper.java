package pl.kstanisz.ai.domain.mapper;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.ContentMetadata;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.kstanisz.ai.domain.dto.AiResponseSourceDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AiResponseSourceMapper {

    public static List<AiResponseSourceDto> mapToAiResponseSources(List<Content> sources) {
        return Objects.requireNonNullElseGet(sources, ArrayList<Content>::new).stream()
                .map(AiResponseSourceMapper::asAiResponseSourceDto)
                .sorted(Comparator.comparingDouble(AiResponseSourceDto::score).reversed())
                .toList();
    }

    private static AiResponseSourceDto asAiResponseSourceDto(Content content) {
        TextSegment textSegment = content.textSegment();
        Metadata textMetadata = textSegment.metadata();

        double score = (Double) content.metadata().get(ContentMetadata.SCORE);

        return AiResponseSourceDto.builder()
                .score(Math.round(score * 100.0) / 100.0)
                .text(textSegment.text())
                .fileName(textMetadata.getString("file_name"))
                .build();
    }

}
