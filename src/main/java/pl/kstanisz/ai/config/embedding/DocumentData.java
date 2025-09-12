package pl.kstanisz.ai.config.embedding;

import lombok.Builder;

import java.nio.file.Path;

@Builder
public record DocumentData(String name, Path path) {
}