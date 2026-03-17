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
import java.util.*;

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

        Map<String, GetWords> providers = Map.of(
                "all", new GetWordsImpl(),
                "verb", new GetVerbsImpl(),
                "animal", new GetAnimalsImpl(),
                "fruit", new GetFruitsImpl(),
                "phrasal-verbs", new GetPhrasalVerbsImpl()
        );

        String type = args.length != 0 ? args[0] : "all";

        GetWords provider = providers.getOrDefault(type, providers.get("all"));



        while (true) {
            WebElement searchBox = driver.findElement(By.name("q"));
            String word = provider.getWord();
            searchBox.clear();
            searchBox.sendKeys("meaning of "+word);
            searchBox.submit();

            Thread.sleep(Duration.ofSeconds(10).toMillis());
        }
    }
}
