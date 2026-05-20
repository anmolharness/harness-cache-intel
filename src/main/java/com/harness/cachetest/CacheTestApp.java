package com.harness.cachetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class CacheTestApp {
    private static final Logger logger = LoggerFactory.getLogger(CacheTestApp.class);
    private static final long TARGET_CACHE_SIZE = 30L * 1024 * 1024 * 1024; // 30 GB
    private static final int CHUNK_SIZE = 100 * 1024 * 1024; // 100 MB chunks

    public static void main(String[] args) {
        logger.info("Cache Intelligence Test Application");
        logger.info("====================================");

        // Simple hello world functionality
        System.out.println("Hello World from Cache Test Application!");

        // Generate cache files
        generateCacheFiles();

        logger.info("Application completed successfully!");
    }

    private static void generateCacheFiles() {
        String cacheDir = System.getProperty("user.home") + "/.m2/repository/cache-data";
        File cacheDirFile = new File(cacheDir);

        if (!cacheDirFile.exists()) {
            cacheDirFile.mkdirs();
            logger.info("Created cache directory: {}", cacheDir);
        }

        long totalSize = 0;
        int fileCount = 0;
        Random random = new Random();

        logger.info("Starting cache file generation (target: 30 GB)...");

        try {
            while (totalSize < TARGET_CACHE_SIZE) {
                String fileName = String.format("cache-file-%05d.bin", fileCount++);
                Path filePath = Paths.get(cacheDir, fileName);

                try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                    byte[] buffer = new byte[CHUNK_SIZE];
                    random.nextBytes(buffer);
                    fos.write(buffer);

                    totalSize += CHUNK_SIZE;

                    if (fileCount % 10 == 0) {
                        long sizeInGB = totalSize / (1024 * 1024 * 1024);
                        logger.info("Generated {} files, total size: {} GB", fileCount, sizeInGB);
                    }
                }
            }

            long finalSizeInGB = totalSize / (1024 * 1024 * 1024);
            logger.info("Cache generation complete!");
            logger.info("Total files: {}", fileCount);
            logger.info("Total size: {} GB", finalSizeInGB);
            logger.info("Cache location: {}", cacheDir);

        } catch (IOException e) {
            logger.error("Error generating cache files", e);
            throw new RuntimeException("Failed to generate cache files", e);
        }
    }
}
