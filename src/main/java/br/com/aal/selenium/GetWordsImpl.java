package br.com.aal.selenium;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static br.com.aal.selenium.GetUtils.rechargeNewWords;

public class GetWordsImpl implements GetWords {

    private static final String WORDS_TXT = "./words.txt";
    private static final String NEW_WORDS_TXT = "./new-words.txt";

    @Override
    public String getWord() throws IOException {
        List<String> words = Files.readAllLines(Path.of(WORDS_TXT));

        List<String> newWords = Files.readAllLines(Path.of(NEW_WORDS_TXT));

        BufferedWriter writerWord = Files.newBufferedWriter(
                Path.of(NEW_WORDS_TXT),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        Set<String> newSet = new HashSet<>();

        if (words.size() == newWords.size()) {
            Files.writeString(Path.of(NEW_WORDS_TXT),"");
            newWords.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String word : newWords) {
                newSet.add(word);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String word;
        while (true) {
            randomIndex = random.nextInt(words.size());
            word = words.get(randomIndex);
            if (newSet.add(word)) {
                words.remove(randomIndex);
                newWords.add(word);
                writerWord.write(word);
                writerWord.newLine();
                writerWord.flush();
                if (words.size() == 0) {
                    rechargeNewWords(words, WORDS_TXT);
                }
                break;
            } else {
                words.remove(randomIndex);
                if (words.size() == 0) {
                    rechargeNewWords(words, WORDS_TXT);
                    break;
                }
            }
        }
        return word;
    }
}
