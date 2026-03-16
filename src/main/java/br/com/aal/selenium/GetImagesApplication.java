package br.com.aal.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class GetImagesApplication {

	public static void main(String[] args) throws InterruptedException, IOException {
		SpringApplication.run(GetImagesApplication.class, args);

        List<String> words = Files.readAllLines(Path.of("./words.txt"));
        List<String> newWords = Files.readAllLines(Path.of("./new-words.txt"));
        BufferedWriter writerWord = Files.newBufferedWriter(
                Path.of("./new-words.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        List<String> verbs = Files.readAllLines(Path.of("./verbs.txt"));
        List<String> newVerbs = Files.readAllLines(Path.of("./new-verbs.txt"));
        BufferedWriter writerVerb = Files.newBufferedWriter(
                Path.of("./new-verbs.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        List<String> animals = Files.readAllLines(Path.of("./animals.txt"));
        List<String> newAnimals = Files.readAllLines(Path.of("./new-animals.txt"));
        BufferedWriter writerAnimal = Files.newBufferedWriter(
                Path.of("./new-animals.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        List<String> fruits = Files.readAllLines(Path.of("./fruits.txt"));
        List<String> newFruits = Files.readAllLines(Path.of("./new-fruits.txt"));
        BufferedWriter writerFruit = Files.newBufferedWriter(
                Path.of("./new-fruits.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--lang=en-GB");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");

        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.google.com/search");
        String word = null;
        String type = "all";
        if (args.length != 0) {
            type = args[0];
        }

        Set<String> newSet = new HashSet<>();

        while (true) {
            WebElement searchBox = driver.findElement(By.name("q"));
            if (type.equals("all")) {
                word = getWord(newSet, words, newWords, writerWord);
            } else if (type.equals("verb")) {
                word = getVerb(newSet, verbs, newVerbs, writerVerb);
            } else if (type.equals("animal")) {
                word = getAnimal(newSet, animals, newAnimals, writerAnimal);
            } else if (type.equals("fruit")) {
                word = getFruit(newSet, fruits, newFruits, writerFruit);
            }

            searchBox.clear();
            searchBox.sendKeys("meaning of "+word);
            searchBox.submit();

            Thread.sleep(10000);
        }
	}

    private static String getWord(Set<String> newSet, List<String> words, List<String> newWords, BufferedWriter writerWord) throws IOException {

        if (words.size() == newWords.size()) {
            Files.writeString(Path.of("./new-words.txt"),"");
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
                    rechargeNewWords(words);
                }
                break;
            } else {
                words.remove(randomIndex);
                if (words.size() == 0) {
                    rechargeNewWords(words);
                    break;
                }
            }
        }
        return word;
    }
    private static String getVerb(Set<String> newSet, List<String> verbs, List<String> newVerbs, BufferedWriter writerVerb) throws IOException {
        if (verbs.size() == newVerbs.size()) {
            Files.writeString(Path.of("./new-verbs.txt"),"");
            newVerbs.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String verb : newVerbs) {
                newSet.add(verb);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String verb;
        while (true) {
            randomIndex = random.nextInt(verbs.size());
            verb = verbs.get(randomIndex);
            if (newSet.add(verb)) {
                verbs.remove(randomIndex);
                newVerbs.add(verb);
                writerVerb.write(verb);
                writerVerb.newLine();
                writerVerb.flush();
                if (verbs.size() == 0) {
                    rechargeNewVerbs(verbs);
                }
                break;
            } else {
                verbs.remove(randomIndex);
                if (verbs.size() == 0) {
                    rechargeNewVerbs(verbs);
                    break;
                }
            }
        }
        return verb;
    }

    private static String getAnimal(Set<String> newSet, List<String> animals, List<String> newAnimals, BufferedWriter writerAnimal) throws IOException {
        if (animals.size() == newAnimals.size()) {
            Files.writeString(Path.of("./new-animals.txt"),"");
            newAnimals.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String animal : newAnimals) {
                newSet.add(animal);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String animal;
        while (true) {
            randomIndex = random.nextInt(animals.size());
            animal = animals.get(randomIndex);
            if (newSet.add(animal)) {
                animals.remove(randomIndex);
                newAnimals.add(animal);
                writerAnimal.write(animal);
                writerAnimal.newLine();
                writerAnimal.flush();
                if (animals.size() == 0) {
                    rechargeNewAnimals(animals);
                }
                break;
            } else {
                animals.remove(randomIndex);
                if (animals.size() == 0) {
                    rechargeNewAnimals(animals);
                    break;
                }
            }
        }
        return animal;
    }

    private static String getFruit(Set<String> newSet, List<String> fruits, List<String> newFruits, BufferedWriter writerFruit) throws IOException {
        if (fruits.size() == newFruits.size() &&
        fruits.containsAll(newFruits) &&
        newFruits.containsAll(fruits)) {
            Files.writeString(Path.of("./new-fruits.txt"),"");
            newFruits.clear();
            newSet.clear();
        } else if (newSet.size() == 0){
            for (String fruit : newFruits) {
                newSet.add(fruit);
            }
        }

        Random random = new Random();
        int randomIndex = 0;
        String fruit;
        while (true) {
            randomIndex = random.nextInt(fruits.size());
            fruit = fruits.get(randomIndex);
            if (newSet.add(fruit)) {
                fruits.remove(randomIndex);
                newFruits.add(fruit);
                writerFruit.write(fruit);
                writerFruit.newLine();
                writerFruit.flush();
                if (fruits.size() == 0) {
                    rechargeNewFruits(fruits);
                }
                break;
            } else {
                fruits.remove(randomIndex);
                if (fruits.size() == 0) {
                    rechargeNewFruits(fruits);
                    break;
                }
            }
        }
        return fruit;
    }

    private static void rechargeNewWords(List<String> words) throws IOException {
        if (words.size() == 0) {
            words.addAll(Files.readAllLines(Path.of("./words.txt")));
        }
    }

    private static void rechargeNewVerbs(List<String> verbs) throws IOException {
        if (verbs.size() == 0) {
            verbs.addAll(Files.readAllLines(Path.of("./verbs.txt")));
        }
    }

    private static void rechargeNewAnimals(List<String> animals) throws IOException {
        if (animals.size() == 0) {
            animals.addAll(Files.readAllLines(Path.of("./animals.txt")));
        }
    }

    private static void rechargeNewFruits(List<String> fruits) throws IOException {
        if (fruits.size() == 0) {
            fruits.addAll(Files.readAllLines(Path.of("./fruits.txt")));
        }
    }

}
