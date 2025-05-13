package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class LogParser {
    private static final Logger LOGGER = LogManager.getLogger(LogParser.class);

    public static String getLatestLogFile(String directoryPath) {
        try {
            return Files.list(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .max((f1, f2) -> {
                        try {
                            return Files.getLastModifiedTime(f1).compareTo(Files.getLastModifiedTime(f2));
                        } catch (IOException e) {
                            return 0;
                        }
                    })
                    .map(path -> path.toAbsolutePath().toString())
                    .orElse("⚠ Лог-файл не найден");
        } catch (IOException e) {
            LOGGER.error("Ошибка при поиске последнего лог-файла: " + e.getMessage(), e);
            return "⚠ Ошибка поиска лог-файла";
        }
    }

    public static String readLastLogLines(String filePath, int lines) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            int start = Math.max(allLines.size() - lines, 0);
            return String.join("\n", allLines.subList(start, allLines.size()));
        } catch (IOException e) {
            LOGGER.error("Ошибка чтения лог-файла: " + e.getMessage(), e);
            return "⚠ Ошибка чтения логов";
        }
    }
}
