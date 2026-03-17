package br.com.aal.selenium;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GetUtils {

    public static void rechargeNewWords(List<String> words, String path) throws IOException {
        if (words.size() == 0) {
            words.addAll(Files.readAllLines(Path.of(path)));
        }
    }
}
