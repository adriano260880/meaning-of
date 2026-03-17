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

        GetWords getWords = new GetWordsImpl();
        GetWords getVerbs = new GetVerbsImpl();
        GetWords getAnimals = new GetAnimalsImpl();
        GetWords getFruits = new GetFruitsImpl();
        GetWords getPhrasalVerbs = new GetPhrasalVerbsImpl();


        while (true) {
            WebElement searchBox = driver.findElement(By.name("q"));
            if (type.equals("all")) {
                word = getWords.getWord();
            } else if (type.equals("verb")) {
                word = getVerbs.getWord();
            } else if (type.equals("animal")) {
                word = getAnimals.getWord();
            } else if (type.equals("fruit")) {
                word = getFruits.getWord();
            } else if (type.equals("phrasal-verbs")) {
                word = getPhrasalVerbs.getWord();
            }

            searchBox.clear();
            searchBox.sendKeys("meaning of "+word);
            searchBox.submit();

            Thread.sleep(10000);
        }
    }
}
