# ST-7. Работа с web-приложениями (Java, Selenium)

![GitHub pull requests](https://img.shields.io/github/issues-pr/UNN-CS/ST-7)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/UNN-CS/ST-7)

Срок сдачи: **до 17.05.2026**

## Описание

Maven-проект: автоматизация браузера Chrome через Selenium WebDriver, три задания — пароль с HTML-страницы, IPv4 и прогноз погоды из JSON.

## Состав

| Файл | Назначение |
|------|------------|
| `src/main/java/com/mycompany/app/App.java` | Задание 1, запуск |
| `src/main/java/com/mycompany/app/Task2.java` | Задание 2 |
| `src/main/java/com/mycompany/app/Task3.java` | Задание 3 |
| `result/forecast.txt` | Таблица прогноза |
| `report/README.md` | Отчёт |

## Требования

- JDK 11+, Maven
- Google Chrome
- `chromedriver.exe` (Chrome for Testing), путь по умолчанию: `C:\chromedriver\chromedriver.exe`  
  Либо переменная окружения `CHROME_DRIVER_PATH`.

## Сборка и запуск

```bash
mvn compile
mvn test
mvn exec:java
```

## Отчёт

[report/README.md](report/README.md)
