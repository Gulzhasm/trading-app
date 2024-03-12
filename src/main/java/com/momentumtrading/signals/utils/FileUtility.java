package com.tvh.ecommerce.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.codeborne.selenide.WebDriverRunner;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtility {

    @SneakyThrows
    public static String readFileToString(String filePath) {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static String getAbsolutePath(final String relativePath) {
        return System.getProperty("user.dir") + "\\src\\" + relativePath;
    }


}
