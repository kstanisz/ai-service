package pl.kstanisz.ai.config.embedding;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/chat/embeddings")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmbeddingController {

    private final EmbeddingService service;

    @PostMapping
    public ResponseEntity<Void> addEmbeddings() {
        List<DocumentData> documents = List.of(
                DocumentData.builder()
                        .name("code_of_conduct")
                        .path(Path.of("src/main/resources/data/code_of_conduct.txt"))
                        .build(),
                DocumentData.builder()
                        .name("employee_of_the_month")
                        .path(Path.of("src/main/resources/data/employee_of_the_month.txt"))
                        .build()
        );

        documents.forEach(service::addDocumentEmbeddings);

        return ResponseEntity.ok().build();
    }

}
