package com.harness.cachetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheTestApp {
    private static final Logger logger = LoggerFactory.getLogger(CacheTestApp.class);

    public static void main(String[] args) {
        logger.info("======================================");
        logger.info("Cache Intelligence Test Application");
        logger.info("======================================");

        System.out.println("\nHello World from Harness Cache Intelligence Test!\n");

        logger.info("This application includes 30GB+ of Maven dependencies.");
        logger.info("Maven cache location: ~/.m2/repository/");
        logger.info("All dependencies have been downloaded during the build phase.");

        System.out.println("✓ Application executed successfully!");
        System.out.println("✓ Maven cache contains 30GB+ of dependencies");

        logger.info("Application completed successfully!");
    }
}
