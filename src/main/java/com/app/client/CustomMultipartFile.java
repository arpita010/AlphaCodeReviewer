package com.app.client;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {

    private final File file;
    private final String name;
    private final String contentType;

    public CustomMultipartFile(File file, String name, String contentType) {
        this.file = file;
        this.name = name;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        // Use Java's built-in file copying
        java.nio.file.Files.copy(file.toPath(), dest.toPath());
    }
}