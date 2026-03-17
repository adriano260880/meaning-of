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

public class GetAnimalsImpl implements GetWords {

    private static final String ANIMALS_TXT = "./animals.txt";
    private static final String NEW_ANIMALS_TXT = "./new-animals.txt";

    @Override
    public String getWord() throws IOException {
        List<String> animals = Files.readAllLines(Path.of(ANIMALS_TXT));

        List<String> newAnimals = Files.readAllLines(Path.of(NEW_ANIMALS_TXT));

        BufferedWriter writerAnimal = Files.newBufferedWriter(
                Path.of(NEW_ANIMALS_TXT),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        Set<String> newSet = new HashSet<>();

        if (animals.size() == newAnimals.size()) {
            Files.writeString(Path.of(NEW_ANIMALS_TXT),"");
            newAnimals.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String word : newAnimals) {
                newSet.add(word);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String word;
        while (true) {
            randomIndex = random.nextInt(animals.size());
            word = animals.get(randomIndex);
            if (newSet.add(word)) {
                animals.remove(randomIndex);
                newAnimals.add(word);
                writerAnimal.write(word);
                writerAnimal.newLine();
                writerAnimal.flush();
                if (animals.size() == 0) {
                    rechargeNewWords(animals, ANIMALS_TXT);
                }
                break;
            } else {
                animals.remove(randomIndex);
                if (animals.size() == 0) {
                    rechargeNewWords(animals, ANIMALS_TXT);
                    break;
                }
            }
        }
        return word;
    }
}
