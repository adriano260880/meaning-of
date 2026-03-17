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

public class GetVerbsImpl implements GetWords {

    private static final String VERBS_TXT = "./verbs.txt";
    private static final String NEW_VERBS_TXT = "./new-verbs.txt";

    @Override
    public String getWord() throws IOException {
        List<String> verbs = Files.readAllLines(Path.of(VERBS_TXT));

        List<String> newVerbs = Files.readAllLines(Path.of(NEW_VERBS_TXT));

        BufferedWriter writerVerb = Files.newBufferedWriter(
                Path.of(NEW_VERBS_TXT),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        Set<String> newSet = new HashSet<>();

        if (verbs.size() == newVerbs.size()) {
            Files.writeString(Path.of(NEW_VERBS_TXT),"");
            newVerbs.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String word : newVerbs) {
                newSet.add(word);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String word;
        while (true) {
            randomIndex = random.nextInt(verbs.size());
            word = verbs.get(randomIndex);
            if (newSet.add(word)) {
                verbs.remove(randomIndex);
                newVerbs.add(word);
                writerVerb.write(word);
                writerVerb.newLine();
                writerVerb.flush();
                if (verbs.size() == 0) {
                    rechargeNewWords(verbs, VERBS_TXT);
                }
                break;
            } else {
                verbs.remove(randomIndex);
                if (verbs.size() == 0) {
                    rechargeNewWords(verbs, VERBS_TXT);
                    break;
                }
            }
        }
        return word;
    }
}
