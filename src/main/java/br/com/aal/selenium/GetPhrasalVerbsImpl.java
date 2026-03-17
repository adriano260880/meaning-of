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

public class GetPhrasalVerbsImpl implements GetWords {

    private static final String PHRASAL_VERBS_TXT = "./phrasal-verbs.txt";
    private static final String NEW_PHRASAL_VERBS_TXT = "./new-phrasal-verbs.txt";

    @Override
    public String getWord() throws IOException {
        List<String> words = Files.readAllLines(Path.of(PHRASAL_VERBS_TXT));

        List<String> newWords = Files.readAllLines(Path.of(NEW_PHRASAL_VERBS_TXT));

        BufferedWriter writerWord = Files.newBufferedWriter(
                Path.of(NEW_PHRASAL_VERBS_TXT),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        Set<String> newSet = new HashSet<>();

        if (words.size() == newWords.size()) {
            Files.writeString(Path.of(NEW_PHRASAL_VERBS_TXT),"");
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
                    rechargeNewWords(words, PHRASAL_VERBS_TXT);
                }
                break;
            } else {
                words.remove(randomIndex);
                if (words.size() == 0) {
                    rechargeNewWords(words, PHRASAL_VERBS_TXT);
                    break;
                }
            }
        }
        return word;
    }
}
