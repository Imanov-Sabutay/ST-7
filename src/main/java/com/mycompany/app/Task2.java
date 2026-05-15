package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Задание №2: получение IPv4 через api.ipify.org в формате JSON.
 */
public final class Task2 {

    private Task2() {
    }

    public static void run(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            String jsonStr = readJsonBody(webDriver);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            String ip = String.valueOf(obj.get("ip"));
            System.out.println("Задание 2 — IPv4-адрес клиента: " + ip);
        } catch (Exception e) {
            System.out.println("Error (Task2)");
            System.out.println(e.toString());
        }
    }

    static String readJsonBody(WebDriver webDriver) {
        try {
            WebElement pre = webDriver.findElement(By.tagName("pre"));
            return pre.getText();
        } catch (Exception ignored) {
            return webDriver.findElement(By.tagName("body")).getText().trim();
        }
    }
}
