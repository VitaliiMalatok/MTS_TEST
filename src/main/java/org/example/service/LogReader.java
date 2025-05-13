package org.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class LogReader {
    public static void main(String[] args) {
        Path logDir = Paths.get("logs"); // Folder with logs

        try (Stream<Path> files = Files.list(logDir)) {
            Path latestLog = files
                    .filter(Files::isRegularFile)
                    .max(Comparator.comparingLong(file -> file.toFile().lastModified())) // Find the last modified file
                    .orElseThrow(() -> new IOException("No log files found"));

            System.out.println("Reading log file: " + latestLog);

            // Reading the contents of the file
            Files.lines(latestLog).forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
