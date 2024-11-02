package com.pdfgen.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class PDFRepository {
    private static final Logger logger = LoggerFactory.getLogger(PDFRepository.class);
    private static final String STORAGE_DIRECTORY = "pdf_storage/";

    public PDFRepository() {
        new File(STORAGE_DIRECTORY).mkdirs();
        logger.info("PDF storage directory created or exists: {}", STORAGE_DIRECTORY);
    }
    public File getFile(String fileName) {
        logger.info("Fetching file: {}", fileName);
        File file = new File(STORAGE_DIRECTORY + fileName);

        if (!file.exists()) {
            logger.warn("Requested file does not exist: {}", fileName);
        }
        return file;
    }


    public byte[] getFileContent(File file) throws IOException {
        logger.info("Reading file content for: {}", file.getName());

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.error("Error reading file content for: {}", file.getName(), e);
            throw e;
        }
    }

    public void saveFile(String fileName) throws IOException {
        File file = new File(STORAGE_DIRECTORY + fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
                logger.info("New file created: {}", fileName);
            } catch (IOException e) {
                logger.error("Failed to create file: {}", fileName, e);
                throw e;
            }
        }
    }


}
