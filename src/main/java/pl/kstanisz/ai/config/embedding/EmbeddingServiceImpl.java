package pl.kstanisz.ai.config.embedding;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static pl.kstanisz.ai.config.embedding.EmbeddingConfig.DOCUMENT_FILE_NAME;

@Service
class EmbeddingServiceImpl implements EmbeddingService {
    private static final DocumentSplitter DOCUMENT_SPLITTER = DocumentSplitters.recursive(300, 30);

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    EmbeddingServiceImpl(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    @Override
    public void addDocumentEmbeddings(DocumentData documentData) {
        final String fileName = documentData.name();

        removeDocumentEmbeddings(fileName);

        List<TextSegment> segments = DOCUMENT_SPLITTER.split(asDocument(documentData));
        for (TextSegment segment : segments) {
            Embedding embedding = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding, segment);
        }
    }

    private void removeDocumentEmbeddings(String fileName) {
        Filter metadataFilter = MetadataFilterBuilder.metadataKey(DOCUMENT_FILE_NAME)
                .isEqualTo(fileName);
        embeddingStore.removeAll(metadataFilter);
    }

    private Document asDocument(DocumentData documentData) {
        try {
            String content = Files.readString(documentData.path(), StandardCharsets.UTF_8);
            Document document = Document.document(content);
            document.metadata().put(DOCUMENT_FILE_NAME, documentData.name());
            return document;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read document from file " + documentData.path(), e);
        }
    }

}
