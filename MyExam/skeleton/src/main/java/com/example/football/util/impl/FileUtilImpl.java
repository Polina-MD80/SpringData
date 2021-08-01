package com.example.football.util.impl;

import com.example.football.util.FileUtil;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {

        return Files.readString(Path.of(filePath));
    }
}
