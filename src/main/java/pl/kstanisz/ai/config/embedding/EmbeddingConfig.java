
package pl.kstanisz.ai.config.embedding;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModelName;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.DefaultMetadataStorageConfig;
import dev.langchain4j.store.embedding.pgvector.MetadataStorageConfig;
import dev.langchain4j.store.embedding.pgvector.MetadataStorageMode;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EmbeddingConfig {

    public static final String DOCUMENT_FILE_NAME = "file_name";
    private static final String DOCUMENT_FILE_NAME_METADATA_COL_DEF = DOCUMENT_FILE_NAME + " varchar(256) not null";
    private static final double MIN_SCORE = 0.7;

    @Bean
    RetrievalAugmentor retrievalAugmentor(EmbeddingModel embeddingModel,
                                          EmbeddingStore<TextSegment> embeddingStore) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(EmbeddingStoreContentRetriever.builder()
                        .embeddingStore(embeddingStore)
                        .embeddingModel(embeddingModel)
                        .minScore(MIN_SCORE)
                        .build())
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel(@Value("${open-ai.api-key}") String apiKey) {
        return OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_3_SMALL)
                .build();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(@Value("${pgvector.host}") String host,
                                               @Value("${pgvector.port}") Integer port,
                                               @Value("${pgvector.database}") String database,
                                               @Value("${pgvector.user}") String user,
                                               @Value("${pgvector.password}") String password,
                                               @Value("${pgvector.table}") String table,
                                               @Value("${pgvector.dimension}") Integer dimension) {
        return PgVectorEmbeddingStore.builder()
                .metadataStorageConfig(metadataStorageConfig())
                .host(host)
                .port(port)
                .database(database)
                .user(user)
                .password(password)
                .table(table)
                .dimension(dimension)
                .build();
    }

    private MetadataStorageConfig metadataStorageConfig() {
        return DefaultMetadataStorageConfig.builder()
                .storageMode(MetadataStorageMode.COLUMN_PER_KEY)
                .columnDefinitions(List.of(DOCUMENT_FILE_NAME_METADATA_COL_DEF))
                .build();
    }

}
