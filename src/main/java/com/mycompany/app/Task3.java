package com.mycompany.app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;

/**
 * Задание №3: прогноз погоды Open-Meteo для Нижнего Новгорода (56°N, 44°E).
 */
public final class Task3 {

    private static final String FORECAST_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
                    + "&hourly=temperature_2m,rain&current=cloud_cover"
                    + "&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

    private Task3() {
    }

    public static void run(WebDriver webDriver) {
        try {
            webDriver.get(FORECAST_URL);
            String jsonStr = Task2.readJsonBody(webDriver);
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) root.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            StringBuilder table = new StringBuilder();
            table.append(String.format("%-4s\t%-19s\t%-12s\t%s%n", "№", "Дата/время", "Температура", "Осадки (мм)"));
            for (int i = 0; i < times.size(); i++) {
                String time = String.valueOf(times.get(i));
                String temp = String.valueOf(temps.get(i));
                String rain = String.valueOf(rains.get(i));
                table.append(String.format("%-4d\t%-19s\t%-12s\t%s%n", i + 1, time, temp, rain));
            }
            String out = table.toString();
            System.out.println("Задание 3 — прогноз на сутки (Нижний Новгород):");
            System.out.print(out);

            Path resultDir = Paths.get("result");
            Files.createDirectories(resultDir);
            Path forecastFile = resultDir.resolve("forecast.txt");
            Files.write(forecastFile, out.getBytes(StandardCharsets.UTF_8));
            System.out.println("Таблица сохранена в " + forecastFile.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error (Task3) — запись файла");
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println("Error (Task3)");
            System.out.println(e.toString());
        }
    }
}
