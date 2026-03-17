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

public class GetFruitsImpl implements GetWords {

    private static final String NEW_FRUITS_TXT = "./new-fruits.txt";
    private static final String FRUITS_TXT = "./fruits.txt";

    @Override
    public String getWord() throws IOException {
        List<String> fruits = Files.readAllLines(Path.of(FRUITS_TXT));

        List<String> newFruits = Files.readAllLines(Path.of(NEW_FRUITS_TXT));

        BufferedWriter writerFruit = Files.newBufferedWriter(
                Path.of(NEW_FRUITS_TXT),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        Set<String> newSet = new HashSet<>();

        if (fruits.size() == newFruits.size()) {
            Files.writeString(Path.of(NEW_FRUITS_TXT),"");
            newFruits.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String word : newFruits) {
                newSet.add(word);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String word;
        while (true) {
            randomIndex = random.nextInt(fruits.size());
            word = fruits.get(randomIndex);
            if (newSet.add(word)) {
                fruits.remove(randomIndex);
                newFruits.add(word);
                writerFruit.write(word);
                writerFruit.newLine();
                writerFruit.flush();
                if (fruits.size() == 0) {
                    rechargeNewWords(fruits, FRUITS_TXT);
                }
                break;
            } else {
                fruits.remove(randomIndex);
                if (fruits.size() == 0) {
                    rechargeNewWords(fruits, FRUITS_TXT);
                    break;
                }
            }
        }
        return word;
    }
}
