package com.mycompany.app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    /**
     * Путь к исполняемому файлу chromedriver в файловой системе (по заданию ST-7).
     * Скачайте архив «chromedriver … for your system» с
     * https://googlechromelabs.github.io/chrome-for-testing/ — версия должна совпадать с Chrome.
     * Распакуйте и укажите полный путь к {@code chromedriver} / {@code chromedriver.exe}.
     * <p>
     * Альтернатива: переменная окружения {@code CHROME_DRIVER_PATH} (например в CI).
     */
    private static final String CHROME_DRIVER_PATH_DEFAULT = "C:\\chromedriver\\chromedriver.exe";

    private static String chromedriverExecutablePath() {
        String fromEnv = System.getenv("CHROME_DRIVER_PATH");
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv.trim();
        }
        return CHROME_DRIVER_PATH_DEFAULT;
    }

    public static void main(String[] args) {
        Path driverPath = Paths.get(chromedriverExecutablePath());
        if (!Files.isRegularFile(driverPath)) {
            System.err.println("Файл chromedriver не найден: " + driverPath.toAbsolutePath());
            System.err.println("Установите Chrome, скачайте драйвер с Chrome for Testing и задайте путь:");
            System.err.println("  • отредактируйте CHROME_DRIVER_PATH_DEFAULT в App.java, или");
            System.err.println("  • задайте переменную окружения CHROME_DRIVER_PATH");
            System.err.println("Страница загрузок: https://googlechromelabs.github.io/chrome-for-testing/");
            System.exit(1);
        }

        System.setProperty("webdriver.chrome.driver", driverPath.toString());

        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv("CI"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
            for (String bin : new String[] {
                    "/usr/bin/google-chrome",
                    "/usr/bin/google-chrome-stable",
                    "/usr/bin/chromium-browser",
                    "/usr/bin/chromium",
                    "/snap/bin/chromium"
            }) {
                if (Files.isExecutable(Paths.get(bin))) {
                    options.setBinary(bin);
                    break;
                }
            }
        }

        WebDriver webDriver = new ChromeDriver(options);
        try {
            runTask1PasswordGenerator(webDriver);
            Task2.run(webDriver);
            Task3.run(webDriver);
        } finally {
            webDriver.quit();
        }
    }

    private static void runTask1PasswordGenerator(WebDriver webDriver) {
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultid")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#resultid .verybigtext b")));
            WebElement passwordEl = webDriver.findElement(By.cssSelector("#resultid .verybigtext b"));
            String password = passwordEl.getText();
            System.out.println("Задание 1 — сгенерированный пароль: " + password);
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}
